package com.schfoo.force.helper.util;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class HttpUtil {

    public static String parseToken(HttpServletRequest request) {
        String headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = getTokenFromBearer(headerAuth);

        if (Strings.isBlank(token)) {
            token = getTokenFromQuery(request.getQueryString());
        }
        return token;
    }

    public static String getTokenFromBearer(String headerAuth) {
        if (Strings.isNotBlank(headerAuth) && StringUtils.hasText(headerAuth)
                && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }

    public static String getTokenFromQuery(String queryString) {
        if (Strings.isBlank(queryString)) {
            return null;
        }

        String[] queries = queryString.split("&");
        String tokenQuery = Arrays.stream(queries).filter(e -> e.contains("access_token"))
                .collect(Collectors.joining());
        if (Strings.isNotBlank(tokenQuery)) {
            String[] t = tokenQuery.split("=");
            return t[1];
        }

        return null;
    }

}
