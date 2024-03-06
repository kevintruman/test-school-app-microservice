package com.schfoo.force.mwgateway.controller;

import com.schfoo.force.helper.controller.BaseErrorController;
import com.schfoo.force.helper.model.WebResponse;
import com.schfoo.force.mwgateway.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ErrorController extends BaseErrorController {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<WebResponse<?>> unauthorizedError(UnauthorizedException ex) {
        WebResponse<?> webResponse = WebResponse.builder()
                .data(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .errors(List.of(ex.getMsg()))
                .build();
        return new ResponseEntity<>(webResponse, HttpStatus.UNAUTHORIZED);
    }

}
