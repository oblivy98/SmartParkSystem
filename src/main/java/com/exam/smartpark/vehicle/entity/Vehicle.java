package com.exam.smartpark.vehicle.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vehicle")
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {

    @Id
    @Column(name = "vehicle_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long vehicleId;

    @Column(name = "license_plate")
    @Getter
    @Setter
    private String licensePlate;

    @Column(name = "vehicle_type", columnDefinition = "VARCHAR")
    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private VehicleType vehicleType;

    @Column(name = "owner_name")
    @Getter
    @Setter
    private String ownerName;
}
