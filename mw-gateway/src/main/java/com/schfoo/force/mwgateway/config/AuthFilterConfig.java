package com.schfoo.force.mwgateway.config;

import com.schfoo.force.helper.util.HttpUtil;
import com.schfoo.force.helper.util.JwtUtil;
import com.schfoo.force.model.constant.WebConstant;
import com.schfoo.force.mwgateway.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.function.Predicate;

@Slf4j
@Component
public class AuthFilterConfig extends AbstractGatewayFilterFactory<AuthFilterConfig.Config> {

    public AuthFilterConfig() {
        super(Config.class);
    }

    @Value("${app-jwt-secret}")
    private String jwtSecret;

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (isSecure.test(exchange.getRequest())) {
                HttpHeaders headers = exchange.getRequest().getHeaders();
                if (!headers.isEmpty() && !headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw UnauthorizedException.build("invalid session");
                }

                String authHeader = headers.get(HttpHeaders.AUTHORIZATION).get(0);
                if (Strings.isNotBlank(authHeader)) {
                    String token = HttpUtil.getTokenFromBearer(authHeader);

                    boolean isValid = JwtUtil.isValid(jwtSecret, token);
                    if (!isValid) {
                        throw UnauthorizedException.build("invalid session");
                    }
                } else {
                    throw UnauthorizedException.build("invalid session");
                }
            }
            return chain.filter(exchange);
        });
    }

    private final Predicate<ServerHttpRequest> isSecure = request -> Arrays.stream(WebConstant.Auth.whitelist)
            .noneMatch(uri -> request.getURI().getPath().contains(uri));

    public static class Config {}

}
