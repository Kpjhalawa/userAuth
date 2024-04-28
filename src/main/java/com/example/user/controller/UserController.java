package com.example.user.controller;

import com.example.user.dto.LoginRequestDto;
import com.example.user.dto.SignupRequestDto;
import com.example.user.model.Token;
import com.example.user.model.User;
import com.example.user.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

public class UserController {
    @Autowired
    private UserServices userServices;

    @PostMapping("/signup")
    public User signup(@RequestBody SignupRequestDto signupRequestDto){
    return userServices.signUp(signupRequestDto.getName(),signupRequestDto.getEmail(),
            signupRequestDto.getPassword());
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:63343")
    public Token login(@RequestBody LoginRequestDto loginRequestDto){
    return userServices.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
    }

    @PostMapping("/logout/{token}")
    public ResponseEntity<Void> logout(@PathVariable("token") String token){
    userServices.logout(token);
    return new ResponseEntity<>(HttpStatus.OK);
    }
}
