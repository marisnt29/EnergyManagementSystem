package com.monitoring.monitoringService.builders;

import com.monitoring.monitoringService.dto.DeviceConsumptionDTO;
import com.monitoring.monitoringService.model.DeviceConsumption;

public class DeviceConsumptionBuilder {

    public static DeviceConsumptionDTO toDTO(DeviceConsumption deviceConsumption) {
        return DeviceConsumptionDTO.builder()
                .id(deviceConsumption.getId())
                .deviceID(deviceConsumption.getDeviceID())
                .energyConsumption(deviceConsumption.getEnergyConsumption())
                .timestamp(deviceConsumption.getTimestamp())
                .build();
    }

    public static DeviceConsumption toEntity(DeviceConsumptionDTO deviceConsumptionDTO) {
        return DeviceConsumption.builder()
                .id(deviceConsumptionDTO.getId())
                .deviceID(deviceConsumptionDTO.getDeviceID())
                .energyConsumption(deviceConsumptionDTO.getEnergyConsumption())
                .timestamp(deviceConsumptionDTO.getTimestamp())
                .build();
    }
}