package com.chiy.rfgc.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "recruitment", schema = "rfgc", catalog = "")
public class RecruitmentEntity {
    private Integer id;
    @ApiModelProperty(value = "公司id")
    private Integer gsid;
    @ApiModelProperty(value = "招聘岗位")
    private String zpgw;
    @ApiModelProperty(value = "招聘部门")
    private String zpbm;
    @ApiModelProperty(value = "薪资待遇")
    private String xzdy;
    @ApiModelProperty(value = "招聘期限")
    private String zpqx;
    @ApiModelProperty(value = "详细说明")
    private String xxsm;
    @ApiModelProperty(value = "创建时间")
    private Date cjsj;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "company_id")
    public Integer getGsid() {
        return gsid;
    }

    public void setGsid(Integer gsid) {
        this.gsid = gsid;
    }

    @Basic
    @Column(name = "position")
    public String getZpgw() {
        return zpgw;
    }

    public void setZpgw(String zpgw) {
        this.zpgw = zpgw;
    }

    @Basic
    @Column(name = "department")
    public String getZpbm() {
        return zpbm;
    }

    public void setZpbm(String zpbm) {
        this.zpbm = zpbm;
    }

    @Basic
    @Column(name = "salary")
    public String getXzdy() {
        return xzdy;
    }

    public void setXzdy(String xzdy) {
        this.xzdy = xzdy;
    }

    @Basic
    @Column(name = "time_limit")
    public String getZpqx() {
        return zpqx;
    }

    public void setZpqx(String zpqx) {
        this.zpqx = zpqx;
    }

    @Basic
    @Column(name = "instruction")
    public String getXxsm() {
        return xxsm;
    }

    public void setXxsm(String xxsm) {
        this.xxsm = xxsm;
    }

    @Basic
    @Column(name = "create_time")
    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecruitmentEntity that = (RecruitmentEntity) o;
        return id == that.id &&
                gsid == that.gsid &&
                Objects.equals(zpgw, that.zpgw) &&
                Objects.equals(zpbm, that.zpbm) &&
                Objects.equals(xzdy, that.xzdy) &&
                Objects.equals(zpqx, that.zpqx) &&
                Objects.equals(xxsm, that.xxsm) &&
                Objects.equals(cjsj, that.cjsj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gsid, zpgw, zpbm, xzdy, zpqx, xxsm, cjsj);
    }
}
