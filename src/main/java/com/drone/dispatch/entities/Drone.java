package com.drone.dispatch.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Drone {

    @Id
    @Column(nullable = false, updatable = false, length = 100)
    private String serialNumber;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private Double batteryCapacity;

    @Column(nullable = false, length = 100)
    private String state;

}