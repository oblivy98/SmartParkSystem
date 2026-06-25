package com.exam.smartpark.parking.lot.entity;

import com.exam.smartpark.vehicle.entity.Vehicle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "parking_lot_vehicle_history")
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLotVehicleHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parking_lot_vehicle_id")
    @Getter
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parking_lot_id", nullable = false)
    @Getter
    @Setter
    private ParkingLot parkingLot;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_id", nullable = false)
    @Getter
    @Setter
    private Vehicle vehicle;

    @Column(name = "check_in")
    @Getter
    @Setter
    private LocalDateTime checkIn;

    @Column(name = "check_out")
    @Getter
    @Setter
    private LocalDateTime checkOut;

    @Column(name = "cost_per_minute_snapshot")
    @Getter
    @Setter
    private BigDecimal costPerMinuteSnapshot;

    @Column(name = "total_parking_cost")
    @Getter
    @Setter
    private BigDecimal totalParkingCost;

    @Column(name = "paid_status")
    @Getter
    @Setter
    private Boolean paidStatus = false;
}
