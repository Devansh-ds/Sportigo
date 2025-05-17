package com.sadds.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleInputRequest(MethodArgumentNotValidException ex) {
        var errors = new HashMap<String, String>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            var fieldName = ((FieldError) error).getField();
            var errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errors));
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetail> userExceptionHandler(UserException e, WebRequest req) {
        ErrorDetail err = new ErrorDetail(
                e.getMessage(),
                req.getDescription(false),
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDetail> badCredentialsExceptionHandler(BadCredentialsException e, WebRequest req) {
        ErrorDetail err = new ErrorDetail(
                e.getMessage(),
                req.getDescription(false),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(TokenInvalidException.class)
    public ResponseEntity<ErrorDetail> tokenInvalidExceptionHandler(TokenInvalidException e, WebRequest req) {
        ErrorDetail err = new ErrorDetail(
                e.getMessage(),
                req.getDescription(false),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorDetail> userAlreadyExistExceptionHandler(UserAlreadyExistException e, WebRequest req) {
        ErrorDetail err = new ErrorDetail(
                e.getMessage(),
                req.getDescription(false),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    @ExceptionHandler(MarketException.class)
    public ResponseEntity<ErrorDetail> marketExceptionHandler(MarketException e, WebRequest req) {
        ErrorDetail err = new ErrorDetail(
                e.getMessage(),
                req.getDescription(false),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(EventException.class)
    public ResponseEntity<ErrorDetail> eventExceptionHandler(EventException e, WebRequest req) {
        ErrorDetail err = new ErrorDetail(
                e.getMessage(),
                req.getDescription(false),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(TeamException.class)
    public ResponseEntity<ErrorDetail> teamExceptionHandler(TeamException e, WebRequest req) {
        ErrorDetail err = new ErrorDetail(
                e.getMessage(),
                req.getDescription(false),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }
}




















