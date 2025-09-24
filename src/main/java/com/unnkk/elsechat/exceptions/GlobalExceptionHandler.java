package com.unnkk.elsechat.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("timestamp", LocalDateTime.now(),
                        "status", HttpStatus.BAD_REQUEST.value(), //400
                        "error", "Bad Request",
                        "message", e.getMessage()));
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<?> handleIncorrectPasswordException(IncorrectPasswordException e){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("timestamp", LocalDateTime.now(),
                        "status", HttpStatus.UNAUTHORIZED.value(), //401
                        "error", "Unauthorized",
                        "message", e.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e){
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(Map.of("timestamp", LocalDateTime.now(),
                        "status", HttpStatus.FORBIDDEN.value(), //403
                        "error", "Access denied",
                        "message", e.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException e){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", HttpStatus.NOT_FOUND.value(), //404
                        "error", "Not found",
                        "message", e.getMessage()
                ));
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<?> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException e){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("timestamp", LocalDateTime.now(),
                        "status", HttpStatus.CONFLICT.value(), //409
                        "error", "Conflict",
                        "message", e.getMessage()));
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<?> handleUnprocessableEntityException(UnprocessableEntityException e){
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(Map.of("timestamp", LocalDateTime.now(),
                        "status", HttpStatus.UNPROCESSABLE_ENTITY.value(), //422
                        "error", "Unprocessable Entity",
                        "message", e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", HttpStatus.INTERNAL_SERVER_ERROR.value(), //500
                        "error", "Internal Server Error",
                        "message", e.getMessage()
                ));
    }
}
