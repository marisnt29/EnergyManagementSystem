package com.user.usermanagement.controller;


import com.user.usermanagement.config.AuthProvider;
import com.user.usermanagement.dtos.AccountDTO;
import com.user.usermanagement.services.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountServiceImpl accountService;
    private final AuthProvider userAuthenticationProvider;


    @GetMapping("/")
    public ResponseEntity<?> getAllAccounts(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {

        String tokenSubstring = token.substring(7);
        if (userAuthenticationProvider.validateToken(tokenSubstring).isAuthenticated()) {
            return ResponseEntity.ok(accountService.getAllAccounts());
        } else {
            System.out.println("Invalid token");
            return ResponseEntity.internalServerError().body("Invalid token");
        }


    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable Long id, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
        String tokenSubstring = token.substring(7);
        if (userAuthenticationProvider.validateToken(tokenSubstring).isAuthenticated()) {
            AccountDTO accountDTO = accountService.getAccountById(id);
            if (accountDTO == null) {

                return ResponseEntity.internalServerError().body("Invalid User");
            } else {
                return ResponseEntity.ok(accountDTO);
            }
        } else {

            return ResponseEntity.internalServerError().body("Invalid token");
        }

    }


    @PostMapping("/register")
    public ResponseEntity<?> saveAccount(@RequestBody AccountDTO Account) {

        if (Account.getName() == null) {
            return ResponseEntity.internalServerError().body("Invalid input for creating new user");
        } else {
            return ResponseEntity.ok(accountService.saveAccount(Account));

        }
    }


    @PutMapping("/{id}")
    public void updateAccount(@RequestBody AccountDTO Account, @PathVariable Long id, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
        Account.setId(id);
        String tokenSubstring = token.substring(7);
        if (userAuthenticationProvider.validateToken(tokenSubstring).isAuthenticated()) {
            accountService.updateAccount(Account);

        } else {
            System.out.println("Invalid token");
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
        String tokenSubstring = token.substring(7);
        if (userAuthenticationProvider.validateToken(tokenSubstring).isAuthenticated()) {
            accountService.deleteAccount(id,token);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            System.out.println("Invalid token");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AccountDTO Account) {
        System.out.println("LOGGING IN");
        AccountDTO loggedUser = accountService.login(Account.getName(), Account.getPassword());
        loggedUser.setToken(userAuthenticationProvider.createToken(Account.getName()));
        return ResponseEntity.status(HttpStatus.OK).body(loggedUser);
    }

}
