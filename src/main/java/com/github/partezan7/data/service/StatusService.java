package com.github.partezan7.data.service;

import com.github.partezan7.data.entity.Status;
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
public class StatusService implements EntityService<Status> {
    private final StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public List<Status> findAll() {
        Iterable<Status> statuses = statusRepository.findAll();
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(statuses.iterator(), Spliterator.ORDERED), false)
                .collect(Collectors.toList());
    }


    public Page<Status> statusPage(Pageable pageable) {
        return statusRepository.findAll(pageable);
    }

    @Override
    public void update(Status status) {
        save(status);
    }

    @Override
    public void save(Status status) {
        if (status == null) {
            System.out.println("status is null");
            return;
        }
        statusRepository.save(status);
    }

    @Override
    public void delete(Status status) {
        statusRepository.delete(status);
    }
}
