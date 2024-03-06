package com.schfoo.force.feapp.page;

import com.schfoo.force.feapp.component.BaseLayout;
import com.schfoo.force.feapp.component.MainLayout;
import com.schfoo.force.feapp.service.StudentService;
import com.schfoo.force.model.web.res.UserRes;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
@UIScope
@SpringComponent
@PageTitle("School Foo - Absensi")
@Route(value = "/attendance", layout = MainLayout.class)
public class AttendancePage extends BaseLayout {

    Button btClock;
    Grid<UserRes> gridStudent;

    @Override
    protected void buildView() {
        btClock = new Button("Masuk");
        gridStudent = new Grid<>();

        gridStudent.addColumn(UserRes::getCode).setHeader("Kode");
        gridStudent.addColumn(UserRes::getFullName).setHeader("Nama");

        add(btClock, gridStudent);

        initData();
    }

    @Autowired
    private StudentService studentService;

    private void initData() {
        List<UserRes> students = studentService.getStudents();
        gridStudent.setItems(students);
    }

}
