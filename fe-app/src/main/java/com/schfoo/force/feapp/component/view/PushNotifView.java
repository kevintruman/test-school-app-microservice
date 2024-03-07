package com.schfoo.force.feapp.component.view;

import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

import java.util.List;

public interface PushNotifView {

    class Success {
        private static Notification notif;

        public static void show(String text) {
            setTheme();
            notif.setText(text);
            notif.open();
        }

        public static void show(List<String> text) {
            setTheme();
            Div div = new Div();
            text.forEach(e -> div.add(new Text(e), new HtmlComponent("br")));
            div.remove(div.getComponentAt(div.getComponentCount() - 1));
            notif.add(div);
            notif.open();
        }

        private static void setTheme() {
            notif = new Notification();
            notif.removeAll();
            notif.setDuration(5000);
            notif.setPosition(Notification.Position.TOP_END);
            notif.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        }
    }

    class Error {
        private static Notification notif;

        public static void show(String text) {
            setTheme();
            notif.setText(text);
            notif.open();
        }

        public static void show(List<String> text) {
            setTheme();
            Div div = new Div();
            text.forEach(e -> div.add(new Text(e), new HtmlComponent("br")));
            div.remove(div.getComponentAt(div.getComponentCount() - 1));
            notif.add(div);
            notif.open();
        }

        private static void setTheme() {
            notif = new Notification();
            notif.removeAll();
            notif.setDuration(5000);
            notif.setPosition(Notification.Position.TOP_END);
            notif.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

}
