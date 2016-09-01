package com.example.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by clint on 8/31/16.
 */

@Entity
public class User extends AuditingBaseEntity{

    @Id
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
