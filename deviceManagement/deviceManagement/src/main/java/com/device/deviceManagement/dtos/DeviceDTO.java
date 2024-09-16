package com.device.deviceManagement.dtos;

import lombok.*;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DeviceDTO {
    private Long id;

    private Float maximumConsumption;

    private String description;

    private String location;

    private Long accountID;

}
