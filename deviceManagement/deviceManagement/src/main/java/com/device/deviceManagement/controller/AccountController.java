package com.device.deviceManagement.controller;

import com.device.deviceManagement.dtos.AccountDTO;
import com.device.deviceManagement.dtos.DeviceDTO;
import com.device.deviceManagement.security.AuthProvider;
import com.device.deviceManagement.services.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dev_accounts")
public class AccountController {

    private final AccountServiceImpl accountService;
    private final AuthProvider userAuthenticationProvider;


    @GetMapping("/")
    public ResponseEntity<?> getAllAccounts(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
        String tokenSubstring = token.substring(7);
        if (userAuthenticationProvider.validateToken(tokenSubstring).isAuthenticated()) {
            return ResponseEntity.ok(accountService.getAllAccounts());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable Long id, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
        String tokenSubstring = token.substring(7);
        if (userAuthenticationProvider.validateToken(tokenSubstring).isAuthenticated()) {
            AccountDTO accountDTO = accountService.getAccountById(id);
            if (accountDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
            } else {
                return ResponseEntity.ok(accountDTO);
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    @PostMapping("/createAccount")
    public ResponseEntity<?> createAccount(@RequestBody AccountDTO accountDTO) {
        AccountDTO newAccountDTO = new AccountDTO();
        newAccountDTO.setAccountID(accountDTO.getAccountID());
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(newAccountDTO));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
        String tokenSubstring = token.substring(7);
        if (userAuthenticationProvider.validateToken(tokenSubstring).isAuthenticated()) {
            accountService.deleteAccount(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }


    @GetMapping("/{accountID}/devices")
    public ResponseEntity<?> getDevicesByAccountID(@PathVariable Long accountID, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
        String tokenSubstring = token.substring(7);
        if (userAuthenticationProvider.validateToken(tokenSubstring).isAuthenticated()) {
            List<DeviceDTO> devices = accountService.getDevicesByAccountID(accountID);
            return ResponseEntity.ok(devices);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }
}
