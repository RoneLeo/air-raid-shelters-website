package com.chiy.rfgc.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "netservice", schema = "rfgc", catalog = "")
public class NetserviceEntity {
    private Integer id;
    @ApiModelProperty(value = "公司id")
    private Integer gsid;
    @ApiModelProperty(value = "产品名称")
    private String cpmc;
    @ApiModelProperty(value = "订购数量")
    private Integer dgsl;
    @ApiModelProperty(value = "公司名称")
    private String gsmc;
    @ApiModelProperty(value = "您的姓名")
    private String ndxm;
    @ApiModelProperty(value = "联系电话")
    private String lxdh;
    @ApiModelProperty(value = "电子信箱")
    private String dzyx;
    @ApiModelProperty(value = "咨询内容")
    private String zxnr;
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
    @Column(name = "product_name")
    public String getCpmc() {
        return cpmc;
    }

    public void setCpmc(String cpmc) {
        this.cpmc = cpmc;
    }

    @Basic
    @Column(name = "number")
    public Integer getDgsl() {
        return dgsl;
    }

    public void setDgsl(Integer dgsl) {
        this.dgsl = dgsl;
    }

    @Basic
    @Column(name = "company_name")
    public String getGsmc() {
        return gsmc;
    }

    public void setGsmc(String gsmc) {
        this.gsmc = gsmc;
    }

    @Basic
    @Column(name = "name")
    public String getNdxm() {
        return ndxm;
    }

    public void setNdxm(String ndxm) {
        this.ndxm = ndxm;
    }

    @Basic
    @Column(name = "phone")
    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    @Basic
    @Column(name = "email")
    public String getDzyx() {
        return dzyx;
    }

    public void setDzyx(String dzyx) {
        this.dzyx = dzyx;
    }

    @Basic
    @Column(name = "content")
    public String getZxnr() {
        return zxnr;
    }

    public void setZxnr(String zxnr) {
        this.zxnr = zxnr;
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
        NetserviceEntity that = (NetserviceEntity) o;
        return id == that.id &&
                gsid == that.gsid &&
                Objects.equals(cpmc, that.cpmc) &&
                Objects.equals(dgsl, that.dgsl) &&
                Objects.equals(gsmc, that.gsmc) &&
                Objects.equals(ndxm, that.ndxm) &&
                Objects.equals(lxdh, that.lxdh) &&
                Objects.equals(dzyx, that.dzyx) &&
                Objects.equals(zxnr, that.zxnr) &&
                Objects.equals(cjsj, that.cjsj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gsid, cpmc, dgsl, gsmc, ndxm, lxdh, dzyx, zxnr, cjsj);
    }
}
