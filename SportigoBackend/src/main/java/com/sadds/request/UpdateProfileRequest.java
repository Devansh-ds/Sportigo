package com.sadds.request;

import lombok.Getter;

public record UpdateProfileRequest(
        String fullname,
        String profilePicture
) {

}
