package com.monitoring.monitoringService.services;

import com.monitoring.monitoringService.builders.DeviceConsumptionBuilder;
import com.monitoring.monitoringService.dto.DeviceConsumptionDTO;
import com.monitoring.monitoringService.model.DeviceConsumption;
import com.monitoring.monitoringService.repository.DeviceConsumptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DeviceConsumptionService {

    private final DeviceConsumptionRepository deviceConsumptionRepository;



    public DeviceConsumptionDTO getDeviceConsumptionById(Long id) {
        DeviceConsumption deviceConsumption = deviceConsumptionRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Invalid device ID:"+id));
        return DeviceConsumptionBuilder.toDTO(deviceConsumption);
    }

    public List<DeviceConsumptionDTO> getDeviceConsumptionsByDeviceID(Long deviceID) {
        List<DeviceConsumption> deviceConsumptions = deviceConsumptionRepository.getAllDeviceConsumptionsByDeviceID(deviceID);
        List<DeviceConsumptionDTO> deviceConsumptionDTOs = new ArrayList<>();
        for (DeviceConsumption deviceConsumption : deviceConsumptions) {
            deviceConsumptionDTOs.add(DeviceConsumptionBuilder.toDTO(deviceConsumption));
        }
        return deviceConsumptionDTOs;
    }

    public DeviceConsumptionDTO save(DeviceConsumptionDTO deviceConsumptionDTO) {
        DeviceConsumption deviceConsumption = DeviceConsumptionBuilder.toEntity(deviceConsumptionDTO);
        DeviceConsumption savedDeviceConsumption = deviceConsumptionRepository.save(deviceConsumption);
        return DeviceConsumptionBuilder.toDTO(savedDeviceConsumption);
    }



    public void deleteDeviceConsumption(Long id) {
        deviceConsumptionRepository.deleteById(id);
    }
}