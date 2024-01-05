package com.github.partezan7.data.service;

import com.github.partezan7.data.entity.Employee;
import com.github.partezan7.data.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAllEmployees(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return employeeRepository.findAll();
        } else {
            return employeeRepository.search(stringFilter);
        }
    }

    public long countContacts() {
        return employeeRepository.count();
    }

    public void deleteEmployee(Employee employee) {
        employeeRepository.delete(employee);
    }

    public void saveEmployee(Employee employee) {
        if (employee == null) {
            System.err.println("Employee is null. Are you sure you have connected your form to the application?");
            return;
        }
        employeeRepository.save(employee);
    }
}
