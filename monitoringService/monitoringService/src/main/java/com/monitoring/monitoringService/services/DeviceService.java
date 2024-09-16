package com.monitoring.monitoringService.services;

import com.monitoring.monitoringService.builders.DeviceBuilder;
import com.monitoring.monitoringService.dto.DeviceDTO;
import com.monitoring.monitoringService.model.Device;
import com.monitoring.monitoringService.repository.DeviceRepository;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;


    public DeviceDTO getDeviceByDeviceID(Long id) {
        Device device = deviceRepository.findByDeviceID(id)
                .orElseThrow(()->new IllegalArgumentException("Invalid device ID:"+id));
        return DeviceBuilder.toDTO(device);
    }

    public DeviceDTO saveDevice(DeviceDTO deviceDTO) {
        Device device = DeviceBuilder.toEntity(deviceDTO);
        Device savedDevice = deviceRepository.save(device);
        return DeviceBuilder.toDTO(savedDevice);
    }



    public void deleteDevice(Long id) {
        deviceRepository.deleteById(id);
    }
}