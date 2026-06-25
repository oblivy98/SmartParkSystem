package com.exam.smartpark.vehicle.controller;

import com.exam.smartpark.vehicle.dto.request.CreateVehicleRequest;
import com.exam.smartpark.vehicle.dto.response.VehicleListResponse;
import com.exam.smartpark.vehicle.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PreAuthorize("hasRole('MASTER')")
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> registerVehicle(@Valid @RequestBody CreateVehicleRequest request) {
        vehicleService.registerVehicle(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('MASTER')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<VehicleListResponse>> readVehicleList() {
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.readVehicleList());
    }
}
