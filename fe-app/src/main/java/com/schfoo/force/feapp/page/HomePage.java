package com.schfoo.force.feapp.page;

import com.schfoo.force.feapp.component.BaseLayout;
import com.schfoo.force.feapp.component.MainLayout;
import com.schfoo.force.feapp.service.AttendanceService;
import com.schfoo.force.feapp.service.SessionService;
import com.schfoo.force.helper.exception.ResException;
import com.schfoo.force.helper.util.DateUtil;
import com.schfoo.force.model.web.res.AttendRes;
import com.schfoo.force.model.web.res.SignInRes;
import com.schfoo.force.model.web.res.UserRes;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@UIScope
@SpringComponent
@PageTitle("School Foo - Home")
@Route(value = "", layout = MainLayout.class)
@RouteAlias(value = "/home", layout = MainLayout.class)
public class HomePage extends BaseLayout {

    private NativeLabel welcomeTx;
    private NativeLabel clockTx;
    private Button attendBt;
    private Grid<AttendRes> historyGd;

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, HH:mm:ss");

    @Override
    protected void buildView() {
        welcomeTx = new NativeLabel("Selamat Datang ");
        clockTx = new NativeLabel(DateUtil.format(new Date(), "dd MMM yyyy, HH:mm:ss"));

        attendBt = new Button("Absen Masuk");
        attendBt.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        attendBt.addClickListener(e -> doAttend());

        historyGd = new Grid<>();
        historyGd.addColumn(e -> sdf.format(e.getClockInClient())).setHeader("Masuk");
        historyGd.addColumn(e -> Optional.ofNullable(e.getClockOutClient())
                .map(sdf::format).orElse("-")).setHeader("Keluar");

        add(welcomeTx, clockTx, attendBt, historyGd);

        initData();
    }

    @Override
    protected void onClose() {
        thread.interrupt();
    }

    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private SessionService sessionService;

    private UI ui;
    private Thread thread;
    private boolean isClockIn = true;

    private void initData() {
        SignInRes session = sessionService.getSession();
        UserRes user = session.getUser();
        welcomeTx.setText("Selamat Datang " + user.getFullName());

        ui = UI.getCurrent();
        thread = new Thread(() -> {
            while (true) {
                try {
                    ui.access(() -> clockTx.setText(DateUtil.format(new Date(), "dd MMM yyyy, HH:mm:ss")));
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                    //throw ExceptionUtil.thr(e.getMessage(), false);
                }
            }
        });
        thread.start();

        getAttendanceData();
    }

    private void getAttendanceData() {
        try {
            List<AttendRes> attendToday = attendanceService.getAttendToday();
            historyGd.setItems(attendToday);

            if (!CollectionUtils.isEmpty(attendToday)) {
                AttendRes first = attendToday.getFirst();
                if (Objects.isNull(first.getClockOutClient())) {
                    isClockIn = false;
                    changeCaptionAttendBt();
                }
            }
        } catch (ResException e) {
            log.error(e.getMessage());
        }
    }

    private void doAttend() {
        attendanceService.doAttend();
        isClockIn = !isClockIn;
        getAttendanceData();
        changeCaptionAttendBt();
    }

    private void changeCaptionAttendBt() {
        if (isClockIn) {
            attendBt.setText("Absen Masuk");
        } else {
            attendBt.setText("Absen Pulang");
        }
    }

}
