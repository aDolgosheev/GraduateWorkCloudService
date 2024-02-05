package ru.dolgosheev.cloudservice.controller;

import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dolgosheev.cloudservice.service.AuthorizationService;

@RestController
@RequestMapping("/logout")
@Validated
public class LogoutController {
    private final AuthorizationService authorizationService;

    public LogoutController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping
    public void logout(@RequestHeader("auth-token") @NotBlank String authToken) {
        authorizationService.logout(authToken);
    }
}