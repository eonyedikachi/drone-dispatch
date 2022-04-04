package com.drone.dispatch.service.inf;

import com.drone.dispatch.pojos.DroneDTO;
import com.drone.dispatch.pojos.MedicationDTO;

import java.util.List;

public interface DroneService {

    /**
     ** Register a new Drone
     * @param droneDTO - drone body with values to he added
     * @return drone values added to db
     */
    DroneDTO registerDrone(DroneDTO droneDTO);

    /**
     ** Load a Drone with medication items
     * @param serialNumber - drone serial number
     * @param medicationCodes - medication items code
     * @return drone values added to db
     */
    DroneDTO loadDroneMedications(String serialNumber, List<String> medicationCodes);

  /**
     ** Load a Drone with medication items
     * @param serialNumber - drone serial number
     * @return drone values added to db
     */
    List<MedicationDTO> getDroneMedications(String serialNumber);

    /**
     **Get available drones for loading
     * @return list of available drones for loading
     */
    List<DroneDTO> getLoadableDrones();

    /**
     * *Get battery level for a given drone
     * @param serialNumber serial number for the specified drone
     * @return battery level of the drone with the serial number provided
     */
    double getDroneBatteryLevel(String serialNumber);

}
