package com.drone.dispatch.entities;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Drone {

    @Id
    @Column(length = 100)
    private String serialNumber;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Double weightLimit;

    @Column(nullable = false)
    private Double batteryCapacity;

    @Column(nullable = false)
    private String state;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "drone_medications",
            joinColumns = @JoinColumn(name = "drone_serial_number"),
            inverseJoinColumns = @JoinColumn(name = "medication_code")
    )
    private List<Medication> medications;

}