package com.schfoo.force.feapp.component;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;

@SpringComponent
@UIScope
@StyleSheet("/common.css")
@Slf4j
public abstract class BaseLayout extends VerticalLayout {

    protected abstract void buildView();

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        init();
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        onClose();
        removeAll();
        super.onDetach(detachEvent);
    }

    protected void onClose() {}

    protected static final String defBtWidthS = "100px";
    protected static final String defBtWidthM = "150px";
    protected static final String defBtWidthL = "200px";
    protected static final String defBtWidthX = "250px";
    protected static final String defTxfWidthM = "250px";
    protected static final String defTxfWidthL = "300px";

    protected void init() {
        String address = VaadinSession.getCurrent().getBrowser().getAddress();
        log.info("access={} from={}", this.getClass().getName(), address);
        setHeight(100, Unit.VH);
        setWidthFull();
        buildView();
    }

}
