package com.ll.admin.domain;

import com.commons.EntityVo;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class Menu extends EntityVo implements Serializable {

    private static final long serialVersionUID = 6L;

    @Id
//    @GeneratedValue(generator = "idGenerator")
//    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @Column(name = "ID",length = 32)
    private String id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "MENU_URL")
    private String menuUrl;

    @Column(name = "PID")
    private String pid;

    @Column(name = "ORDER_NO")
    private Integer orderNo;

    @Column(name = "ICON")
    private String icon;

    @Column(name = "PATH")
    private String path;

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

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", menuUrl='" + menuUrl + '\'' +
                ", pid='" + pid + '\'' +
                ", orderNo=" + orderNo +
                ", icon='" + icon + '\'' +
                ", path='" + path + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", creator='" + creator + '\'' +
                ", modifier='" + modifier + '\'' +
                '}';
    }
}
