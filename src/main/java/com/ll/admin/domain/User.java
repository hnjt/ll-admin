package com.ll.admin.domain;

import com.ll.commons.EntityVo;
import com.ll.utils.EntityValidator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 用户信息
 */
@Entity
@Table
public class User extends EntityVo implements Serializable {

    private static final long serialVersionUID = 5L;

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "IDENTITY")
    private String identity;

    @Column(name = "ADDRESS1")
    private Integer address1;

    @Column(name = "ADDRESS2")
    private Integer address2;

    @Column(name = "ADDRESS3")
    private Integer address3;

    @Column(name = "ADDRESS4")
    private String address4;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "WECHAT")
    private String wechat;

    @Column(name = "SEX")
    private Integer sex;

    @Column(name = "NATION")
    private Integer nation;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (null != phone){
            if (!EntityValidator.isMobile( phone ))
                throw new RuntimeException( "手机号码不合规" );
        }
        this.phone = phone;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        if (null != identity){
            if (!EntityValidator.isIDCard( identity ))
                throw new RuntimeException( "身份证号码不合规" );
        }
        this.identity = identity;
    }

    public Integer getAddress1() {
        return address1;
    }

    public void setAddress1(Integer address1) {
        this.address1 = address1;
    }

    public Integer getAddress2() {
        return address2;
    }

    public void setAddress2(Integer address2) {
        this.address2 = address2;
    }

    public Integer getAddress3() {
        return address3;
    }

    public void setAddress3(Integer address3) {
        this.address3 = address3;
    }

    public String getAddress4() {
        return address4;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (null != email){
            if (!EntityValidator.isEmail( email ))
                throw new RuntimeException( "邮箱格式不合规" );
        }
        this.email = email;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getNation() {
        return nation;
    }

    public void setNation(Integer nation) {
        this.nation = nation;
    }
}
