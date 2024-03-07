package com.schfoo.force.feapp.service;

import com.schfoo.force.model.web.req.SignInReq;
import com.schfoo.force.model.web.res.SignInRes;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WrappedSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {

    @Autowired
    private ApiService apiService;

    @Value("${app-api-endpoint}")
    private String apiEndpoint;
    private static final String signInUrl = "/user/v1.0/auth/sign-in";

    public void login(String username, String password) {
        SignInReq payload = new SignInReq();
        payload.setUsername(username);
        payload.setPassword(password);

        SignInRes signInRes = apiService.fetchNotSecure(
                apiEndpoint + signInUrl, HttpMethod.POST, payload, SignInRes.class);

        WrappedSession session = VaadinSession.getCurrent().getSession();
        session.setAttribute("sessionId", signInRes);

        UI current = UI.getCurrent();
        current.navigate("");
    }

}
