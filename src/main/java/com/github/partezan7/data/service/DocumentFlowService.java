package com.github.partezan7.data.service;

import com.github.partezan7.data.entity.Department;
import com.github.partezan7.data.entity.Employee;
import com.github.partezan7.data.entity.Status;
import com.github.partezan7.data.repository.DepartmentRepository;
import com.github.partezan7.data.repository.EmployeeRepository;
import com.github.partezan7.data.repository.StatusRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    public void saveDepartment(Department department) {
        if (department == null) {
            System.out.println("department is null");
            return;
        }
        departmentRepository.save(department);
    }

    public void updateDepartment(Department department) {
        saveDepartment(department);
    }

    public void deleteDepartment(Department department) {
        departmentRepository.delete(department);
    }

    public List<Department> findAllDepartments() {
        Iterable<Department> departments = departmentRepository.findAll();
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(departments.iterator(), Spliterator.ORDERED), false)
                .collect(Collectors.toList());
    }

    public List<Status> findAllStatuses() {
        Iterable<Status> statuses = statusRepository.findAll();
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(statuses.iterator(), Spliterator.ORDERED), false)
                .collect(Collectors.toList());
    }

    public Page<Department> departmentList(Pageable pageable) {
        return departmentRepository.findAll(pageable);
    }

    public Page<Status> statusList(Pageable pageable) {
        return statusRepository.findAll(pageable);
    }

    public void updateStatus(Status status) {
        saveStatus(status);
    }

    public void saveStatus(Status status) {
        if (status == null) {
            System.out.println("status is null");
            return;
        }
        statusRepository.save(status);
    }

    public void deleteStatus(Status status) {
        statusRepository.delete(status);
    }
}