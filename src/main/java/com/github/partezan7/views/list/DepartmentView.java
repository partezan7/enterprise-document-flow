package com.github.partezan7.views.list;

import com.github.partezan7.data.entity.Department;
import com.github.partezan7.data.service.DepartmentService;
import com.github.partezan7.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Shortcuts;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import jakarta.annotation.security.PermitAll;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

/**
 * Вкладка "Подразделения"
 */
@SpringComponent
@Scope("prototype")
@PermitAll
@Route(value = "department", layout = MainLayout.class)
@PageTitle("Подразделения")
public class DepartmentView extends VerticalLayout {
    final Grid<Department> grid;
    final Binder<Department> binder;
    final Editor<Department> editor;
    private final TextField addDepartmentField;
    private final DepartmentService service;
    private Optional<Grid.Column<Department>> currentColumn = Optional.empty();
    private Optional<Department> currentItem = Optional.empty();

    public DepartmentView(DepartmentService service) {
        this.service = service;
        this.grid = new Grid<>(Department.class);
        this.binder = new BeanValidationBinder<>(Department.class);
        this.editor = grid.getEditor();
        this.addDepartmentField = new TextField();

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
        service.save(department);
        updateList();
    }

    private void deleteDepartment() {
        Department department = grid.asSingleSelect().getValue();
        if (department != null) {
            service.delete(department);
            updateList();
        }
    }

    private void configureGrid() {
        grid.addClassNames("department-grid");
        grid.setSizeFull();

        editor.setBinder(binder);
        editor.setBuffered(true);

        // Save Listener to save the changed Department
        editor.addSaveListener(event -> {
            Department department = event.getItem();
            service.update(department);
        });

        TextField textName = new TextField();
        textName.setWidthFull();
        binder.forField(textName)
                .bind("name");
        grid.setColumns("name");
        List<Grid.Column<Department>> columns = grid.getColumns();
        columns.get(0)
                .setHeader("Название подразделения")
                .setEditorComponent(textName);
        columns.forEach(col -> col.setAutoWidth(true));

        grid.setItems(query -> service.departmentPage(
                        PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);


        // If a row is selected open the Department in the editor
        grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(department -> {
            editor.save();

            if (!editor.isOpen()) {
                grid.getEditor().editItem(department);

                currentColumn.ifPresent(column -> {
                    if (column.getEditorComponent() instanceof Focusable<?> focusable) {
                        focusable.focus();
                    }
                });
            }
        }));

        grid.addCellFocusListener(event -> {
            // Store the item on cell focus. Used in the ENTER ShortcutListener
            currentItem = event.getItem();
            // Store the current column. Used in the SelectionListener to focus the editor component
            currentColumn = event.getColumn();
        });

        // Select row on enter
        Shortcuts.addShortcutListener(grid, event -> {
                    if (editor.isOpen()) {
                        editor.save();
                    } else {
                        currentItem.ifPresent(grid::select);
                    }
                }, Key.ENTER)
                .listenOn(grid);

        // Cancel the editor on Escape
        Shortcuts.addShortcutListener(grid, () -> {
            if (editor.isOpen()) {
                editor.cancel();
            }
        }, Key.ESCAPE).listenOn(grid);

    }

    private Component getToolbar() {
        addDepartmentField.setPlaceholder("Подразделение");
        addDepartmentField.setValueChangeMode(ValueChangeMode.LAZY);

        Button addDepartmentButton = new Button("Добавить");
        addDepartmentButton.addClickListener(click -> addDepartment());

        Button deleteDepartmentButton = new Button("Удалить");
        deleteDepartmentButton.addClickListener(click -> deleteDepartment());

        var toolbar = new HorizontalLayout(addDepartmentField, addDepartmentButton, deleteDepartmentButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }


    private void addDepartment() {
        grid.asSingleSelect().clear();
        saveDepartment(addDepartmentField.getValue());
        addDepartmentField.clear();
    }


    private void updateList() {
        grid.setItems(service.findAll());
    }

}