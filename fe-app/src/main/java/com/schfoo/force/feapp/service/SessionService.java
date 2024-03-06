package com.schfoo.force.feapp.service;

import com.schfoo.force.model.web.res.SignInRes;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WrappedSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class SessionService {

    public SignInRes getSession() {
        WrappedSession session = VaadinSession.getCurrent().getSession();
        Object sessionId = session.getAttribute("sessionId");
        if (Objects.nonNull(sessionId)) {
            return (SignInRes) sessionId;
        } else {
            session.invalidate();
        }
        return null;
    }

}
