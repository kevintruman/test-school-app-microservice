package com.schfoo.force.beuser.config;

import com.schfoo.force.beuser.repo.UserSessionRepo;
import com.schfoo.force.helper.util.ExceptionUtil;
import com.schfoo.force.helper.util.HttpUtil;
import com.schfoo.force.helper.util.JwtUtil;
import com.schfoo.force.model.entity.user.UserSessionEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Component
public class AuthFilterConfig extends OncePerRequestFilter {

    @Autowired
    private UserSessionRepo userSessionRepo;
    @Autowired
    private UserDetailConfig userDetailConfig;

    @Value("${app-jwt-secret}")
    private String jwtSecret;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = HttpUtil.parseToken(request);
            if (Strings.isNotBlank(jwt) && JwtUtil.isValid(jwtSecret, jwt)) {
                String subject = JwtUtil.getSubject(jwtSecret, jwt, false);

                UserSessionEntity userSession = userSessionRepo.getOneByIdAndIsActive(subject);
                if (Objects.isNull(userSession)) {
                    throw ExceptionUtil.thr("invalid session", false);
                }

                UserDetails userDetails = userDetailConfig.loadUserByUsername(userSession.getUser().getUsername());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.error("cannot authenticate: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

}
