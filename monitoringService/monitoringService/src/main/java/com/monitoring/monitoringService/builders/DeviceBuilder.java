package com.monitoring.monitoringService.builders;

import com.monitoring.monitoringService.dto.DeviceDTO;

import com.monitoring.monitoringService.model.Device;

public class DeviceBuilder {

    public static DeviceDTO toDTO(Device device) {
        return DeviceDTO.builder()
                .id(device.getId())
                .deviceID(device.getDeviceID())
                .maxConsumption(device.getMaxConsumption())
                .build();
    }

    public static Device toEntity(DeviceDTO deviceDTO) {
        return Device.builder()
                .id(deviceDTO.getId())
                .deviceID(deviceDTO.getDeviceID())
                .maxConsumption(deviceDTO.getMaxConsumption())
                .build();
    }
}