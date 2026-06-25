package com.exam.smartpark.parking.lot.repository;

import com.exam.smartpark.parking.lot.entity.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {

    boolean existsByLotId(String lotId);

    Optional<ParkingLot> findByLotId(String lotId);
}
