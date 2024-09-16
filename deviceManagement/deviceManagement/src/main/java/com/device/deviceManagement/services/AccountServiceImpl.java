package com.device.deviceManagement.services;

import com.device.deviceManagement.builders.AccountBuilder;
import com.device.deviceManagement.builders.DeviceBuilder;
import com.device.deviceManagement.dtos.AccountDTO;
import com.device.deviceManagement.dtos.DeviceDTO;
import com.device.deviceManagement.entity.Account;
import com.device.deviceManagement.entity.Device;
import com.device.deviceManagement.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) {
        Account newAccount = AccountBuilder.toEntity(accountDTO);
        accountRepository.save(newAccount);
        return AccountBuilder.toDTO(newAccount);
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        List<AccountDTO> accountDTOS = new ArrayList<>();
        for (Account account : accounts) {
            accountDTOS.add(AccountBuilder.toDTO(account));
        }
        return accountDTOS;
    }

    @Override
    public AccountDTO getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid account device ID: " + id));

        return AccountBuilder.toDTO(account);
    }

    @Override
    public void deleteAccount(Long id) {

        Account existingAccount = accountRepository.findAccountByAccountID(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid account_id: " + id));

        accountRepository.delete(existingAccount);
    }


    public List<DeviceDTO> getDevicesByAccountID(Long accountID) {
        Account account = accountRepository.findAccountByAccountID(accountID)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with account_id: " + accountID));

        List<Device> devices = account.getDevices();

        List<DeviceDTO> deviceDTOs = new ArrayList<>();
        for (Device device : devices) {
            deviceDTOs.add(DeviceBuilder.toDTO(device));
        }
        return deviceDTOs;
    }

}

