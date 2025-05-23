package com.sadds.security_controller;

import com.sadds.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {

    private String fullname;
    private String email;
    private String password;
    private Role role;


}
