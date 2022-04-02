package com.drone.dispatch.repos;

import com.drone.dispatch.entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MedicationRepository extends JpaRepository<Medication, String> {
}