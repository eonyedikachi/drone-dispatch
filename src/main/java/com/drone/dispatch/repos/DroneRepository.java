package com.drone.dispatch.repos;

import com.drone.dispatch.entities.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DroneRepository extends JpaRepository<Drone, String> {
    List<Drone> findDroneByState(String state);
}