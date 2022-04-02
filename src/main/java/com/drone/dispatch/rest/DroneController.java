package com.drone.dispatch.rest;

import com.drone.dispatch.pojos.DroneDTO;
import com.drone.dispatch.service.DroneService;
import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/drones", produces = MediaType.APPLICATION_JSON_VALUE)
public class DroneController {

    @Autowired
    private DroneService droneService;

    public DroneController() {
    }

    @GetMapping
    public ResponseEntity<List<DroneDTO>> getAllDrones() {
        return ResponseEntity.ok(droneService.findAll());
    }

    @GetMapping("/{serialNumber}")
    public ResponseEntity<DroneDTO> getDrone(@PathVariable final String serialNumber) {
        return ResponseEntity.ok(droneService.get(serialNumber));
    }

    @PostMapping
    public ResponseEntity<Void> createDrone(@RequestBody @Valid final DroneDTO droneDTO) {
        droneService.create(droneDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{serialNumber}")
    public ResponseEntity<Void> updateDrone(@PathVariable final String serialNumber,
                                            @RequestBody @Valid final DroneDTO droneDTO) {
        droneService.update(serialNumber, droneDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{serialNumber}")
    public ResponseEntity<Void> deleteDrone(@PathVariable final String serialNumber) {
        droneService.delete(serialNumber);
        return ResponseEntity.noContent().build();
    }

}