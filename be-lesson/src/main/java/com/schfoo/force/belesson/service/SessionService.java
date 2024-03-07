package com.schfoo.force.belesson.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schfoo.force.belesson.repo.UserSessionRepo;
import com.schfoo.force.helper.exception.TrxException;
import com.schfoo.force.helper.util.ExceptionUtil;
import com.schfoo.force.helper.util.HttpUtil;
import com.schfoo.force.helper.util.JwtUtil;
import com.schfoo.force.model.entity.user.UserMainEntity;
import com.schfoo.force.model.entity.user.UserSessionEntity;
import com.schfoo.force.model.web.req.ValidateTokenReq;
import com.schfoo.force.model.web.res.SignInRes;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@Transactional(rollbackFor = TrxException.class)
public class SessionService {

    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserSessionRepo userSessionRepo;

    @Value("${app-jwt-secret}")
    private String jwtSecret;
    private static final String authUrl = "http://localhost:8092/user/v1.0/auth/validate-token";

    public Map<String, SignInRes> sessions = new ConcurrentHashMap<>();

    public UserMainEntity getUser(boolean rollbackIfError) {
        String token = HttpUtil.parseToken(httpServletRequest);
        if (Strings.isBlank(token)) {
            throw ExceptionUtil.thr("invalid sesion", rollbackIfError);
        }

        boolean isValid = JwtUtil.isValid(jwtSecret, token);
        if (!isValid) {
            throw ExceptionUtil.thr("invalid sesion", rollbackIfError);
        }

        String subject = JwtUtil.getSubject(jwtSecret, token, rollbackIfError);
        SignInRes signInRes = sessions.get(subject);

        // get from auth
        if (Objects.isNull(signInRes)) {
            ValidateTokenReq payload = new ValidateTokenReq(token);

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<ValidateTokenReq> requestEntity = new HttpEntity<>(payload, headers);

            try {
                ResponseEntity<Map<String, Object>> res = restTemplate.exchange(
                        authUrl, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<>() {});

                Map<String, Object> body = res.getBody();
                if (Objects.isNull(body)) {
                    throw ExceptionUtil.thr("invalid sesion", rollbackIfError);
                }

                ObjectMapper objectMapper = new ObjectMapper();
                signInRes = objectMapper.convertValue(body.get("data"), SignInRes.class);

            } catch (RestClientException e) {
                log.error("error fetch auth: {}", e.getMessage());
                throw ExceptionUtil.thr("invalid sesion", rollbackIfError);
            }

            subject = JwtUtil.getSubject(jwtSecret, signInRes.getToken(), rollbackIfError);
            sessions.put(subject, signInRes);
        }

        return new UserMainEntity(signInRes.getUser().getId());
    }

    public UserMainEntity getUserLocal(boolean rollbackIfError) {
        String token = HttpUtil.parseToken(httpServletRequest);
        if (Strings.isBlank(token)) {
            throw ExceptionUtil.thr("invalid sesion", rollbackIfError);
        }

        boolean isValid = JwtUtil.isValid(jwtSecret, token);
        if (!isValid) {
            throw ExceptionUtil.thr("invalid sesion", rollbackIfError);
        }

        String subject = JwtUtil.getSubject(jwtSecret, token, rollbackIfError);
        UserSessionEntity userSession = userSessionRepo.getOneByIdAndIsActive(Long.valueOf(subject));

        if (Objects.isNull(userSession) || Objects.isNull(userSession.getUser())) {
            throw ExceptionUtil.thr("invalid sesion", rollbackIfError);
        }

        return userSession.getUser();
    }

}
