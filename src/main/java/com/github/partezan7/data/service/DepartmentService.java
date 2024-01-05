package com.github.partezan7.data.service;

import com.github.partezan7.data.entity.Department;
import com.github.partezan7.data.repository.DepartmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
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

    public Page<Department> departmentList(Pageable pageable) {
        return departmentRepository.findAll(pageable);
    }

}
