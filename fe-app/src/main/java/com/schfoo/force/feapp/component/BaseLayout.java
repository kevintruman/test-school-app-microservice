package com.schfoo.force.feapp.component;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.lang.Nullable;

@SpringComponent
@UIScope
@StyleSheet("/common.css")
@Slf4j
public abstract class BaseLayout extends VerticalLayout {

    protected abstract void buildView();

    protected static final String defBtWidthS = "100px";
    protected static final String defBtWidthM = "150px";
    protected static final String defBtWidthL = "200px";
    protected static final String defBtWidthX = "250px";
    protected static final String defTxfWidthM = "250px";
    protected static final String defTxfWidthL = "300px";

    @PostConstruct
    protected void init() {
        log.info("access={}", this.getClass().getName());
        setHeight(100, Unit.VH);
        setWidthFull();
        buildView();
    }

    protected void confirmModalView(@Nullable ClickEvent onYes) {
        confirmModalView(null, null, onYes, null);
    }
    protected void confirmModalView(
            @Nullable String title, @Nullable String description, @Nullable ClickEvent onYes, @Nullable ClickEvent onNo) {
        if (Strings.isBlank(title)) {
            title = "Confirmation";
        }
        if (Strings.isBlank(description)) {
            description = "Are You Sure?";
        }

        Dialog confirmDialog = new Dialog();
        confirmDialog.setHeaderTitle(title);
        confirmDialog.setWidth(30, Unit.VW);
        remove(confirmDialog);
        add(confirmDialog);

        VerticalLayout vl = new VerticalLayout();
        vl.setWidthFull();
        vl.add(new NativeLabel(description));
        confirmDialog.add(vl);

        Button yesBt = new Button("Yes", e -> {
            if (onYes != null) {
                onYes.accept();
            }
            confirmDialog.close();
        });
        yesBt.setWidthFull();
        yesBt.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button noBt = new Button("No", e -> {
            if (onNo != null) {
                onNo.accept();
            }
            confirmDialog.close();
        });
        noBt.setWidthFull();

        HorizontalLayout hlFooter = new HorizontalLayout(noBt, yesBt);
        hlFooter.setWidthFull();
        confirmDialog.getFooter().add(hlFooter);
        confirmDialog.open();
    }

    @FunctionalInterface
    protected interface ClickEvent {
        void accept();
    }

}
