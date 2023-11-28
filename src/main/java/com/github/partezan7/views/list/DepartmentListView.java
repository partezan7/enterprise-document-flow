package com.github.partezan7.views.list;

import com.github.partezan7.data.entity.Department;
import com.github.partezan7.data.entity.Employee;
import com.github.partezan7.data.service.DocumentFlowService;
import com.github.partezan7.security.SecurityService;
import com.github.partezan7.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import org.springframework.context.annotation.Scope;

/**
 * Вкладка "Список сотрудников"
 */
@SpringComponent
@Scope("prototype")
@PermitAll
@Route(value = "department", layout = MainLayout.class)
@PageTitle("Подразделения")
public class DepartmentListView extends VerticalLayout {
    final Grid<Department> grid = new Grid<>(Department.class);
    final Editor<Department> editor = grid.getEditor();
    final Binder<Department> binder = new Binder<>(Department.class);
    private final TextField textField = new TextField();
    private final DocumentFlowService service;
    private final SecurityService securityService;

    public DepartmentListView(DocumentFlowService service, SecurityService securityService) {
        this.service = service;
        this.securityService = securityService;
        addClassName("department-list-view");
        setSizeFull();
        configureGrid();
        add(getToolbar(), getContent());
        updateList();
    }

    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid);
        content.addClassNames("department-content");
        content.setSizeFull();
        return content;
    }

    private void saveDepartment(String departmentName) {
        if (departmentName.isEmpty()) return;
        Department department = new Department();
        department.setName(departmentName);
        service.saveDepartment(department);
        updateList();
    }

    private void deleteDepartment() {
        Department department = grid.asSingleSelect().getValue();
        if (department != null) {
            service.deleteDepartment(department);
            updateList();
        }
    }

    private void configureGrid() {
        grid.addClassNames("department-grid");
        grid.setSizeFull();
        grid.setColumns("name");
        grid.getColumns().get(0).setHeader("Название подразделения");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.addItemDoubleClickListener(e -> {
            editor.editItem(e.getItem());
            Component editorComponent = e.getColumn().getEditorComponent();
            if (editorComponent instanceof Focusable) {
                ((Focusable) editorComponent).focus();
            }
        });


        editor.setBinder(binder);
        editor.setBuffered(true);

        TextField name = new TextField();
        name.setWidthFull();

        // FIXME: 24.11.2023 not working, needing to implement
        binder.forField(name)
                .asRequired("Department name must not be empty")
                .bind(Department::getName, (department, string) -> {
                    department.setName(string);
                    service.updateDepartment(department);
                });
        grid.getColumns().get(0).setEditorComponent(name);
    }

    private Component getToolbar() {
        textField.setPlaceholder("Подразделение");
        textField.setValueChangeMode(ValueChangeMode.LAZY);

        Button addDepartmentButton = new Button("Добавить");
        addDepartmentButton.addClickListener(click -> addDepartment());

        Button deleteDepartmentButton = new Button("Удалить");
        deleteDepartmentButton.addClickListener(click -> deleteDepartment());

        var toolbar = new HorizontalLayout(textField, addDepartmentButton, deleteDepartmentButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

//    public void editDepartment(Department department) {
//        if (department == null) {
////            closeEditor();
//        } else {
//            form.setContact(employee);
//            form.setVisible(true);
//            addClassName("editing");
//        }
//    }

//    private void closeEditor() {
//        form.setContact(null);
//        form.setVisible(false);
//        removeClassName("editing");
//    }

    private void addDepartment() {
        grid.asSingleSelect().clear();
        saveDepartment(textField.getValue());
        textField.clear();
    }


    private void updateList() {
        grid.setItems(service.findAllDepartments());
    }
}