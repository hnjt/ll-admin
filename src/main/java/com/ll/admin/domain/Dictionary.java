package com.ll.admin.domain;

import com.commons.EntityVo;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class Dictionary extends EntityVo implements Serializable {

    private static final long serialVersionUID = 7L;

    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @Column(name = "ID",length = 32)
    private String id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "P_CODE")
    private String pCode;

    @Column(name = "ORDER_NO")
    private Integer orderNo;

    @Column(name = "NAME")
    private String name;

    @Column(name = "IS_PERMIT_DELETE")
    private Integer isPermitDelete;

    @Column(name = "REMARKS")
    private String remarks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = StringUtils.isNotEmpty( code )?code.toUpperCase():code;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = StringUtils.isNotEmpty( pCode )?pCode.toUpperCase():pCode;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsPermitDelete() {
        return isPermitDelete;
    }

    public void setIsPermitDelete(Integer isPermitDelete) {
        this.isPermitDelete = isPermitDelete;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
