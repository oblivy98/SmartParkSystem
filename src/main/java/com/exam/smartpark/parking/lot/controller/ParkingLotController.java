package com.exam.smartpark.parking.lot.controller;

import com.exam.smartpark.parking.lot.dto.request.CreateParkingLotRequest;
import com.exam.smartpark.parking.lot.dto.response.AvailableParkingLotResponse;
import com.exam.smartpark.parking.lot.dto.response.CheckoutParkingResponse;
import com.exam.smartpark.parking.lot.dto.response.ParkingLotDetailsResponse;
import com.exam.smartpark.parking.lot.service.ParkingLotService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking")
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    public ParkingLotController(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }

    @PreAuthorize("hasRole('MASTER')")
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> registerParkingLot(@Valid @RequestBody CreateParkingLotRequest request) {
        parkingLotService.registerParkingLot(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('MASTER')")
    @PostMapping(value = "/check-in")
    ResponseEntity<Void> checkinVehicleInParking(@RequestParam String licensePlate,
                                                 @RequestParam String lotId) {
        parkingLotService.checkinParking(licensePlate, lotId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PreAuthorize("hasRole('MASTER')")
    @PostMapping(   value = "/check-out",
                    produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CheckoutParkingResponse> checkoutVehicleInParking(@RequestParam String licensePlate,
                                                                     @RequestParam String lotId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(parkingLotService.checkoutParking(licensePlate, lotId));
    }

    @PreAuthorize("hasRole('MASTER')")
    @GetMapping(    value = "/available",
                    produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<AvailableParkingLotResponse>> readAvailableParkingLots() {
        return ResponseEntity.status(HttpStatus.OK).body(parkingLotService.readAvailableParkingLots());
    }

    @PreAuthorize("hasRole('MASTER')")
    @GetMapping(    produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ParkingLotDetailsResponse> readParkingLotDetail(@RequestParam String lotId) {
        return ResponseEntity.status(HttpStatus.OK).body(parkingLotService.readParkingLotDetails(lotId));
    }

}
