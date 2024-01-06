package com.github.partezan7.data.service;

import com.github.partezan7.data.entity.Employee;
import com.github.partezan7.data.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService implements EntityService<Employee> {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAll(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return findAll();
        } else {
            return employeeRepository.search(stringFilter);
        }
    }

    @Override
    public void delete(Employee employee) {
        employeeRepository.delete(employee);
    }

    @Override
    public void update(Employee entity) {
        employeeRepository.save(entity);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public void save(Employee employee) {
        if (employee == null) {
            System.err.println("Employee is null. Are you sure you have connected your form to the application?");
            return;
        }
        employeeRepository.save(employee);
    }
}
