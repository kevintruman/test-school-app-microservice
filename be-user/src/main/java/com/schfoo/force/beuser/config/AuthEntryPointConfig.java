package com.schfoo.force.beuser.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schfoo.force.helper.model.WebResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class AuthEntryPointConfig implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        log.error("unauthorized error: {} ", authException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        WebResponse<?> res = WebResponse.builder()
                .errors(List.of(authException.getMessage()))
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), res);
    }

}
