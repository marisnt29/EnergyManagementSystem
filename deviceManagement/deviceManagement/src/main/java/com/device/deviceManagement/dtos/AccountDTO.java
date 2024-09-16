    package com.device.deviceManagement.dtos;

    import com.device.deviceManagement.entity.Device;
    import lombok.*;

    import java.util.List;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public class AccountDTO {
        private Long id;

        private Long accountID;

        private List<Device> devices;

    }
