package com.exam.smartpark.vehicle.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
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

    @Column(name = "license_plate", nullable = false)
    @Pattern(regexp = "^[a-zA-Z0-9-]+$", message = "Column can only contain letters, numbers, and dashes.")
    @Getter
    @Setter
    private String licensePlate;

    @Column(name = "vehicle_type", columnDefinition = "VARCHAR", nullable = false)
    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private VehicleType vehicleType;

    @Column(name = "owner_name", nullable = false)
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "The field must contain only letters and spaces")
    @Getter
    @Setter
    private String ownerName;
}
