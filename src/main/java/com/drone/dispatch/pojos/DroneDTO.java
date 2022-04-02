package com.drone.dispatch.pojos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DroneDTO {

    @NotNull
    @Size(max = 100)
    private String serialNumber;

    @NotNull
    @Size(max = 255)
    private String model;

    @NotNull
    private Double weight;

    @NotNull
    private Double batteryCapacity;

    @NotNull
    @Size(max = 100)
    private String state;

}