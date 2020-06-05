package com.ll.admin.domain;

import javax.persistence.GeneratedValue;
import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table
public class Role implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue
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


    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
