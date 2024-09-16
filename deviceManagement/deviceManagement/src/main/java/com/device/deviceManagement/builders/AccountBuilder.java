package com.device.deviceManagement.builders;

import com.device.deviceManagement.dtos.AccountDTO;
import com.device.deviceManagement.entity.Account;


public class AccountBuilder {

    public static AccountDTO toDTO(Account account) {
        return AccountDTO.builder()
                .id(account.getId())
                .accountID(account.getAccountID())
                .devices(account.getDevices())
                .build();
    }

    public static Account toEntity(AccountDTO accountDTO) {
        return Account.builder()
                .id(accountDTO.getId())
                .accountID(accountDTO.getAccountID())
                .devices(accountDTO.getDevices())
                .build();
    }

}
