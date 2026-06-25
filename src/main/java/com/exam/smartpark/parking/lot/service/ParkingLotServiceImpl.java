package com.exam.smartpark.parking.lot.service;

import com.exam.smartpark.exception.type.DuplicateKeyException;
import com.exam.smartpark.exception.type.ResourceNotFoundException;
import com.exam.smartpark.exception.type.UnprocessableContentException;
import com.exam.smartpark.parking.lot.dto.request.CreateParkingLotRequest;
import com.exam.smartpark.parking.lot.dto.response.AvailableParkingLotResponse;
import com.exam.smartpark.parking.lot.dto.response.CheckoutParkingResponse;
import com.exam.smartpark.parking.lot.dto.response.ParkingLotDetailsResponse;
import com.exam.smartpark.parking.lot.entity.ParkingLot;
import com.exam.smartpark.parking.lot.entity.ParkingLotVehicleHistory;
import com.exam.smartpark.parking.lot.mapper.ParkingLotMapper;
import com.exam.smartpark.parking.lot.mapper.ParkingLotVehicleMapper;
import com.exam.smartpark.parking.lot.repository.ParkingLotRepository;
import com.exam.smartpark.parking.lot.repository.ParkingLotVehicleRepository;
import com.exam.smartpark.vehicle.entity.Vehicle;
import com.exam.smartpark.vehicle.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ParkingLotServiceImpl implements ParkingLotService{

    private final ParkingLotRepository parkingLotRepository;

    private final ParkingLotMapper parkingLotMapper;

    private final VehicleRepository vehicleRepository;

    private final ParkingLotVehicleMapper parkingLotVehicleMapper;

    private final ParkingLotVehicleRepository parkingLotVehicleRepository;

    public ParkingLotServiceImpl(   ParkingLotRepository parkingLotRepository,
                                    ParkingLotMapper parkingLotMapper,
                                    VehicleRepository vehicleRepository,
                                    ParkingLotVehicleMapper parkingLotVehicleMapper,
                                    ParkingLotVehicleRepository parkingLotVehicleRepository) {
        this.parkingLotRepository = parkingLotRepository;
        this.parkingLotMapper = parkingLotMapper;
        this.vehicleRepository = vehicleRepository;
        this.parkingLotVehicleMapper = parkingLotVehicleMapper;
        this.parkingLotVehicleRepository = parkingLotVehicleRepository;
    }

    @Override
    public void registerParkingLot(CreateParkingLotRequest request) {

        if (parkingLotRepository.existsByLotId(request.lotId())) {
            throw new DuplicateKeyException("There is already existing Parking Lot ID");
        }

        ParkingLot parkingLot = parkingLotMapper.toParkingLot(request);
        parkingLotRepository.save(parkingLot);
    }

    @Transactional
    @Override
    public void checkinParking(String licensePlate, String lotId) {
        Vehicle vehicle = vehicleRepository .findByLicensePlate(licensePlate)
                                            .orElseThrow( () -> new ResourceNotFoundException("No vehicle found"));
        ParkingLot parkingLot = parkingLotRepository.findByLotId(lotId)
                                                    .orElseThrow(() -> new ResourceNotFoundException("No Parking Lot not found"));

        //Validations
        if (parkingLot.getCapacity().equals(parkingLot.getOccupiedSpace())) {
            throw new UnprocessableContentException("There is no available parking space in this parking lot");
        }
        if (parkingLotVehicleRepository.existsByVehicleAndCheckOutIsNull(vehicle)) {
            throw new UnprocessableContentException("The vehicle is currently parked");
        }

        ParkingLotVehicleHistory parkingLotVehicleHistory = parkingLotVehicleMapper
                .toParkingLotVehicleHistory(vehicle, parkingLot);

        parkingLotVehicleRepository.save(parkingLotVehicleHistory);

        parkingLot.setOccupiedSpace(parkingLot.getOccupiedSpace() + 1);
        parkingLotRepository.save(parkingLot);
    }

    @Transactional
    @Override
    public CheckoutParkingResponse checkoutParking(String licensePlate, String lotId) {
        Vehicle vehicle = vehicleRepository.findByLicensePlate(licensePlate)
                .orElseThrow(() -> new ResourceNotFoundException("No vehicle found"));
        ParkingLot parkingLot = parkingLotRepository.findByLotId(lotId)
                .orElseThrow(() -> new ResourceNotFoundException("No Parking lot found"));
        ParkingLotVehicleHistory parkingLotVehicleHistory = parkingLotVehicleRepository
                .findByVehicleAndParkingLotAndCheckOutIsNull(vehicle, parkingLot)
                .orElseThrow(() -> new ResourceNotFoundException("No parked vehicle in this parking lot"));

        parkingLotVehicleHistory.setCheckOut(LocalDateTime.now());

        Long minutes = getTotalMinutes(parkingLotVehicleHistory);

        parkingLotVehicleHistory.setCostPerMinuteSnapshot(parkingLotVehicleHistory.getParkingLot().getCostPerMinute());
        parkingLotVehicleHistory.setTotalParkingCost(getTotalParkingCost(parkingLotVehicleHistory, minutes));

        parkingLotVehicleRepository.save(parkingLotVehicleHistory);

        parkingLot.setOccupiedSpace(parkingLot.getOccupiedSpace() - 1);

        return new CheckoutParkingResponse(parkingLotVehicleHistory.getTotalParkingCost());
    }

    @Override
    public AvailableParkingLotResponse readAvailableParkingLot(String lotId) {
        ParkingLot parkingLot = parkingLotRepository.findByLotId(lotId)
                .orElseThrow(() -> new ResourceNotFoundException("No parking lot found"));

        return parkingLotMapper.toAvailableParkingLot(parkingLot);
    }

    @Override
    public ParkingLotDetailsResponse readParkingLotDetails(String lotId) {
        ParkingLot parkingLot = parkingLotRepository.findByLotId(lotId)
                .orElseThrow(() -> new ResourceNotFoundException("No parking lot found"));

        List<ParkingLotVehicleHistory> parkingLotVehicleHistory = parkingLotVehicleRepository
                .findByParkingLotAndCheckOutIsNull(parkingLot)
                .orElseThrow(() -> new ResourceNotFoundException("No existing parked vehicle in this lot"));

        if(parkingLotVehicleHistory.isEmpty()) {
            return null;
        }
        return parkingLotMapper.toCurrentlyParkedVehicleInParkingLot(parkingLotVehicleHistory);
    }

    private Long getTotalMinutes(ParkingLotVehicleHistory parkingLotVehicleHistory) {
        return Duration.between(parkingLotVehicleHistory.getCheckIn(),
                                parkingLotVehicleHistory.getCheckOut()).toMinutes() % 60;
    }

    private BigDecimal getTotalParkingCost(ParkingLotVehicleHistory parkingLotVehicleHistory, Long minutes) {
        return parkingLotVehicleHistory .getCostPerMinuteSnapshot()
                                        .multiply(BigDecimal.valueOf(minutes));
    }
}
