package com.drone.dispatch.service.inf;

import com.drone.dispatch.pojos.MedicationDTO;

import java.util.List;

public interface MedicationService {

    /**
     * Get all medications
     * @return list of all medications
     */
    List<MedicationDTO> getAllMedications();

    /**
     ** Get a list of medication items
     * @param codes - medication codes
     * @return list of medication items
     */
    List<MedicationDTO> getMedications(List<String> codes);

    /**
     ** Get a particular medication item
     * @param code - medication's code
     * @return medication item
     */
    MedicationDTO getMedication(String code);


    /**
     ** Add a medication item
     * @param medicationDTO
     * @return medication item added
     */
    MedicationDTO addMedication(MedicationDTO medicationDTO);
}
