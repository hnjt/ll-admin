package com.ll.admin.domain;

import com.ll.commons.EntityVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ll.utils.EntityValidator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * 登录信息
 */
@Entity
@Table
public class Login extends EntityVo implements Serializable,UserDetails{

    private static final long serialVersionUID = 1L;

    @Id
//    @GeneratedValue(generator = "idGenerator")
//    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @Column(name = "ID",length = 32)
    private String id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "USER_NAME")
    private String username;

    @Column(name = "PASS_WORD")
    private String password;

    @Column(name = "LOGIN_IP")
    private String loginIp;

    /**
     * LOGIN 状态码
     * 200：允许登录
     * 400：锁定
     * 500：禁用
     */
    @Column(name = "STARTS")
    private String starts;

    @Column(name = "LOGIN_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date loginTime;

    @Column(name = "LOGOUT_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date logoutTime;

    @Column(name = "CNT")
    private Integer cnt;

    @ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>( );


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
        if (!EntityValidator.isChinese(name))
            throw new RuntimeException( "需要使用1-9字中文名称" );
        this.name = name;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (!EntityValidator.isUserName(username))
            throw new RuntimeException( "帐户不符合规定" );
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getStarts() {
        return starts;
    }

    public void setStarts(String starts) {
        this.starts = starts;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Login{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", loginIp='" + loginIp + '\'' +
                ", starts='" + starts + '\'' +
                ", loginTime=" + loginTime +
                ", updateTime=" + updateTime +
                ", cnt=" + cnt +
                ", roles=" + roles +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", creator='" + creator + '\'' +
                ", modifier='" + modifier + '\'' +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> auths = new ArrayList<>();
        Set<Role> roles = this.getRoles();
        for (Role role : roles) {
            auths.add( new SimpleGrantedAuthority( role.getName() ) );
        }
        return auths;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
