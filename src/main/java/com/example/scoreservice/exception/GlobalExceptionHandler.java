package com.example.scoreservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ScoreNotFoundException.class)
    public Mono<ResponseStatusException> handleScoreNotFoundException(ScoreNotFoundException ex) {
        return Mono.just(new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseStatusException> handleException(Exception ex) {
        return Mono.just(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }
}
