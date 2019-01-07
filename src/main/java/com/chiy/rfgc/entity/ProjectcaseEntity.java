package com.chiy.rfgc.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "projectcase", schema = "rfgc", catalog = "")
public class ProjectcaseEntity {
    private Integer id;
    @ApiModelProperty(value = "公司id")
    private Integer gsid;
    @ApiModelProperty(value = "工程名称")
    private String gcmc;
    @ApiModelProperty(value = "图片")
    private String tp;
    @ApiModelProperty(value = "案例介绍")
    private String aljs;
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
    @Column(name = "project_name")
    public String getGcmc() {
        return gcmc;
    }

    public void setGcmc(String gcmc) {
        this.gcmc = gcmc;
    }

    @Basic
    @Column(name = "create_time")
    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    @Basic
    @Column(name = "introduction")
    public String getAljs() {
        return aljs;
    }

    public void setAljs(String aljs) {
        this.aljs = aljs;
    }

    @Basic
    @Column(name = "photo")
    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectcaseEntity)) return false;
        ProjectcaseEntity that = (ProjectcaseEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(gsid, that.gsid) &&
                Objects.equals(gcmc, that.gcmc) &&
                Objects.equals(tp, that.tp) &&
                Objects.equals(aljs, that.aljs) &&
                Objects.equals(cjsj, that.cjsj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gsid, gcmc, tp, aljs, cjsj);
    }
}
