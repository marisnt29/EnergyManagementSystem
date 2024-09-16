package com.device.deviceManagement.services;

import com.device.deviceManagement.builders.DeviceBuilder;
import com.device.deviceManagement.dtos.DeviceDTO;
import com.device.deviceManagement.dtos.RequestDTO;
import com.device.deviceManagement.entity.Account;
import com.device.deviceManagement.entity.Device;
import com.device.deviceManagement.repository.AccountRepository;
import com.device.deviceManagement.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final AccountRepository accountRepository;
    private final RestTemplate restTemplate;

    @Value("${monitoring.image.name}")
    private String monitoringImageName;

    @Value("${monitoring.server.context.path}")
    private String serverContextPath;

    @Override
    public DeviceDTO getDeviceById(Long id) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid device ID: " + id));

        return DeviceBuilder.toDTO(device);
    }

    @Override
    public DeviceDTO saveDevice(DeviceDTO deviceDTO) {
        Device newDevice = DeviceBuilder.toEntity(deviceDTO);
        Account newAccount = accountRepository.findAccountByAccountID(deviceDTO.getAccountID())
                .orElseThrow(() -> new IllegalArgumentException("Invalid account ID: " + deviceDTO.getAccountID()));
        newDevice.setAccount(newAccount);
        deviceRepository.save(newDevice);

//        RequestDTO requestBody = new RequestDTO();
//        requestBody.setDeviceID(newDevice.getId());
//        requestBody.setMaxConsumption(newDevice.getMaximumConsumption());
//        String APIurl = "http://" + monitoringImageName + ":8080" + serverContextPath + "/monitoring_devices/createDevice";
//        System.out.println(APIurl);
//        ResponseEntity<String> response = restTemplate.postForEntity(
//                APIurl,
//                requestBody, String.class
//        );
//
//        if (response.getStatusCode() == HttpStatus.CREATED) {
//
//
//        } else {
//            throw new RuntimeException("Device creation failed");
//        }

        return DeviceBuilder.toDTO(newDevice);
    }

    @Override
    public void updateDevice(DeviceDTO deviceDTO) {
        Device existingDevice = deviceRepository.findById(deviceDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid device ID: " + deviceDTO.getId()));


        if (deviceDTO.getMaximumConsumption() != null) {
            existingDevice.setMaximumConsumption(deviceDTO.getMaximumConsumption());
        }
        if (deviceDTO.getDescription() != null) {
            existingDevice.setDescription(deviceDTO.getDescription());
        }
        if (deviceDTO.getLocation() != null) {
            existingDevice.setLocation(deviceDTO.getLocation());
        }

        deviceRepository.save(existingDevice);
    }

    @Override
    public void deleteDevice(DeviceDTO deviceDTO) {
        Device existingDevice = deviceRepository.findById(deviceDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid device ID: " + deviceDTO.getId()));

//        String APIurl = "http://" + monitoringImageName + ":8080" + serverContextPath + "/monitoring_devices/"+ existingDevice.getId();
//        ResponseEntity<Void> response = restTemplate.exchange(
//                APIurl,
//                HttpMethod.DELETE,
//                null,
//                Void.class
//        );

//        if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
//             deviceRepository.delete(existingDevice);
//        } else {
//            throw new RuntimeException("Account deletion failed");
//        }
        deviceRepository.delete(existingDevice);

    }

    @Override
    public List<DeviceDTO> getAllDevices() {
        List<Device> devices = deviceRepository.findAll();
        List<DeviceDTO> deviceDTOs = new ArrayList<>();
        for (Device device : devices) {
            deviceDTOs.add(DeviceBuilder.toDTO(device));
        }
        return deviceDTOs;
    }
}
