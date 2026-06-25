package com.exam.smartpark.vehicle.service;

import com.exam.smartpark.exception.type.DuplicateKeyException;
import com.exam.smartpark.exception.type.UnprocessableContentException;
import com.exam.smartpark.vehicle.dto.request.CreateVehicleRequest;
import com.exam.smartpark.vehicle.dto.response.VehicleListResponse;
import com.exam.smartpark.vehicle.entity.Vehicle;
import com.exam.smartpark.vehicle.entity.VehicleType;
import com.exam.smartpark.vehicle.mapper.VehicleMapper;
import com.exam.smartpark.vehicle.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService{

    private final VehicleRepository vehicleRepository;

    private final VehicleMapper vehicleMapper;

    public VehicleServiceImpl(  VehicleRepository vehicleRepository,
                                VehicleMapper vehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
    }

    @Override
    public void registerVehicle(CreateVehicleRequest request) {

        if(vehicleRepository.existsByLicensePlate(request.licensePlate())) {
            throw new DuplicateKeyException("There is already existing License Plate");
        }

        if(!VehicleType.isValid(request.vehicleType().name())) {
            throw new UnprocessableContentException("The vehicle type is unrecognizable, " +
                    "only CAR, MOTORCYCLE, and TRUCK is allowed");
        }

        Vehicle vehicle = vehicleMapper.toVehicle(request);
        vehicleRepository.save(vehicle);
    }

    @Override
    public List<VehicleListResponse> readVehicleList() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return vehicleMapper.toVehicleListResponse(vehicles);
    }

}
