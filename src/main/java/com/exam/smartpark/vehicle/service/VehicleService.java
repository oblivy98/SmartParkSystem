package com.exam.smartpark.vehicle.service;

import com.exam.smartpark.vehicle.dto.request.CreateVehicleRequest;
import com.exam.smartpark.vehicle.dto.response.VehicleListResponse;

import java.util.List;

public interface VehicleService {

    void registerVehicle(CreateVehicleRequest request);

    List<VehicleListResponse> readVehicleList();
}
