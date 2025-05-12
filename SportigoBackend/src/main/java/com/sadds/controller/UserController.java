package com.sadds.controller;

import com.sadds.dto.UserDto;
import com.sadds.exception.BadCredentialsException;
import com.sadds.exception.UserException;
import com.sadds.request.UpdateProfileRequest;
import com.sadds.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDto> getUserProfile(@RequestHeader("Authorization") String token)
            throws UserException, BadCredentialsException {
        return ResponseEntity.ok(userService.getUserProfile(token));
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUserProfile(@RequestHeader("Authorization") String token,
                                                     @RequestBody UpdateProfileRequest request)
            throws UserException, BadCredentialsException {
        return ResponseEntity.ok(userService.updateUserProfile(token, request));
    }
}
