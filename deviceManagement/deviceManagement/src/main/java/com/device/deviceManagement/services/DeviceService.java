package com.device.deviceManagement.services;

import com.device.deviceManagement.dtos.DeviceDTO;

import java.util.List;

public interface DeviceService {

    DeviceDTO getDeviceById(Long id);
    DeviceDTO saveDevice(DeviceDTO device);
    void updateDevice(DeviceDTO device);
    void deleteDevice(DeviceDTO device);
    List<DeviceDTO> getAllDevices();
}
