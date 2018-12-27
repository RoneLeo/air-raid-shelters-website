package com.chiy.rfgc.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "file", schema = "rfgc", catalog = "")
public class FileEntity {
    private Integer id;
    @ApiModelProperty(value = "公司id")
    private Integer gsid;
    @ApiModelProperty(value = "文件类型")
    private Integer wjlx;
    @ApiModelProperty(value = "文件名称")
    private String wjmc;
    @ApiModelProperty(value = "文件路径")
    private String wjlj;
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
    @Column(name = "file_type")
    public Integer getWjlx() {
        return wjlx;
    }

    public void setWjlx(Integer wjlx) {
        this.wjlx = wjlx;
    }

    @Basic
    @Column(name = "file_name")
    public String getWjmc() {
        return wjmc;
    }

    public void setWjmc(String wjmc) {
        this.wjmc = wjmc;
    }

    @Basic
    @Column(name = "file_url")
    public String getWjlj() {
        return wjlj;
    }

    public void setWjlj(String wjlj) {
        this.wjlj = wjlj;
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
        FileEntity that = (FileEntity) o;
        return id == that.id &&
                gsid == that.gsid &&
                Objects.equals(wjlx, that.wjlx) &&
                Objects.equals(wjmc, that.wjmc) &&
                Objects.equals(wjlj, that.wjlj) &&
                Objects.equals(cjsj, that.cjsj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gsid, wjlx, wjmc, wjlj, cjsj);
    }
}
