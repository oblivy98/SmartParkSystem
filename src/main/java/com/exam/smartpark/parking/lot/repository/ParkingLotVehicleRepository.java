package com.exam.smartpark.parking.lot.repository;

import com.exam.smartpark.parking.lot.entity.ParkingLot;
import com.exam.smartpark.parking.lot.entity.ParkingLotVehicleHistory;
import com.exam.smartpark.vehicle.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParkingLotVehicleRepository extends JpaRepository<ParkingLotVehicleHistory, Long> {

    Optional<List<ParkingLotVehicleHistory>> findByParkingLotAndCheckOutIsNull(ParkingLot parkingLot);

    Optional<ParkingLotVehicleHistory> findByVehicleAndParkingLotAndCheckOutIsNull(Vehicle vehicle, ParkingLot parkingLot);

    Optional<List<ParkingLotVehicleHistory>> findByCheckOutIsNull();

    boolean existsByVehicleAndCheckOutIsNull(Vehicle vehicle);
}
