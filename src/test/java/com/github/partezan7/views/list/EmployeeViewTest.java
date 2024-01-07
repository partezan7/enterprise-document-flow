package com.github.partezan7.views.list;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.partezan7.data.entity.Employee;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmployeeViewTest {

    static {
        // Prevent Vaadin Development mode to launch browser window
        System.setProperty("vaadin.launch-browser", "false");
    }

    @Autowired
    private EmployeeView employeeView;

    @Test
    public void formShownWhenContactSelected() {
        Grid<Employee> grid = employeeView.grid;
        Employee firstEmployee = getFirstItem(grid);

        EmployeeForm form = employeeView.form;

        assertFalse(form.isVisible());
        grid.asSingleSelect().setValue(firstEmployee);
        assertTrue(form.isVisible());
        assertEquals(firstEmployee.getFirstName(), form.firstName.getValue());
    }

    private Employee getFirstItem(Grid<Employee> grid) {
        return ((ListDataProvider<Employee>) grid.getDataProvider()).getItems().iterator().next();
    }
}