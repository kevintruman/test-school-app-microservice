package com.schfoo.force.feapp.page;

import com.schfoo.force.feapp.component.BaseLayout;
import com.schfoo.force.feapp.component.MainLayout;
import com.schfoo.force.feapp.service.SessionService;
import com.schfoo.force.model.web.res.SignInRes;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@UIScope
@SpringComponent
@PageTitle("School Foo - Home")
@Route(value = "", layout = MainLayout.class)
@RouteAlias(value = "/home", layout = MainLayout.class)
public class HomePage extends BaseLayout {

    @Autowired
    private SessionService sessionService;

    @Override
    protected void buildView() {
        SignInRes session = sessionService.getSession();
        NativeLabel label = new NativeLabel("Selamat Datang " + session.getUser().getFullName());
        add(label);
    }

}
