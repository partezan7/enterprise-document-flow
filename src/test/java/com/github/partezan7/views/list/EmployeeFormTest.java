package com.github.partezan7.views.list;

import com.github.partezan7.data.entity.Department;
import com.github.partezan7.data.entity.Employee;
import com.github.partezan7.data.entity.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmployeeFormTest {
    private List<Department> companies;
    private List<Status> statuses;
    private Employee marcUsher;
    private Department department1;
    private Department department2;
    private Status status1;
    private Status status2;

    @BeforeEach
    public void setupData() {
        companies = new ArrayList<>();
        department1 = new Department();
        department1.setName("Vaadin Ltd");
        department2 = new Department();
        department2.setName("IT Mill");
        companies.add(department1);
        companies.add(department2);

        statuses = new ArrayList<>();
        status1 = new Status();
        status1.setName("Status 1");
        status2 = new Status();
        status2.setName("Status 2");
        statuses.add(status1);
        statuses.add(status2);

        marcUsher = new Employee();
        marcUsher.setFirstName("Marc");
        marcUsher.setLastName("Usher");
        marcUsher.setEmail("marc@usher.com");
        marcUsher.setStatus(status1);
        marcUsher.setCompany(department2);
    }

    @Test
    public void formFieldsPopulated() {
        EmployeeForm form = new EmployeeForm(companies, statuses);
        form.setEmployee(marcUsher);
        assertEquals("Marc", form.firstName.getValue());
        assertEquals("Usher", form.lastName.getValue());
        assertEquals("marc@usher.com", form.email.getValue());
        assertEquals(department2, form.company.getValue());
        assertEquals(status1, form.status.getValue());
    }

    @Test
    public void saveEventHasCorrectValues() {
        EmployeeForm form = new EmployeeForm(companies, statuses);
        Employee employee = new Employee();
        form.setEmployee(employee);
        form.firstName.setValue("John");
        form.lastName.setValue("Doe");
        form.company.setValue(department1);
        form.email.setValue("john@doe.com");
        form.status.setValue(status2);

        AtomicReference<Employee> savedContactRef = new AtomicReference<>(null);
        form.addSaveListener(e -> {
            savedContactRef.set(e.getEmployee());
        });
        form.save.click();
        Employee savedEmployee = savedContactRef.get();

        assertEquals("John", savedEmployee.getFirstName());
        assertEquals("Doe", savedEmployee.getLastName());
        assertEquals("john@doe.com", savedEmployee.getEmail());
        assertEquals(department1, savedEmployee.getCompany());
        assertEquals(status2, savedEmployee.getStatus());
    }
}