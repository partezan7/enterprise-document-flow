package com.github.partezan7.data.repository;

import com.github.partezan7.data.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<Department, Long> {
    Page<Department> findAll(Pageable pageable);
}
