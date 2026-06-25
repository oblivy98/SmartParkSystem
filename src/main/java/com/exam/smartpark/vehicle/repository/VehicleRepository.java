package com.exam.smartpark.vehicle.repository;

import com.exam.smartpark.vehicle.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    boolean existsByLicensePlate(String licensePlate);

    Optional<Vehicle> findByLicensePlate(String licensePlate);
}
