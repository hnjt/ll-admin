package com.ll.admin.domain;

import com.commons.EntityVo;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "role_menus")
public class RoleMenus extends EntityVo implements Serializable {

    private static final long serialVersionUID = 7L;

    @Id
//    @GeneratedValue(generator = "idGenerator")
//    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @Column(name = "ID",length = 32)
    private String id;

    @Column(name = "ROLE_ID")
    private String roleId;

    @Column(name = "MENUS_ID")
    private String menusId;

    //权限操作码，初始化/关闭：0；1：开启；
    @Column(name = "FUN_CODE")
    private String funCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMenusId() {
        return menusId;
    }

    public void setMenusId(String menusId) {
        this.menusId = menusId;
    }

    public String getFunCode() {
        return funCode;
    }

    public void setFunCode(String funCode) {
        this.funCode = funCode;
    }
}
