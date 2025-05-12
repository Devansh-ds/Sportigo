package com.sadds.service;

import com.sadds.config.JwtService;
import com.sadds.dto.UserDto;
import com.sadds.exception.BadCredentialsException;
import com.sadds.exception.UserException;
import com.sadds.mapper.UserMapper;
import com.sadds.model.User;
import com.sadds.repo.UserRepository;
import com.sadds.request.UpdateProfileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public User findByJwtToken(String token) throws UserException, BadCredentialsException {
        token = token.substring(7);
        String email = jwtService.extractUsername(token);
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new BadCredentialsException("User not found with email: " + email));
        return user;
    }

    @Override
    public UserDto getUserProfile(String token) throws UserException, BadCredentialsException {
        User user = findByJwtToken(token);
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto updateUserProfile(String token, UpdateProfileRequest request) throws UserException, BadCredentialsException {
        User user = findByJwtToken(token);
        if (request.fullname() != null  && !request.fullname().isEmpty()) {
            user.setFullname(request.fullname());
        }
        if (request.profilePicture() != null  && !request.profilePicture().isEmpty()) {
            user.setProfilePicture(request.profilePicture());
        }
        User updatedUser = userRepository.save(user);
        return userMapper.toUserDto(updatedUser);
    }

    private User getUserById(Integer userId) throws UserException {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new UserException("User not found with id: " + userId));
    }

}
