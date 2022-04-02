package com.drone.dispatch.repos;

import com.drone.dispatch.entities.Drone;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DroneRepository extends JpaRepository<Drone, String> {
}