package com.schfoo.force.helper.controller;

import com.schfoo.force.helper.exception.ResException;
import com.schfoo.force.helper.exception.TrxException;
import com.schfoo.force.helper.model.WebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

public abstract class BaseErrorController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<WebResponse<?>> validationError(MethodArgumentNotValidException ex) {
        List<String> errorList = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage).toList();
        WebResponse<?> webResponse = WebResponse.builder()
                .data(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .errors(errorList)
                .build();
        return new ResponseEntity<>(webResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ResException.class, TrxException.class, RuntimeException.class})
    public <T extends Exception> ResponseEntity<WebResponse<?>> handleError(T exception) {

        List<String> errors;
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        if (exception instanceof ResException) {
            errors = ((ResException) exception).getErrorsMessage();
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        else if (exception instanceof TrxException) {
            errors = ((TrxException) exception).getErrorsMessage();
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        else {
            errors = List.of(exception.getMessage());
        }
        WebResponse<?> webResponse = WebResponse.builder()
                .errors(errors)
                .build();

        return new ResponseEntity<>(webResponse, httpStatus);
    }

}
