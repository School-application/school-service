package com.schoolservice.handler;

import com.schoolservice.dto.response.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedList;
import java.util.List;

@ControllerAdvice
public class ErrorResponseExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(new ErrorResponseDto(400, messageForMethodArgumentNotValidException(ex)), HttpStatus.BAD_REQUEST);
    }

    private String messageForMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        StringBuilder sb = new StringBuilder("Validation failed: ");
        List<String> validationErrorMessages = new LinkedList<>();

        ex.getBindingResult().getGlobalErrors()
                .forEach(error -> validationErrorMessages.add(error.getDefaultMessage()));

        ex.getBindingResult().getFieldErrors()
                .forEach(error -> validationErrorMessages.add(String.format("%s", error.getDefaultMessage())));

        sb.append(String.join(", ", validationErrorMessages));
        sb.append(".");
        return sb.toString();
    }
}
