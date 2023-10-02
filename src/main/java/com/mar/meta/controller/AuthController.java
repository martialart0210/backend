package com.mar.meta.controller;

import com.mar.meta.dto.LogInDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
public class AuthController {
    @Operation(summary = "Login.")
    @PostMapping("/login")
    @ResponseStatus(OK)
    public void fakeLogin(@RequestBody LogInDto dto) {}

    @Operation(summary = "Logout.")
    @PostMapping("/logout")
    @ResponseStatus(OK)
    public void fakeLogout() {}
}
