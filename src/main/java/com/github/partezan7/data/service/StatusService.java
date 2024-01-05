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
public class StatusService {
    private final StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public List<Status> findAllStatuses() {
        Iterable<Status> statuses = statusRepository.findAll();
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(statuses.iterator(), Spliterator.ORDERED), false)
                .collect(Collectors.toList());
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
