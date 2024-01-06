package com.github.partezan7.views.list;

import com.github.partezan7.data.entity.Status;
import com.github.partezan7.data.service.StatusService;
import com.github.partezan7.security.SecurityService;
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
 * Вкладка "Статусы"
 */
@SpringComponent
@Scope("prototype")
@PermitAll
@Route(value = "status", layout = MainLayout.class)
@PageTitle("Статусы")
public class StatusListView extends VerticalLayout {
    final Grid<Status> grid;
    final Binder<Status> binder;
    final Editor<Status> editor;
    private final TextField addStatusField;
    private final StatusService service;
    private final SecurityService securityService;
    private Optional<Grid.Column<Status>> currentColumn = Optional.empty();
    private Optional<Status> currentItem = Optional.empty();

    public StatusListView(StatusService service, SecurityService securityService) {
        this.service = service;
        this.securityService = securityService;
        this.grid = new Grid<>(Status.class);
        this.binder = new BeanValidationBinder<>(Status.class);
        this.editor = grid.getEditor();
        this.addStatusField = new TextField();

        addClassName("Status-list-view");
        setSizeFull();
        configureGrid();
        add(getToolbar(), getContent());
        updateList();
    }

    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid);
        content.addClassNames("Status-content");
        content.setSizeFull();
        return content;
    }

    private void saveStatus(String StatusName) {
        if (StatusName.isEmpty()) return;
        Status Status = new Status();
        Status.setName(StatusName);
        service.save(Status);
        updateList();
    }

    private void deleteStatus() {
        Status status = grid.asSingleSelect().getValue();
        if (status != null) {
            service.delete(status);
            updateList();
        }
    }

    private void configureGrid() {
        grid.addClassNames("Status-grid");
        grid.setSizeFull();

        editor.setBinder(binder);
        editor.setBuffered(true);

        // Save Listener to save the changed Status
        editor.addSaveListener(event -> {
            Status Status = event.getItem();
            service.update(Status);
        });

        TextField textName = new TextField();
        textName.setWidthFull();
        binder.forField(textName)
                .bind("name");
        grid.setColumns("name");
        List<Grid.Column<Status>> columns = grid.getColumns();
        columns.get(0)
                .setHeader("Название статуса")
                .setEditorComponent(textName);
        columns.forEach(col -> col.setAutoWidth(true));

        grid.setItems(query -> service.statusPage(
                        PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);


        // If a row is selected open the Status in the editor
        grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(Status -> {
            editor.save();

            if (!editor.isOpen()) {
                grid.getEditor().editItem(Status);

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
        Shortcuts.addShortcutListener(grid, event -> currentItem.ifPresent(grid::select), Key.ENTER).listenOn(grid);

        // Cancel the editor on Escape
        Shortcuts.addShortcutListener(grid, () -> {
            if (editor.isOpen()) {
                editor.cancel();
            }
        }, Key.ESCAPE).listenOn(grid);

    }

    private Component getToolbar() {
        addStatusField.setPlaceholder("Подразделение");
        addStatusField.setValueChangeMode(ValueChangeMode.LAZY);

        Button addStatusButton = new Button("Добавить");
        addStatusButton.addClickListener(click -> addStatus());

        Button deleteStatusButton = new Button("Удалить");
        deleteStatusButton.addClickListener(click -> deleteStatus());

        var toolbar = new HorizontalLayout(addStatusField, addStatusButton, deleteStatusButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }


    private void addStatus() {
        grid.asSingleSelect().clear();
        saveStatus(addStatusField.getValue());
        addStatusField.clear();
    }


    private void updateList() {
        grid.setItems(service.findAll());
    }

}