package com.drone.dispatch.service.impl;

import com.drone.dispatch.entities.Drone;
import com.drone.dispatch.pojos.DroneDTO;
import com.drone.dispatch.repos.DroneRepository;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class DroneServiceImpl {

    private final double MAX_WEIGHT_LIMIT = 500.0;

    @Autowired
    private DroneRepository droneRepository;

    public DroneServiceImpl() {
    }

    public List<DroneDTO> findAll() {
        return droneRepository.findAll()
                .stream()
                .map(drone -> mapToDTO(drone, new DroneDTO()))
                .collect(Collectors.toList());
    }

    public DroneDTO get(final String serialNumber) {
        return droneRepository.findById(serialNumber)
                .map(drone -> mapToDTO(drone, new DroneDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public String registerDrone(DroneDTO droneDTO) {

        final Drone drone = new Drone();
        mapToEntity(droneDTO, drone);
        return droneRepository.save(drone).getSerialNumber();
    }

    public void update(final String serialNumber, final DroneDTO droneDTO) {
        final Drone drone = droneRepository.findById(serialNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(droneDTO, drone);
        droneRepository.save(drone);
    }

    public void delete(final String serialNumber) {
        droneRepository.deleteById(serialNumber);
    }

    private DroneDTO mapToDTO(final Drone drone, final DroneDTO droneDTO) {
        droneDTO.setSerialNumber(drone.getSerialNumber());
        droneDTO.setModel(drone.getModel());
        droneDTO.setWeightLimit(drone.getWeightLimit());
        droneDTO.setBatteryCapacity(drone.getBatteryCapacity());
        droneDTO.setState(drone.getState());
        return droneDTO;
    }

    private Drone mapToEntity(final DroneDTO droneDTO, final Drone drone) {
        drone.setSerialNumber(droneDTO.getSerialNumber());
        drone.setModel(droneDTO.getModel());
        drone.setWeightLimit(droneDTO.getWeightLimit());
        drone.setBatteryCapacity(droneDTO.getBatteryCapacity());
        drone.setState(droneDTO.getState());
        return drone;
    }

}