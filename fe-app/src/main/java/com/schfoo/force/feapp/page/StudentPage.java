package com.schfoo.force.feapp.page;

import com.schfoo.force.feapp.component.BaseLayout;
import com.schfoo.force.feapp.component.MainLayout;
import com.schfoo.force.feapp.component.view.PushNotifView;
import com.schfoo.force.feapp.service.SessionService;
import com.schfoo.force.feapp.service.StudentService;
import com.schfoo.force.helper.exception.ResException;
import com.schfoo.force.model.constant.UserConstant;
import com.schfoo.force.model.web.req.RegisterStudentGuardianReq;
import com.schfoo.force.model.web.req.RegisterStudentReq;
import com.schfoo.force.model.web.res.SignInRes;
import com.schfoo.force.model.web.res.UserRes;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@UIScope
@SpringComponent
@PageTitle("School Foo - Pendaftaran Siswa Baru")
@Route(value = "/student", layout = MainLayout.class)
public class StudentPage extends BaseLayout {

    private TextField searchTxf;
    private Grid<UserRes> studentGrd;

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");

    @Override
    protected void buildView() {
        VerticalLayout expandVl = new VerticalLayout();
        expandVl.setWidthFull();

        searchTxf = new TextField();
        searchTxf.setPlaceholder("Masukkan Nama");

        Button searchBt = new Button("Cari");
        searchBt.addClickListener(e -> initData());

        Button addBt = new Button("Tambah Siswa Baru");
        addBt.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addBt.addClickListener(e -> openForm());

        HorizontalLayout hl = new HorizontalLayout(searchTxf, searchBt, expandVl, addBt);
        hl.setWidthFull();

        studentGrd = new Grid<>();
        studentGrd.addColumn(UserRes::getFullName).setHeader("Nama");
        studentGrd.addColumn(UserRes::getGender).setHeader("Jenis Kelamin");
        studentGrd.addColumn(e -> Optional.ofNullable(e.getDob()).map(sdf::format).orElse("-"))
                .setHeader("Tanggal Lahir");
        studentGrd.addColumn(UserRes::getPhone).setHeader("Telepon");
        studentGrd.addColumn(e -> Optional.ofNullable(e.getGuardianUser()).map(UserRes::getFullName).orElse("-"))
                .setHeader("Nama Wali");
        studentGrd.addColumn(e -> Optional.ofNullable(e.getGuardianUser()).map(UserRes::getPhone).orElse("-"))
                .setHeader("Telepon Wali");

        add(hl, studentGrd);

        initData();
    }

    @Autowired
    private StudentService studentService;
    @Autowired
    private SessionService sessionService;

    private void initData() {
        SignInRes session = sessionService.getSession();
        UserRes user = session.getUser();

        List<UserRes> students = studentService.getStudentsByCorporateId(
                user.getCorporate().getId(), searchTxf.getValue());
        studentGrd.setItems(students);
    }

    private void openForm() {
        Dialog formDl = new Dialog();
        formDl.setHeaderTitle("Pendaftaran Siswa Baru");
        formDl.setWidth(30, Unit.VW);
        formDl.setCloseOnOutsideClick(false);
        remove(formDl);
        add(formDl);


        TextField studentNameTx = new TextField();
        studentNameTx.setPlaceholder("Nama Lengkap");

        DatePicker dobDt = new DatePicker();
        dobDt.setPlaceholder("Tanggal Lahir");

        TextField pobTx = new TextField();
        pobTx.setPlaceholder("Tempat Lahir");

        TextField studentPhoneTx = new TextField();
        studentPhoneTx.setPlaceholder("No. Telepon / HP");

        ComboBox<String> studentGenderCb = new ComboBox<>();
        studentGenderCb.setItems(List.of(UserConstant.Gender.male, UserConstant.Gender.female));
        studentGenderCb.setPlaceholder("Jenis Kelamin Siswa");


        TextField guardNameTx = new TextField();
        guardNameTx.setPlaceholder("Nama Wali");

        TextField guardPhoneTx = new TextField();
        guardPhoneTx.setPlaceholder("No. Telepon Wali");

        ComboBox<String> guardGenderCb = new ComboBox<>();
        guardGenderCb.setItems(List.of(UserConstant.Gender.male, UserConstant.Gender.female));
        guardGenderCb.setPlaceholder("Jenis Kelamin Wali");

        VerticalLayout studentLfVl = new VerticalLayout(studentNameTx, dobDt, studentPhoneTx);
        studentLfVl.setWidthFull();
        studentLfVl.setMargin(false);
        studentLfVl.setPadding(false);
        VerticalLayout studentRgVl = new VerticalLayout(studentGenderCb, pobTx);
        studentRgVl.setWidthFull();
        studentRgVl.setMargin(false);
        studentRgVl.setPadding(false);
        HorizontalLayout studentHl = new HorizontalLayout(studentLfVl, studentRgVl);
        studentHl.setMargin(false);
        studentHl.setPadding(false);
        studentHl.setWidthFull();

        VerticalLayout guardLfVl = new VerticalLayout(guardNameTx, guardPhoneTx);
        guardLfVl.setWidthFull();
        guardLfVl.setMargin(false);
        guardLfVl.setPadding(false);
        VerticalLayout guardRgVl = new VerticalLayout(guardGenderCb);
        guardRgVl.setWidthFull();
        guardRgVl.setMargin(false);
        guardRgVl.setPadding(false);
        HorizontalLayout guardHl = new HorizontalLayout(guardLfVl, guardRgVl);
        guardHl.setWidthFull();
        guardHl.setMargin(false);
        guardHl.setPadding(false);

        VerticalLayout vl = new VerticalLayout(studentHl, guardHl);
        vl.setWidthFull();
        vl.setMargin(false);
        vl.setPadding(false);
        formDl.add(vl);


        Button yesBt = new Button("Simpan", e -> {
            RegisterStudentGuardianReq registerStudentGuardian = new RegisterStudentGuardianReq();
            registerStudentGuardian.setFullName(guardNameTx.getValue());
            registerStudentGuardian.setPhone(guardPhoneTx.getValue());
            registerStudentGuardian.setGender(guardGenderCb.getValue());

            RegisterStudentReq registerStudent = new RegisterStudentReq();
            registerStudent.setFullName(studentNameTx.getValue());
            registerStudent.setDob(Date.from(dobDt.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            registerStudent.setPob(pobTx.getValue());
            registerStudent.setPhone(studentPhoneTx.getValue());
            registerStudent.setGender(studentGenderCb.getValue());
            registerStudent.setGuardian(registerStudentGuardian);

            try {
                studentService.registerStudent(registerStudent);
            } catch (ResException ex) {
                PushNotifView.Error.show(ex.getErrorsMessage());
                return;
            }
            PushNotifView.Success.show("Berhasil Tersimpan");
            initData();
            formDl.close();
        });
        yesBt.setWidthFull();
        yesBt.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        VerticalLayout yesVl = new VerticalLayout();
        yesVl.setMargin(false);
        yesVl.setPadding(false);
        yesVl.setWidthFull();
        yesVl.add(yesBt);

        Button noBt = new Button("Batal", e -> {
            formDl.close();
        });
        noBt.setWidthFull();
        VerticalLayout noVl = new VerticalLayout();
        noVl.setMargin(false);
        noVl.setPadding(false);
        noVl.setWidthFull();
        noVl.add(noBt);

        HorizontalLayout hlFooter = new HorizontalLayout(noVl, yesVl);
        hlFooter.setWidthFull();
        formDl.getFooter().add(hlFooter);
        formDl.open();
    }

}
