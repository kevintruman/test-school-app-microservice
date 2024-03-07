package com.schfoo.force.feapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schfoo.force.helper.model.WebResponse;
import com.schfoo.force.helper.util.ExceptionUtil;
import com.schfoo.force.model.web.res.SignInRes;
import com.vaadin.flow.server.VaadinSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class ApiService {

    @Autowired
    private SessionService sessionService;
    @Autowired
    private RestTemplate restTemplate;

    public <T> T fetch(String url, HttpMethod method, Object reqBody, Class<T> clazz, boolean isSecure) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (isSecure) {
            SignInRes signInRes = sessionService.getSession();
            if (Objects.nonNull(signInRes)) {
                headers.setBearerAuth(signInRes.getToken());
            }
        }

        HttpEntity<?> requestEntity = new HttpEntity<>(reqBody, headers);

        try {
            ResponseEntity<Map<String, Object>> res = restTemplate.exchange(
                    url, method, requestEntity, new ParameterizedTypeReference<>() {});

            Map<String, Object> body = res.getBody();
            if (Objects.isNull(body)) {
                throw ExceptionUtil.thr("error null", false);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.convertValue(body.get("data"), clazz);

        } catch (HttpStatusCodeException e) {
            String errorMsg = e.getMessage();
            log.error("error fetch: {}", errorMsg);

            WebResponse<?> responseBodyAs = e.getResponseBodyAs(WebResponse.class);
            if (Objects.nonNull(responseBodyAs) && !CollectionUtils.isEmpty(responseBodyAs.getErrors())) {
                if (e.getStatusCode().value() == 401) {
                    VaadinSession.getCurrent().getSession().invalidate();
                }
                throw ExceptionUtil.thr(responseBodyAs.getErrors(), false);
            }

            throw ExceptionUtil.thr(errorMsg, false);
        }
    }

    public <T> T fetchSecure(String url, HttpMethod method, Object reqBody, Class<T> clazz) {
        return fetch(url, method, reqBody, clazz, true);
    }

    public <T> T fetchNotSecure(String url, HttpMethod method, Object reqBody, Class<T> clazz) {
        return fetch(url, method, reqBody, clazz, false);
    }

    public <T> List<T> toList(List<?> data, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(
                data, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
    }

}
