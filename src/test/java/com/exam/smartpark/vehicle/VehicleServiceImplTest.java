package com.exam.smartpark.vehicle;

import com.exam.smartpark.exception.type.DuplicateKeyException;
import com.exam.smartpark.exception.type.UnprocessableContentException;
import com.exam.smartpark.vehicle.dto.request.CreateVehicleRequest;
import com.exam.smartpark.vehicle.dto.response.VehicleListResponse;
import com.exam.smartpark.vehicle.entity.Vehicle;
import com.exam.smartpark.vehicle.entity.VehicleType;
import com.exam.smartpark.vehicle.mapper.VehicleMapper;
import com.exam.smartpark.vehicle.repository.VehicleRepository;
import com.exam.smartpark.vehicle.service.VehicleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceImplTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private VehicleMapper vehicleMapper;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    private CreateVehicleRequest createVehicleRequest;
    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        createVehicleRequest = new CreateVehicleRequest(
                "ABC123",
                VehicleType.CAR,
                "Drenzo"
        );

        vehicle = new Vehicle(1L, "ABC123", VehicleType.CAR, "DrenzoAndrei");
    }

    @Test
    void shouldRegisterVehicleSuccessfully() {
        when(vehicleRepository.existsByLicensePlate("ABC123")).thenReturn(false);
        when(vehicleMapper.toVehicle(createVehicleRequest)).thenReturn(vehicle);

        vehicleService.registerVehicle(createVehicleRequest);

        verify(vehicleRepository).existsByLicensePlate("ABC123");
        verify(vehicleMapper).toVehicle(createVehicleRequest);
        verify(vehicleRepository).save(vehicle);
    }

    @Test
    void shouldThrowDuplicateKeyExceptionWhenLicensePlateAlreadyExists() {
        when(vehicleRepository.existsByLicensePlate("ABC123")).thenReturn(true);

        assertThatThrownBy(() -> vehicleService.registerVehicle(createVehicleRequest))
                .isInstanceOf(DuplicateKeyException.class)
                .hasMessage("There is already existing License Plate");

        verify(vehicleRepository).existsByLicensePlate("ABC123");
        verify(vehicleMapper, never()).toVehicle(any());
        verify(vehicleRepository, never()).save(any());
    }

    @Test
    void shouldThrowUnprocessableContentExceptionWhenVehicleTypeIsInvalid() {
        CreateVehicleRequest invalidRequest = new CreateVehicleRequest(
                "XYZ999",
                null,
                "Test Owner"
        );

        when(vehicleRepository.existsByLicensePlate("XYZ999")).thenReturn(false);

        assertThatThrownBy(() -> vehicleService.registerVehicle(invalidRequest))
                .isInstanceOfAny(UnprocessableContentException.class, NullPointerException.class);

        verify(vehicleRepository).existsByLicensePlate("XYZ999");
        verify(vehicleMapper, never()).toVehicle(any());
        verify(vehicleRepository, never()).save(any());
    }

    @Test
    void shouldReadVehicleListSuccessfully() {
        Vehicle vehicle1 = new Vehicle(1L, "ABC123", VehicleType.CAR, "DrenzoAndrei");

        Vehicle vehicle2 = new Vehicle(2L, "XYZ999", VehicleType.TRUCK, "Dreia Zondo");

        List<Vehicle> vehicles = List.of(vehicle1, vehicle2);

        VehicleListResponse response1 = mock(VehicleListResponse.class);
        VehicleListResponse response2 = mock(VehicleListResponse.class);

        List<VehicleListResponse> expectedResponses = List.of(response1, response2);

        when(vehicleRepository.findAll()).thenReturn(vehicles);
        when(vehicleMapper.toVehicleListResponse(vehicles)).thenReturn(expectedResponses);

        List<VehicleListResponse> actualResponses = vehicleService.readVehicleList();

        assertThat(actualResponses).isEqualTo(expectedResponses);

        verify(vehicleRepository).findAll();
        verify(vehicleMapper).toVehicleListResponse(vehicles);
    }
}
