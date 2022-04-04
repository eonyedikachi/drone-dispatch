package com.drone.dispatch.service.impl;

import com.drone.dispatch.entities.Medication;
import com.drone.dispatch.pojos.MedicationDTO;
import com.drone.dispatch.repos.MedicationRepository;
import java.util.List;
import java.util.stream.Collectors;

import com.drone.dispatch.service.inf.MedicationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class MedicationServiceImpl implements MedicationService {

    @Autowired
    private MedicationRepository medicationRepository;

    private ModelMapper mapper = new ModelMapper();

    public MedicationServiceImpl() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MedicationDTO> getAllMedications() {
        return medicationRepository.findAll()
                .stream()
                .map(medication -> mapper.map(medication, MedicationDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MedicationDTO> getMedications(List<String> codes) {
   return medicationRepository.findAllById(codes)
           .stream()
           .map(medication -> mapper.map(medication, MedicationDTO.class))
           .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MedicationDTO getMedication(final String code) {
        return medicationRepository.findById(code)
                .map(medication -> mapper.map(medication, MedicationDTO.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MedicationDTO addMedication(final MedicationDTO medicationDTO) {
        Medication medication = mapper.map(medicationDTO, Medication.class);
         medicationRepository.save(medication);
        return medicationDTO;
    }

    public void update(String code, MedicationDTO medicationDTO) {
        Medication medication = medicationRepository.findById(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        medicationRepository.save(mapper.map(medicationDTO, Medication.class));
    }

    public void delete(String code) {
        medicationRepository.deleteById(code);
    }

}