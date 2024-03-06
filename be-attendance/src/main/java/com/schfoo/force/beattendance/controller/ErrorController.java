package com.schfoo.force.beattendance.controller;

import com.schfoo.force.helper.controller.BaseErrorController;
import com.schfoo.force.helper.model.WebResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ErrorController extends BaseErrorController {

    @Override
    public <T extends Exception> ResponseEntity<WebResponse<?>> handleError(T exception) {
        exception.printStackTrace();
        return super.handleError(exception);
    }
}
