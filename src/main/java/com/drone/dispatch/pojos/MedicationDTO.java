package com.drone.dispatch.pojos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MedicationDTO {

    @NotNull
    @Pattern(regexp = "^[A-Z0-9_]*$")
    private String code;

    @NotNull
    @Pattern(regexp = "^[\\w-]+$")
    private String name;

    @NotNull
    private Double weight;

    private String image;

}