package com.drone.dispatch.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;



@Getter
@AllArgsConstructor
public enum DroneUtil {

    LIGHT_WEIGHT("Lightweight",200.0),
    MIDDLE_WEIGHT("Middleweight",300.0),
    CRUISER_WEIGHT("Cruiserweight",400.0),
    HEAVY_WEIGHT("Heavyweight",500.0);

    private String model;
    private double weightLimit;

    public static double getWeightLimit(String model) {
        for (DroneUtil b : DroneUtil.values()) {
            if (String.valueOf(b.model).equals(model)) {
                return b.weightLimit;
            }
        }
        return HEAVY_WEIGHT.weightLimit;
    }
}
