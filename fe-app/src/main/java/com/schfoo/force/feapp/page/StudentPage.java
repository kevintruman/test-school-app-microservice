package com.schfoo.force.feapp.page;

import com.schfoo.force.feapp.component.BaseLayout;
import com.schfoo.force.feapp.component.MainLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UIScope
@SpringComponent
@PageTitle("School Foo - Siswa")
@Route(value = "/student", layout = MainLayout.class)
public class StudentPage extends BaseLayout {

    @Override
    protected void buildView() {

    }

}
