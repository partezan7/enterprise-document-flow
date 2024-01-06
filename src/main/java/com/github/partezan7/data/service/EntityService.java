package com.github.partezan7.data.service;

import java.util.List;

public interface EntityService<E> {
    List<E> findAll();

    void save(E entity);

    void delete(E entity);

    void update(E entity);
}
