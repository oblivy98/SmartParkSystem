package com.exam.smartpark.internal;

import com.exam.smartpark.parking.lot.entity.ParkingLotVehicleHistory;
import com.exam.smartpark.parking.lot.repository.ParkingLotVehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class ParkingMonitorScheduler {

    private static final Logger log = LoggerFactory.getLogger(ParkingMonitorScheduler.class);

    private final ParkingLotVehicleRepository parkingLotVehicleRepository;

    public ParkingMonitorScheduler(ParkingLotVehicleRepository parkingLotVehicleRepository) {
        this.parkingLotVehicleRepository = parkingLotVehicleRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void checkCurrentlyParkedVehicles() {
        Optional<List<ParkingLotVehicleHistory>> parkingLotVehicleHistories = parkingLotVehicleRepository.findByCheckOutIsNull();

        if(parkingLotVehicleHistories.isEmpty()) {
            return;
        }

        for (ParkingLotVehicleHistory history : parkingLotVehicleHistories.get()) {
            if(history.getCheckIn().plusMinutes(15).isBefore(LocalDateTime.now())) {
                log.warn("This vehicle with License Plate: "
                        + history.getVehicle().getLicensePlate()
                        + "has already lapsed at 15 minutes, removing this vehicle to this parking lot: "
                        + history.getParkingLot().getLotId() + " automatically");
            }
        }
    }
}
