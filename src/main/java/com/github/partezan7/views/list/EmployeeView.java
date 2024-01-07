package com.github.partezan7.views.list;

import com.github.partezan7.data.entity.Employee;
import com.github.partezan7.data.entity.user.Role;
import com.github.partezan7.data.service.DepartmentService;
import com.github.partezan7.data.service.EmployeeService;
import com.github.partezan7.data.service.StatusService;
import com.github.partezan7.security.SecurityService;
import com.github.partezan7.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
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
@Route(value = "", layout = MainLayout.class)
@PageTitle("Сотрудники")
public class EmployeeView extends VerticalLayout {
    final Grid<Employee> grid = new Grid<>(Employee.class);
    private final TextField filterText = new TextField();
    final EmployeeForm form;
    private final EmployeeService service;
    private final SecurityService securityService;

    public EmployeeView(EmployeeService service, DepartmentService departmentService, StatusService statusService, SecurityService securityService) {
        this.service = service;
        this.securityService = securityService;
        this.form = new EmployeeForm(departmentService.findAll(), statusService.findAll());
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("employee-content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form.setWidth("25em");
        form.addSaveListener(this::saveEmployee); // <1>
        form.addDeleteListener(this::deleteEmployee); // <2>
        form.addCloseListener(e -> closeEditor()); // <3>
    }

    private void saveEmployee(EmployeeForm.SaveEvent event) {
        service.save(event.getEmployee());
        updateList();
        closeEditor();
    }

    private void deleteEmployee(EmployeeForm.DeleteEvent event) {
        service.delete(event.getEmployee());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassNames("employee-grid");
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName", "email");
        grid.getColumns().get(0).setHeader("Имя");
        grid.getColumns().get(1).setHeader("Фамилия");
        grid.addColumn(contact -> contact.getStatus().getName()).setHeader("Статус");
        grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Подразделение");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (securityService.isUserHaveRights(Role.ADMIN)) editEmployee(event.getValue());
        });

    }

    private Component getToolbar() {
        filterText.setPlaceholder("Найти по ФИО");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addEmployeeButton = new Button("Добавить");
        addEmployeeButton.addClickListener(click -> addEmployee());

        var toolbar = new HorizontalLayout(filterText);
        if (securityService.isUserHaveRights(Role.ADMIN)) toolbar.add(addEmployeeButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editEmployee(Employee employee) {
        if (employee == null) {
            closeEditor();
        } else {
            form.setEmployee(employee);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setEmployee(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addEmployee() {
        grid.asSingleSelect().clear();
        editEmployee(new Employee());
    }


    private void updateList() {
        grid.setItems(service.findAll(filterText.getValue()));
    }
}