package com.exam.smartpark.parking.lot.mapper;

import com.exam.smartpark.parking.lot.dto.request.CreateParkingLotRequest;
import com.exam.smartpark.parking.lot.dto.response.AvailableParkingLotResponse;
import com.exam.smartpark.parking.lot.dto.response.ParkingLotDetailsResponse;
import com.exam.smartpark.parking.lot.dto.response.VehiclesInParkingLotResponse;
import com.exam.smartpark.parking.lot.entity.ParkingLot;
import com.exam.smartpark.parking.lot.entity.ParkingLotVehicleHistory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ParkingLotMapper {

    public ParkingLot toParkingLot(CreateParkingLotRequest request) {
        ParkingLot parkingLot = new ParkingLot();

        parkingLot.setLotId(request.lotId());
        parkingLot.setLocation(request.location());
        parkingLot.setCapacity(request.capacity());
        parkingLot.setCostPerMinute(request.costPerMinute());
        parkingLot.setOccupiedSpace(0);

        return parkingLot;
    }

    public List<AvailableParkingLotResponse> toAvailableParkingLotList(List<ParkingLot> parkingLots) {
        return parkingLots.stream()
                .map(parkingLot -> new AvailableParkingLotResponse(
                        parkingLot.getLotId(),
                        parkingLot.getCapacity(),
                        parkingLot.getOccupiedSpace()
                )).toList();
    }

    public ParkingLotDetailsResponse toCurrentlyParkedVehicleInParkingLot(List<ParkingLotVehicleHistory> parkingLotVehicleHistoryList) {
        return new ParkingLotDetailsResponse(
                parkingLotVehicleHistoryList.get(0).getParkingLot().getLotId(),
                parkingLotVehicleHistoryList.stream().map(history -> new VehiclesInParkingLotResponse(
                        history.getVehicle().getLicensePlate(),
                        history.getVehicle().getOwnerName(),
                        Duration.between(history.getCheckIn(), LocalDateTime.now()).toMinutes()
                )).toList()
        );
    }
}
