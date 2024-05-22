package com.syncrosa.livestreamingservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.syncrosa.livestreamingservice.payload.response.ErrorResponse;

public class ErrorController {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse<String>> constraintViolationException(ConstraintViolationException exception,
                                                                              HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.<String>builder()
                        .apiPath(request.getRequestURI())
                        .errorCode(HttpStatus.BAD_REQUEST.value())
                        .errorMessage(exception.getMessage())
                        .errorTime(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<List<Map<String, String>>>> methodArgumentNotValidException(MethodArgumentNotValidException exception,
                                                                                                    HttpServletRequest request) {

        List<Map<String, String>> errors = exception.getBindingResult().getFieldErrors()
                .stream().map(validation -> {
                    Map<String, String> error = new HashMap<>();
                    error.put(validation.getField(), validation.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.<List<Map<String, String>>>builder()
                        .errorCode(HttpStatus.BAD_REQUEST.value())
                        .apiPath(request.getRequestURI())
                        .errorTime(LocalDateTime.now())
                        .errorMessage(errors)
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<String>> globalException(Exception exception,
                                                                 HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.<String>builder()
                        .apiPath(request.getRequestURI())
                        .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .errorMessage(exception.getMessage())
                        .errorTime(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(ResponseStatusException.class)
    private ResponseEntity<ErrorResponse<String>> responseStatusException(ResponseStatusException exception,
                                                                          HttpServletRequest request) {
        return ResponseEntity.status(exception.getStatusCode())
                .body(ErrorResponse.<String>builder()
                        .apiPath(request.getRequestURI())
                        .errorCode(exception.getStatusCode().value())
                        .errorMessage(exception.getMessage())
                        .errorTime(LocalDateTime.now())
                        .build());
    }

}
