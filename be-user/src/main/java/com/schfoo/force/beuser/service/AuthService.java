package com.schfoo.force.beuser.service;

import com.schfoo.force.beuser.repo.UserMainRepo;
import com.schfoo.force.beuser.repo.UserSessionRepo;
import com.schfoo.force.helper.exception.ResException;
import com.schfoo.force.helper.exception.TrxException;
import com.schfoo.force.helper.util.DateUtil;
import com.schfoo.force.helper.util.JwtUtil;
import com.schfoo.force.model.entity.user.UserMainEntity;
import com.schfoo.force.model.entity.user.UserSessionEntity;
import com.schfoo.force.model.web.req.RegisterReq;
import com.schfoo.force.model.web.req.SignInReq;
import com.schfoo.force.model.web.req.ValidateTokenReq;
import com.schfoo.force.model.web.res.SignInRes;
import com.schfoo.force.model.web.res.UserRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

@Slf4j
@Service
@Transactional(rollbackFor = TrxException.class)
public class AuthService {

    @Autowired
    private UserMainRepo userMainRepo;
    @Autowired
    public PasswordEncoder passwordEncoder;
    @Autowired
    private UserSessionRepo userSessionRepo;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${app-jwt-secret}")
    private String jwtSecret;
    @Value("${app-jwt-expired}")
    private Long jwtExpired;

    public SignInRes signIn(SignInReq signInReq) {
        // validation
        UserMainEntity userMain = userMainRepo.getOneByUsernameOrEmailAndIsActive(signInReq.getUsername());
        if (Objects.isNull(userMain)) {
            log.error("user not found {}", signInReq.getUsername());
            throw ResException.build("User not found");
        }

        boolean isMatch = passwordEncoder.matches(signInReq.getPassword(), userMain.getPassword());
        if (!isMatch) {
            log.error("wrong password {}", userMain.getUsername());
            throw ResException.build("User not found");
        }

        // set security context
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInReq.getUsername(), signInReq.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // create user session
        Date expiredDate = DateUtil.addSecond(new Date(), Math.toIntExact(jwtExpired));

        UserSessionEntity userSession = UserSessionEntity.builder()
                .user(userMain)
                .expiredDate(expiredDate)
                .build();
        userSession = userSessionRepo.saveAndFlush(userSession);

        // build token
        String token = JwtUtil.generate(jwtSecret, expiredDate, String.valueOf(userSession.getId()), null);

        return SignInRes.build(userSession, token);
    }

    public SignInRes validateToken(ValidateTokenReq validateTokenReq) {
        boolean valid = JwtUtil.isValid(jwtSecret, validateTokenReq.getToken());
        if (!valid) {
            throw ResException.build("token not valid");
        }

        String subject = JwtUtil.getSubject(jwtSecret, validateTokenReq.getToken(), false);
        UserSessionEntity userSession = userSessionRepo.getOneByIdAndIsActive(subject);

        return SignInRes.build(userSession, validateTokenReq.getToken());
    }

    public UserRes register(RegisterReq registerReq) {
        String passwordHash = passwordEncoder.encode(registerReq.getPassword());
        UserMainEntity newUserMainEntity = registerReq.toNewUserMainEntity(passwordHash);
        newUserMainEntity = userMainRepo.saveAndFlush(newUserMainEntity);
        return UserRes.build(newUserMainEntity);
    }

}
