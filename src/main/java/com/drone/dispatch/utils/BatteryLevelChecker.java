package com.drone.dispatch.utils;

import com.drone.dispatch.pojos.DroneDTO;
import com.drone.dispatch.service.impl.DroneServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Slf4j
@Configuration
@EnableScheduling
public class BatteryLevelChecker {

    @Autowired
    private DroneServiceImpl droneService;

    //    @Scheduled(cron = "[Seconds] [Minutes] [Hours] [Day of month] [Month] [Day of week] [Year]")
    @Scheduled(cron = "00 05 * * * ?")
    public void batteryLevelChecker(){
        log.info("Checking all drones battery level");
        List<DroneDTO> drones = droneService.getAllDrones();

        if (drones != null){
            for (DroneDTO drone: drones) {
                log.info("{} drone with serial number [{}], has {}% battery capacity",
                        drone.getModel(),
                        drone.getSerialNumber(),
                        drone.getBatteryCapacity()*100);
            }
        } else
            log.info("No Drone found!");

        log.info("End of battery check");
    }
}
