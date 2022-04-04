package com.drone.dispatch.entities;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Medication {

    @Id
    @Column(nullable = false, updatable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double weight;

    @Column
    private String image;

    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL, mappedBy = "medications")
    private List<Drone> drones;

}