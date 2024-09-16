package com.device.deviceManagement.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RequestDTO {
    private Long deviceID;
    private Float maxConsumption;
}
