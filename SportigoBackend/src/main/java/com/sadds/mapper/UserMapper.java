package com.sadds.mapper;

import com.sadds.dto.UserDto;
import com.sadds.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserDto toUserDto(User user) {
        return UserDto
                .builder()
                .id(user.getId())
                .fullname(user.getFullname())
                .profilePicture(user.getProfilePicture())
                .registeredAt(user.getRegisteredAt())
                .walletBalance(user.getWalletBalance())
                .build();
    }
}
