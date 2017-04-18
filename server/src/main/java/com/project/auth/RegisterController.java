package com.project.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Adas on 2017-04-18.
 */
@RestController
@RequestMapping("/auth")
public class RegisterController {
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Account> register(@RequestBody Account account){
        if (account != null) {
            if (accountRepository.findByEmail(account.getEmail()) != null) {
                accountRepository.save(account);
            }
        }

        return new ResponseEntity<Account>(account, HttpStatus.OK);
    }
}
