package com.monitoring.monitoringService.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DeviceDTO {

    private Long id;

    private Long deviceID;

    private Float maxConsumption;

}