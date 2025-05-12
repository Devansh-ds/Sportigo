package com.sadds.exception;

import lombok.Data;

import java.util.HashMap;

@Data
public class ErrorResponse {
    HashMap<String, String> errors;

    public ErrorResponse() {}

    public ErrorResponse(HashMap<String, String> errors) {
        this.errors = errors;
    }
}
