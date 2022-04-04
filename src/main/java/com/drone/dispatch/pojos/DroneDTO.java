package com.drone.dispatch.pojos;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class DroneDTO {

    @NotNull
    @Size(max = 100)
    private String serialNumber;

    @NotNull
    private String model;

    @NotNull
    @Max(value = 500)
    private Double weightLimit;

    @NotNull
    private Double batteryCapacity;

    @NotNull
    private String state;

    private List<MedicationDTO> medications;

}