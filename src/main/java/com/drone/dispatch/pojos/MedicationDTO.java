package com.drone.dispatch.pojos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MedicationDTO {

    @NotNull
    @Size(max = 255)
    private String code;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    private Double weight;

    @Size(max = 255)
    private String image;

}