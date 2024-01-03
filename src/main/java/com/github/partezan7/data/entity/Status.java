package com.github.partezan7.data.entity;

import jakarta.persistence.Entity;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class Status extends AbstractEntity {
    private String name;

    public Status() {

    }

    public Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
