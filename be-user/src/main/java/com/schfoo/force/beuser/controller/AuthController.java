package com.schfoo.force.beuser.controller;

import com.schfoo.force.beuser.service.AuthService;
import com.schfoo.force.helper.controller.BaseController;
import com.schfoo.force.model.web.req.RegisterReq;
import com.schfoo.force.model.web.req.SignInReq;
import com.schfoo.force.model.web.req.ValidateTokenReq;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app-prefix-path}/v1.0/auth")
public class AuthController extends BaseController {

    @Autowired
    private AuthService authService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody @Valid SignInReq signInReq) {
        return wrap(() -> authService.signIn(signInReq));
    }

    @PostMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestBody @Valid ValidateTokenReq validateTokenReq) {
        return wrap(() -> authService.validateToken(validateTokenReq));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterReq registerReq) {
        return wrap(() -> authService.register(registerReq));
    }

}
