package com.drone.dispatch.service;

import com.drone.dispatch.entities.Medication;
import com.drone.dispatch.pojos.MedicationDTO;
import com.drone.dispatch.repos.MedicationRepository;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class MedicationService {

    @Autowired
    private MedicationRepository medicationRepository;

    public MedicationService() {

    }

    public List<MedicationDTO> findAll() {
        return medicationRepository.findAll()
                .stream()
                .map(medication -> mapToDTO(medication, new MedicationDTO()))
                .collect(Collectors.toList());
    }

    public MedicationDTO get(final String code) {
        return medicationRepository.findById(code)
                .map(medication -> mapToDTO(medication, new MedicationDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public String create(final MedicationDTO medicationDTO) {
        final Medication medication = new Medication();
        mapToEntity(medicationDTO, medication);
        return medicationRepository.save(medication).getCode();
    }

    public void update(final String code, final MedicationDTO medicationDTO) {
        final Medication medication = medicationRepository.findById(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(medicationDTO, medication);
        medicationRepository.save(medication);
    }

    public void delete(final String code) {
        medicationRepository.deleteById(code);
    }

    private MedicationDTO mapToDTO(final Medication medication, final MedicationDTO medicationDTO) {
        medicationDTO.setCode(medication.getCode());
        medicationDTO.setName(medication.getName());
        medicationDTO.setWeight(medication.getWeight());
        medicationDTO.setImage(medication.getImage());
        return medicationDTO;
    }

    private Medication mapToEntity(final MedicationDTO medicationDTO, final Medication medication) {
        medication.setCode(medicationDTO.getCode());
        medication.setName(medicationDTO.getName());
        medication.setWeight(medicationDTO.getWeight());
        medication.setImage(medicationDTO.getImage());
        return medication;
    }

}