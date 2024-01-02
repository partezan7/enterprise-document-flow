package com.github.partezan7.data.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;

import java.util.LinkedList;
import java.util.List;

@Entity
@DynamicUpdate
public class Department extends AbstractEntity {
    @NotBlank
    private String name;

    @OneToMany(mappedBy = "department")
    @Nullable
    private List<Employee> employees = new LinkedList<>();

    @Formula("(select count(c.id) from Employee c where c.department_id = id)")
    private int employeeCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }
}
