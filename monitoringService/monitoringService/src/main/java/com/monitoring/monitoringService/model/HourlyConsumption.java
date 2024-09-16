package com.monitoring.monitoringService.model;


import lombok.*;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class HourlyConsumption {
    private Long timestamp;
    private Float lastConsumption;


}
