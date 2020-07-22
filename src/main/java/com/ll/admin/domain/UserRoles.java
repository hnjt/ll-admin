package com.ll.admin.domain;

import com.ll.commons.EntityVo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 用户角色关系
 */
@Entity
@Table(name = "login_roles")
public class UserRoles extends EntityVo implements Serializable {

    private static final long serialVersionUID = 3L;

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "LOGIN_ID")
    private String loginId;

    @Column(name = "ROLES_ID")
    private String rolesId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getRolesId() {
        return rolesId;
    }

    public void setRolesId(String rolesId) {
        this.rolesId = rolesId;
    }
}
