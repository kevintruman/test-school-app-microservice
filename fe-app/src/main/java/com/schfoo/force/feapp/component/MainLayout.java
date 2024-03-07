package com.schfoo.force.feapp.component;

import com.schfoo.force.feapp.page.AttendancePage;
import com.schfoo.force.feapp.page.HomePage;
import com.schfoo.force.feapp.page.SchedulePage;
import com.schfoo.force.feapp.page.StudentPage;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WrappedSession;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import jakarta.annotation.PostConstruct;

@UIScope
@SpringComponent
public class MainLayout extends HorizontalLayout implements RouterLayout, BeforeEnterObserver {

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        WrappedSession session = VaadinSession.getCurrent().getSession();
        Object sessionId = session.getAttribute("sessionId");

        if (sessionId == null) {
            event.forwardTo("/sign-in");
        }
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        this.content.getElement().appendChild(content.getElement());
    }

    private final VerticalLayout content = new VerticalLayout();

    @PostConstruct
    void init() {
        setHeight(100, Unit.VH);
        setWidth(100, Unit.VW);

        content.setHeight(100, Unit.VH);
        content.setWidthFull();

        add(getMenu(), content);
    }

    private Component getMenu() {
        VerticalLayout menuBar = new VerticalLayout();
        menuBar.setMinWidth(250, Unit.PIXELS);
        menuBar.setMaxWidth(250, Unit.PIXELS);
        menuBar.setHeightFull();
        menuBar.getElement().getStyle().set("background-color", "#f2f2f2");

        SideNav nav = new SideNav();
        nav.setWidthFull();
        nav.onEnabledStateChanged(true);
        nav.addItem(new SideNavItem("Home", HomePage.class));
        nav.addItem(new SideNavItem("Absensi Siswa", AttendancePage.class));
        nav.addItem(new SideNavItem("Pendaftaran Siswa Baru", StudentPage.class));
        nav.addItem(new SideNavItem("Jadwal Pelajaran", SchedulePage.class));

        VerticalLayout expanded = new VerticalLayout();
        expanded.setHeightFull();

        Button logoutBt = new Button("Logout");
        logoutBt.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        logoutBt.addClickListener(e -> {
            VaadinSession.getCurrent().getSession().invalidate();
            //UI.getCurrent().navigate("/sign-in");
        });

//        Button merchantBt = new Button("Absensi");
//        merchantBt.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
//        merchantBt.addClickListener(e -> UI.getCurrent().navigate("/attendance"));
//
//        Button paymentMethodBt = new Button("Siswa");
//        paymentMethodBt.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
//        paymentMethodBt.addClickListener(e -> UI.getCurrent().navigate("/student"));
//
//        Button bankBt = new Button("Jadwal Pelajaran");
//        bankBt.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
//        bankBt.addClickListener(e -> UI.getCurrent().navigate("/schedule"));

        menuBar.add(nav, expanded, logoutBt);
        return menuBar;
    }

}
