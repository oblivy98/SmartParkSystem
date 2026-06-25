package com.exam.smartpark.parking.lot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "parking_lot")
@AllArgsConstructor
@NoArgsConstructor
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parking_lot_id")
    @Getter
    private Long parkingLotId;

    @Size(max = 50, message = "Lot id must not exceed 50 characters")
    @Column(name = "lot_id", length = 50, nullable = false)
    @Getter
    @Setter
    private String lotId;

    @Column(name = "location", nullable = false)
    @Getter
    @Setter
    private String location;

    @Column(name = "capacity", nullable = false)
    @Getter
    @Setter
    private Integer capacity;

    @Column(name = "occupied_space")
    @Getter
    @Setter
    private Integer occupiedSpace;

    @Column(name = "cost_per_minute")
    @Getter
    @Setter
    private BigDecimal costPerMinute;
}
