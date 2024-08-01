package com.nurd.todolist.controllers;

import com.nurd.todolist.servs.AuthServ;
import com.nurd.todolist.utils.dtos.AuthDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final AuthServ authServ;

    @PostMapping("/login")
    public ResponseEntity<AuthDto.ResponseLoginDto> login(@RequestBody @Valid AuthDto.RequestLoginDto obj) {
       return ResponseEntity.ok(authServ.login(obj));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthDto.ResponseRegisterDto> register(@RequestBody @Valid AuthDto.RequestRegisterDto obj) {
        return new ResponseEntity<>(authServ.register(obj), HttpStatus.CREATED);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthDto.ResponseRefreshTokenDto> refreshToken(@RequestBody @Valid AuthDto.RequestRefreshTokenDto obj) {
        return ResponseEntity.ok(authServ.refreshToken(obj));
    }
}
