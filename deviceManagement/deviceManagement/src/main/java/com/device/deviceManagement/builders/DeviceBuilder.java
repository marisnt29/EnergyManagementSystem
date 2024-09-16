package com.device.deviceManagement.builders;

import com.device.deviceManagement.dtos.DeviceDTO;
import com.device.deviceManagement.entity.Account;
import com.device.deviceManagement.entity.Device;

public class DeviceBuilder {

    public static DeviceDTO toDTO(Device device) {
        return DeviceDTO.builder()
                .id(device.getId())
                .maximumConsumption(device.getMaximumConsumption())
                .description(device.getDescription())
                .location(device.getLocation())
                .accountID(device.getAccount().getAccountID())
                .build();
    }

    public static Device toEntity(DeviceDTO deviceDTO) {
        return Device.builder()
                .id(deviceDTO.getId())
                .maximumConsumption(deviceDTO.getMaximumConsumption())
                .description(deviceDTO.getDescription())
                .location(deviceDTO.getLocation())
                .account(new Account())
                .build();
    }
}
