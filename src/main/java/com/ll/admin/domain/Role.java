package com.ll.admin.domain;

import com.commons.EntityVo;

import java.io.Serializable;

import javax.persistence.*;

/**
 * 角色信息
 */
@Entity
@Table
public class Role extends EntityVo implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id
    @Column(name = "ID")
    private String id;
    @Column(name = "NAME")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
