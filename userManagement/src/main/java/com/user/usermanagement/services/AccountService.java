package com.user.usermanagement.services;

import com.user.usermanagement.dtos.AccountDTO;

import java.util.List;


public interface AccountService {

    AccountDTO getAccountById(Long id);
    AccountDTO saveAccount(AccountDTO account);
    void updateAccount(AccountDTO account);
    void deleteAccount(Long id,String token);
    List<AccountDTO> getAllAccounts();
    AccountDTO login(String name, String password);
    public AccountDTO getAccountByName(String name);



}
