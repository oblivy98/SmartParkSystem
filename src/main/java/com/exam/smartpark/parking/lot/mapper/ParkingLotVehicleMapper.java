package com.exam.smartpark.parking.lot.mapper;

import com.exam.smartpark.parking.lot.entity.ParkingLot;
import com.exam.smartpark.parking.lot.entity.ParkingLotVehicleHistory;
import com.exam.smartpark.vehicle.entity.Vehicle;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ParkingLotVehicleMapper {

    public ParkingLotVehicleHistory toParkingLotVehicleHistory(Vehicle vehicle,
                                                        ParkingLot parkingLot) {
        ParkingLotVehicleHistory parkingLotVehicleHistory = new ParkingLotVehicleHistory();

        parkingLotVehicleHistory.setVehicle(vehicle);
        parkingLotVehicleHistory.setParkingLot(parkingLot);
        parkingLotVehicleHistory.setCheckIn(LocalDateTime.now());

        return parkingLotVehicleHistory;
    }
}
