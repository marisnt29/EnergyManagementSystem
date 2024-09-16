package com.device.deviceManagement.services;

import com.device.deviceManagement.dtos.AccountDTO;

import java.util.List;

public interface AccountService {

    AccountDTO createAccount(AccountDTO accountDTO);
    List<AccountDTO> getAllAccounts();
    AccountDTO getAccountById(Long id);
    void deleteAccount(Long id);

}
