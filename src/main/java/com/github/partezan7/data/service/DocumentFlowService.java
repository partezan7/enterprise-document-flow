package com.github.partezan7.data.service;

import com.github.partezan7.data.entity.Department;
import com.github.partezan7.data.entity.Employee;
import com.github.partezan7.data.entity.Status;
import com.github.partezan7.data.repository.DepartmentRepository;
import com.github.partezan7.data.repository.EmployeeRepository;
import com.github.partezan7.data.repository.StatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentFlowService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final StatusRepository statusRepository;

    public DocumentFlowService(EmployeeRepository employeeRepository,
                               DepartmentRepository departmentRepository,
                               StatusRepository statusRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.statusRepository = statusRepository;
    }

    public List<Employee> findAllContacts(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return employeeRepository.findAll();
        } else {
            return employeeRepository.search(stringFilter);
        }
    }

    public long countContacts() {
        return employeeRepository.count();
    }

    public void deleteContact(Employee employee) {
        employeeRepository.delete(employee);
    }

    public void saveContact(Employee employee) {
        if (employee == null) {
            System.err.println("Employee is null. Are you sure you have connected your form to the application?");
            return;
        }
        employeeRepository.save(employee);
    }

    public List<Department> findAllCompanies() {
        return departmentRepository.findAll();
    }

    public List<Status> findAllStatuses() {
        return statusRepository.findAll();
    }
}