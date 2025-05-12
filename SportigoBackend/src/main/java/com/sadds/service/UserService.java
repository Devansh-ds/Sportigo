package com.sadds.service;

import com.sadds.dto.UserDto;
import com.sadds.exception.BadCredentialsException;
import com.sadds.exception.UserException;
import com.sadds.model.User;
import com.sadds.request.UpdateProfileRequest;

public interface UserService {

    User findByJwtToken(String token) throws UserException, BadCredentialsException;
    UserDto getUserProfile(String token) throws UserException, BadCredentialsException;
    UserDto updateUserProfile(String token, UpdateProfileRequest request) throws UserException, BadCredentialsException;

}
