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
import com.drone.dispatch.utils.DroneUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
@Transactional
public class DroneServiceImpl implements DroneService {

    private final double MAX_WEIGHT_LIMIT = 500.0;
    private final double MIN_BATTERY_CAPACITY = 0.25;
    private ModelMapper mapper = new ModelMapper();

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private MedicationServiceImpl medicationService;

    public DroneServiceImpl() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DroneDTO  registerDrone(DroneDTO droneDTO) {
         boolean checkDroneExist = droneRepository.existsById(droneDTO.getSerialNumber());
         Drone drone;

         if(!checkDroneExist){
             droneDTO.setWeightLimit(DroneUtil.getWeightLimit(droneDTO.getModel()));
             drone = mapper.map(droneDTO, Drone.class);
             droneRepository.save(drone);
         }else
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Drone already registered");

        return mapper.map(drone, DroneDTO.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DroneDTO loadDroneMedications(String serialNumber, List<String> medicationCodes) {
        Drone drone = droneRepository.findById(serialNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Drone not found: Invalid serial number"));

        final int minSize = 0;
        List<Medication> meds = medicationRepository.findAllById(medicationCodes);
        if (meds.size() > minSize) {
            drone.setMedications(meds);
            droneRepository.save(drone);
        }

        return mapper.map(drone, DroneDTO.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DroneDTO> getLoadableDrones() {
        List<Drone> drones = droneRepository.findDroneByState(DroneState.IDLE);

        if (drones != null){
            return drones.stream().map(drone ->
                    mapper.map(drone, DroneDTO.class))
                    .collect(Collectors.toList());
        }

        return  new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public  List<MedicationDTO> getDroneMedications(String serialNumber){
        Drone drone = droneRepository.findById(serialNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Drone not found: Invalid serial number"));

        List<Medication> meds = drone.getMedications();

        if (meds != null){
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
        Drone drone = droneRepository.findById(serialNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Drone not found: Invalid serial number"));

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

}