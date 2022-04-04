package com.drone.dispatch.rest;

import com.drone.dispatch.pojos.DroneDTO;
import com.drone.dispatch.pojos.MedicationDTO;
import com.drone.dispatch.service.impl.DroneServiceImpl;
import java.util.List;
import javax.validation.Valid;

import com.drone.dispatch.service.impl.MedicationServiceImpl;
import com.drone.dispatch.service.inf.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/rest/", produces = MediaType.APPLICATION_JSON_VALUE)
public class DispatchController {

    @Autowired
    private DroneService droneService;

    public DispatchController() {
    }

    @PostMapping("drone")
    public ResponseEntity<DroneDTO> registerDrone(@RequestBody @Valid DroneDTO droneDTO) {
        return ResponseEntity.ok(droneService.registerDrone(droneDTO));
    }

    @PutMapping("drone/medications")
    public ResponseEntity<DroneDTO> loadDroneMedications(@RequestParam("serialnumber") @Valid String serialNumber, @RequestParam("medicationcodes") @Valid List<String> medicationCodes) {
        return ResponseEntity.ok(droneService.loadDroneMedications(serialNumber, medicationCodes));
    }

    @GetMapping("drone/medications")
    public ResponseEntity<List<MedicationDTO>>  getDroneMedications(@RequestParam("serialnumber") @Valid String serialNumber) {
        return ResponseEntity.ok(droneService.getDroneMedications(serialNumber));
    }

    @GetMapping("drones/loadable")
    public ResponseEntity<List<DroneDTO>>   getLoadableDrones() {
        return ResponseEntity.ok(droneService.getLoadableDrones());
    }

    @GetMapping("drone/battery")
    public ResponseEntity<Double>   getDroneBatteryLevel(@RequestParam("serialnumber") @Valid String serialNumber) {
        return ResponseEntity.ok(droneService.getDroneBatteryLevel(serialNumber));
    }

}