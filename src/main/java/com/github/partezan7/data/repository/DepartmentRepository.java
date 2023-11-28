package com.github.partezan7.data.repository;

import com.github.partezan7.data.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Modifying
    @Query("update Department d set d.name = ?1 where d.id = ?2")
    void updateDepartmentById(String name, Long id);
}
