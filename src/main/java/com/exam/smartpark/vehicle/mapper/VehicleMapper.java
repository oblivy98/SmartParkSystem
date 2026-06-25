package com.exam.smartpark.vehicle.mapper;

import com.exam.smartpark.vehicle.dto.request.CreateVehicleRequest;
import com.exam.smartpark.vehicle.dto.response.VehicleListResponse;
import com.exam.smartpark.vehicle.entity.Vehicle;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VehicleMapper {

    public Vehicle toVehicle(CreateVehicleRequest request) {
        Vehicle vehicle = new Vehicle();

        vehicle.setLicensePlate(request.licensePlate());
        vehicle.setVehicleType(request.vehicleType());
        vehicle.setOwnerName(request.ownerName());

        return vehicle;
    }

    public List<VehicleListResponse> toVehicleListResponse(List<Vehicle> vehicleList) {
        return new ArrayList<>(
                vehicleList.stream().map(vehicle -> new VehicleListResponse(
                                            vehicle.getLicensePlate(),
                                            vehicle.getVehicleType(),
                                            vehicle.getOwnerName()
                )).toList());
    }
}
