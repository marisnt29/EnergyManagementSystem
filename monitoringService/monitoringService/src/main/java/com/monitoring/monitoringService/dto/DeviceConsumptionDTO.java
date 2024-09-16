package com.monitoring.monitoringService.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DeviceConsumptionDTO {

    private Long id;

    private Long deviceID;

    private Float energyConsumption;

    private Long timestamp;

}