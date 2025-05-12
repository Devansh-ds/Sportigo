package com.sadds.security_controller;

import com.sadds.exception.TokenInvalidException;
import com.sadds.exception.UserAlreadyExistException;
import com.sadds.exception.UserException;
import com.sadds.model.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request) throws UserAlreadyExistException {
        request.setRole(Role.USER);
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request) throws UserException {
        return ResponseEntity.ok(authenticationService.authenticate(request));

    }

    @PostMapping("/refreshToken")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestHeader("Authorization") String refreshToken) throws IOException, TokenInvalidException {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshToken));
    }


}

