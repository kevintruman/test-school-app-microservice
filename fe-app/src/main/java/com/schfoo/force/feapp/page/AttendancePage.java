package com.schfoo.force.feapp.page;

import com.schfoo.force.feapp.component.BaseLayout;
import com.schfoo.force.feapp.component.MainLayout;
import com.schfoo.force.feapp.component.view.PushNotifView;
import com.schfoo.force.feapp.service.AttendanceService;
import com.schfoo.force.feapp.service.LessonService;
import com.schfoo.force.feapp.service.SessionService;
import com.schfoo.force.feapp.service.StudentService;
import com.schfoo.force.model.web.res.LessonScheduleRes;
import com.schfoo.force.model.web.res.SignInRes;
import com.schfoo.force.model.web.res.UserRes;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

@Slf4j
@UIScope
@SpringComponent
@PageTitle("School Foo - Absensi Siswa")
@Route(value = "/attendance", layout = MainLayout.class)
public class AttendancePage extends BaseLayout {

    private NativeLabel scheduleTx;
    private NativeLabel sessionTx;
    private Grid<UserRes> gridStudent;

    @Override
    protected void buildView() {
        scheduleTx = new NativeLabel("");
        sessionTx = new NativeLabel("");

        gridStudent = new Grid<>();
        gridStudent.setWidthFull();
        gridStudent.setHeightFull();
        gridStudent.setSelectionMode(Grid.SelectionMode.MULTI);
        gridStudent.addColumn(UserRes::getCode).setHeader("Kode");
        gridStudent.addColumn(UserRes::getFullName).setHeader("Nama");

        Button attendBt = new Button("Kirim Absen Masuk");
        attendBt.addClickListener(e -> doAttendStudent());
        attendBt.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        VerticalLayout expandLy = new VerticalLayout();
        expandLy.setWidthFull();
        HorizontalLayout hl = new HorizontalLayout(expandLy, attendBt);
        hl.setWidthFull();

        add(scheduleTx, sessionTx, hl, gridStudent);

        initData();
    }

    @Autowired
    private StudentService studentService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private SessionService sessionService;

    private LessonScheduleRes lessonScheduleData;

    private void initData() {
        SignInRes session = sessionService.getSession();
        UserRes user = session.getUser();

        List<LessonScheduleRes> teacherLessons = lessonService.getTeacherLesson();
        if (!CollectionUtils.isEmpty(teacherLessons)) {
            lessonScheduleData = teacherLessons.getLast();
            scheduleTx.setText(lessonScheduleData.getLessonTeacher().getTeacherUser().getFullName() + " | " +
                    lessonScheduleData.getLessonClass().getName() + " | " +
                    lessonScheduleData.getLessonTeacher().getLesson().getName());
            sessionTx.setText(lessonScheduleData.getDay() + ": " + lessonScheduleData.getStartTime() + " - " +
                    lessonScheduleData.getEndTime());
        }

        List<UserRes> students = studentService.getStudentsByCorporateId(user.getCorporate().getId(), "");
        gridStudent.setItems(students);
    }

    private void doAttendStudent() {
        Set<UserRes> selectedItems = gridStudent.getSelectionModel().getSelectedItems();
        List<Long> studentsId = selectedItems.stream().map(UserRes::getId).toList();
        attendanceService.doAttendStudent(lessonScheduleData.getId(), studentsId);

        gridStudent.deselectAll();
        PushNotifView.Success.show("Berhasil Tersimpan");
    }

}
