package com.exam.smartpark.parking.lot.entity;

import jakarta.persistence.*;
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

    @Column(name = "lot_id")
    @Getter
    @Setter
    private String lotId;

    @Column(name = "location")
    @Getter
    @Setter
    private String location;

    @Column(name = "capacity")
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
