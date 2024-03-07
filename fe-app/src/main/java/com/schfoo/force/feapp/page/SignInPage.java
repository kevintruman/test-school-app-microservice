package com.schfoo.force.feapp.page;

import com.schfoo.force.feapp.component.BaseLayout;
import com.schfoo.force.feapp.component.view.PushNotifView;
import com.schfoo.force.feapp.service.AuthService;
import com.schfoo.force.helper.exception.ResException;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@UIScope
@SpringComponent
@PageTitle("School Foo - Sign In")
@Route("/sign-in")
public class SignInPage extends BaseLayout {

    private TextField userTx;
    private PasswordField passTx;

    @Override
    protected void buildView() {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setHeight(100, Unit.VH);

        VerticalLayout content = new VerticalLayout();
        content.setMaxWidth(defTxfWidthL);
        content.setMinWidth(defTxfWidthL);

        NativeLabel caption = new NativeLabel("School Foo");
        caption.setWidth("20");

        userTx = new TextField();
        userTx.setPlaceholder("Username");
        userTx.setWidthFull();

        passTx = new PasswordField();
        passTx.setPlaceholder("Password");
        passTx.setWidthFull();

        Button loginBt = new Button("Login");
        loginBt.setWidthFull();
        loginBt.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        loginBt.addClickListener(e -> login());
        loginBt.addClickShortcut(Key.ENTER);

        content.add(caption, userTx, passTx, loginBt);
        add(content);
    }

    @Autowired
    private AuthService authService;

    private void login() {
        try {
            authService.login(userTx.getValue().trim(), passTx.getValue());
        } catch (ResException e) {
            //e.printStackTrace();
            PushNotifView.Error.show(e.getErrorsMessage());
        }
    }

}
