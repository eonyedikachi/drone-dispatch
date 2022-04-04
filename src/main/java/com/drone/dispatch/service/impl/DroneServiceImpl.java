package com.drone.dispatch.service.impl;

import com.drone.dispatch.entities.Drone;
import com.drone.dispatch.entities.Medication;
import com.drone.dispatch.pojos.DroneDTO;
import com.drone.dispatch.pojos.MedicationDTO;
import com.drone.dispatch.repos.DroneRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.drone.dispatch.repos.MedicationRepository;
import com.drone.dispatch.service.inf.DroneService;
import com.drone.dispatch.utils.DroneState;
import com.drone.dispatch.utils.DroneModelWeightLimit;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
@Slf4j
@Transactional
public class DroneServiceImpl implements DroneService {

    private final double MIN_BATTERY_CAPACITY = 25;
    private ModelMapper mapper = new ModelMapper();

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private MedicationRepository medicationRepository;

    public DroneServiceImpl() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DroneDTO  registerDrone(DroneDTO droneDTO) {
        log.info("About to register drone...");
         boolean checkDroneExist = droneRepository.existsById(droneDTO.getSerialNumber());
         Drone drone;

         if(!checkDroneExist){
             droneDTO.setWeightLimit(DroneModelWeightLimit.getWeightLimit(droneDTO.getModel()));
             droneDTO.setState(DroneState.IDLE);
             drone = mapper.map(droneDTO, Drone.class);
             droneRepository.save(drone);
         }else {
             log.warn("Drone already registered drone.");
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Drone already registered.");
         }

        log.info("Drone registered with the following details: {}.", droneDTO);
        return mapper.map(drone, DroneDTO.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DroneDTO loadDroneMedications(String serialNumber, List<String> medicationCodes) {

        log.info("About to load a drone [{}] with medication items...", serialNumber);
        Drone drone = droneRepository.findById(serialNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Drone not found: Invalid serial number"));

        boolean isLoadable = checkLoadable(drone.getBatteryCapacity());

        if (isLoadable) {
            drone.setState(DroneState.LOADING);
            final int minSize = 0;

            log.info("Fetching medication items data from MEDICATION TABLE.");
            List<Medication> medications = medicationRepository.findAllById(medicationCodes);
            if (medications.size() > minSize) {
                if(checkWeightLimit(medications, drone.getModel())) {
                    drone.setMedications(medications);
                    drone.setState(DroneState.LOADED);
                    droneRepository.save(drone);
                    log.info("Medication items data loaded.");
                    return mapper.map(drone, DroneDTO.class);
                } else {
                    log.error("Medication(s) weight exceeds drone weight limit.");
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Medication(s) weight exceeds drone weight limit ("
                            + DroneModelWeightLimit.getWeightLimit(drone.getModel()) + "gr)");
                }
            } else {
                log.error("Can not load medication(s): Invalid medication(s) code.");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can not load medication(s): Invalid medication(s) code");
            }
        } else
            log.error("Can not load medication(s): Drone battery capacity below 25%");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can not load medication(s): Drone battery capacity below 25%");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DroneDTO> getLoadableDrones() {
        log.info("About to get loadable drones...");
        List<Drone> drones = droneRepository.findDroneByState(DroneState.IDLE);
        List<DroneDTO> result = new ArrayList<>();
        if (drones != null){
            for (Drone droneValue: drones) {
                if (checkLoadable(droneValue.getBatteryCapacity())) {
                    result.add(mapper.map(droneValue, DroneDTO.class));
                }
            }
            log.info("Successfully fetched loadable drones.");
        }
        return  result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public  List<MedicationDTO> getDroneMedications(String serialNumber){
        log.error("About to get medications loaded on a drone...");
        Drone drone = droneRepository.findById(serialNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Drone not found: Invalid serial number"));

        List<Medication> meds = drone.getMedications();

        if (meds != null){
            log.info("Successfully fetched medications loaded on drone [{}].", serialNumber);
            return drone.getMedications().stream()
                    .map(medication ->
                            mapper.map(medication, MedicationDTO.class))
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getDroneBatteryLevel(String serialNumber) {
        log.info("About to get drone[{}] battery level...", serialNumber);
        Drone drone = droneRepository.findById(serialNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Drone not found: Invalid serial number"));
        log.info("Fetched drone[{}] battery level.", serialNumber);
        return drone.getBatteryCapacity();
    }


    public List<DroneDTO> getAllDrones() {
        return droneRepository.findAll()
                .stream()
                .map(drone -> mapper.map(drone, DroneDTO.class))
                .collect(Collectors.toList());
    }

    public DroneDTO getDrone(final String serialNumber) {
        DroneDTO drone1 = droneRepository.findById(serialNumber)
                .map(drone -> mapper.map(drone, DroneDTO.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return drone1;
    }

    public void update(final String serialNumber, final DroneDTO droneDTO) {
        final Drone drone = droneRepository.findById(serialNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapper.map(droneDTO, DroneDTO.class);
        droneRepository.save(drone);
    }

    public void delete(final String serialNumber) {
        droneRepository.deleteById(serialNumber);
    }

    private boolean checkWeightLimit(List<Medication> medications, String model){

        double medsWeight = 0.0;
        for (Medication medication : medications) {
            medsWeight += medication.getWeight();
        }

     return DroneModelWeightLimit.getWeightLimit(model) >= medsWeight;
    }

    private boolean checkLoadable(double batteryCapacity){
        return batteryCapacity > MIN_BATTERY_CAPACITY;
    }

}