package com.user.usermanagement.services;

import com.user.usermanagement.builders.AccountBuilder;
import com.user.usermanagement.dtos.AccountDTO;
import com.user.usermanagement.dtos.RequestDTO;
import com.user.usermanagement.entity.Account;
import com.user.usermanagement.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

@Transactional
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final RestTemplate restTemplate;

    @Value("${device.image.name}")
    private String deviceImageName;

    @Value("${device.server.context.path}")
    private String serverContextPath;


    public AccountDTO getAccountById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid account Id:" + id));

        return AccountBuilder.toAccountDto(account);

    }

    public AccountDTO getAccountByName(String name) {
        Account account = accountRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException("Invalid account name:" + name));
        return AccountBuilder.toAccountDto(account);
    }

    @Override
    public AccountDTO saveAccount(AccountDTO account) {
        Account newAccount = AccountBuilder.toEntity(account);
        accountRepository.save(newAccount);

        RequestDTO requestBody = new RequestDTO(newAccount.getId());
        String APIurl = "http://" + deviceImageName + ":8080" + serverContextPath + "/dev_accounts/createAccount";
        ResponseEntity<String> response = restTemplate.postForEntity(
                APIurl,
                requestBody, String.class
        );

        if (response.getStatusCode() == HttpStatus.CREATED) {

            return AccountBuilder.toAccountDto(newAccount);
        } else {
            throw new RuntimeException("Account creation failed");
        }

    }


    @Override
    public void updateAccount(AccountDTO account) {
        Account existingAccount = accountRepository.findById(account.getId()).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + account.getId()));

        if (account.getName() != null) {
            existingAccount.setName(account.getName());
        }

        if (account.getPassword() != null) {
            existingAccount.setPassword(account.getPassword());
        }

        accountRepository.save(existingAccount);
    }

    @Override
    public void deleteAccount(Long id,String token) {
        Account existingAccount = accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        String APIurl = "http://" + deviceImageName + ":8080" + serverContextPath + "/dev_accounts/"+ existingAccount.getId();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",   token);
        HttpEntity<String> entity = new HttpEntity<>(headers);


        ResponseEntity<Void> response = restTemplate.exchange(
                APIurl,
                HttpMethod.DELETE,
                entity,
                Void.class
        );

        if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
            accountRepository.delete(existingAccount);
        } else {
            throw new RuntimeException("Account deletion failed");
        }

    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        List<AccountDTO> accountDTOS = new ArrayList<>();
        for (Account account : accounts) {
            accountDTOS.add(AccountBuilder.toAccountDto(account));
        }
        return accountDTOS;
    }

    @Override
    public AccountDTO login(String name, String password) {
        Account account = accountRepository.findByNameAndPassword(name,password).orElseThrow(() -> new IllegalArgumentException("Invalid user credentials:"));
        AccountDTO accountDTO = AccountBuilder.toAccountDto(account);
        return accountDTO;
    }


}
