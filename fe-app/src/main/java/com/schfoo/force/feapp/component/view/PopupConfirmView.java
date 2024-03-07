package com.schfoo.force.feapp.component.view;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.apache.logging.log4j.util.Strings;
import org.springframework.lang.Nullable;

public class PopupConfirmView {

    public static void build(HasComponents parent, @Nullable Runnable onYes) {
        build(parent, null, null, onYes, null);
    }
    public static void build(
            HasComponents parent, @Nullable String title, @Nullable String description,
            @Nullable Runnable onYes, @Nullable Runnable onNo) {
        if (Strings.isBlank(title)) {
            title = "Confirmation";
        }
        if (Strings.isBlank(description)) {
            description = "Are You Sure?";
        }

        Dialog confirmDialog = new Dialog();
        confirmDialog.setHeaderTitle(title);
        confirmDialog.setWidth(30, Unit.VW);
        parent.remove(confirmDialog);
        parent.add(confirmDialog);

        VerticalLayout vl = new VerticalLayout();
        vl.setWidthFull();
        vl.add(new NativeLabel(description));
        confirmDialog.add(vl);

        Button yesBt = new Button("Yes", e -> {
            if (onYes != null) {
                onYes.run();
            }
            confirmDialog.close();
        });
        yesBt.setWidthFull();
        yesBt.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        VerticalLayout yesVl = new VerticalLayout();
        yesVl.setMargin(false);
        yesVl.setPadding(false);
        yesVl.setWidthFull();
        yesVl.add(yesBt);

        Button noBt = new Button("No", e -> {
            if (onNo != null) {
                onNo.run();
            }
            confirmDialog.close();
        });
        noBt.setWidthFull();
        VerticalLayout noVl = new VerticalLayout();
        noVl.setMargin(false);
        noVl.setPadding(false);
        noVl.setWidthFull();
        noVl.add(noBt);

        HorizontalLayout hlFooter = new HorizontalLayout(noVl, yesVl);
        hlFooter.setWidthFull();
        confirmDialog.getFooter().add(hlFooter);
        confirmDialog.open();
    }

}
