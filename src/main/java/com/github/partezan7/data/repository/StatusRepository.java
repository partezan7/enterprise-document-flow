package com.github.partezan7.data.repository;

import com.github.partezan7.data.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface StatusRepository extends CrudRepository<Status, Long> {
    Page<Status> findAll(Pageable pageable);
}
