package com.chiy.rfgc.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "contactus", schema = "rfgc", catalog = "")
public class ContactusEntity {
    private Integer id;
    @ApiModelProperty(value = "公司id")
    private Integer gsid;
    @ApiModelProperty(value = "公司名称")
    private String gsmc;
    @ApiModelProperty(value = "联系地址")
    private String lxdz;
    @ApiModelProperty(value = "联系电话")
    private String lxdh;
    @ApiModelProperty(value = "联系传真")
    private String lxcz;
    @ApiModelProperty(value = "公司网址")
    private String gswz;
    @ApiModelProperty(value = "联系邮编")
    private String lxyb;
    @ApiModelProperty(value = "联系邮箱")
    private String lxyx;
    @ApiModelProperty(value = "经纬度")
    private String jwd;
    @ApiModelProperty(value = "公司简介")
    private String gsjj;
    @ApiModelProperty(value = "备案信息")
    private String baxx;
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
    @Column(name = "company_name")
    public String getGsmc() {
        return gsmc;
    }

    public void setGsmc(String gsmc) {
        this.gsmc = gsmc;
    }

    @Basic
    @Column(name = "address")
    public String getLxdz() {
        return lxdz;
    }

    public void setLxdz(String lxdz) {
        this.lxdz = lxdz;
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
    @Column(name = "fax")
    public String getLxcz() {
        return lxcz;
    }

    public void setLxcz(String lxcz) {
        this.lxcz = lxcz;
    }

    @Basic
    @Column(name = "url")
    public String getGswz() {
        return gswz;
    }

    public void setGswz(String gswz) {
        this.gswz = gswz;
    }

    @Basic
    @Column(name = "postcode")
    public String getLxyb() {
        return lxyb;
    }

    public void setLxyb(String lxyb) {
        this.lxyb = lxyb;
    }

    @Basic
    @Column(name = "email")
    public String getLxyx() {
        return lxyx;
    }

    public void setLxyx(String lxyx) {
        this.lxyx = lxyx;
    }

    @Basic
    @Column(name = "latitude_longitude")
    public String getJwd() {
        return jwd;
    }

    public void setJwd(String jwd) {
        this.jwd = jwd;
    }

    @Basic
    @Column(name = "company_introduction")
    public String getGsjj() {
        return gsjj;
    }

    public void setGsjj(String gsjj) {
        this.gsjj = gsjj;
    }

    @Basic
    @Column(name = "record_information")
    public String getBaxx() {
        return baxx;
    }

    public void setBaxx(String baxx) {
        this.baxx = baxx;
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
        if (!(o instanceof ContactusEntity)) return false;
        ContactusEntity that = (ContactusEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(gsid, that.gsid) &&
                Objects.equals(gsmc, that.gsmc) &&
                Objects.equals(lxdz, that.lxdz) &&
                Objects.equals(lxdh, that.lxdh) &&
                Objects.equals(lxcz, that.lxcz) &&
                Objects.equals(gswz, that.gswz) &&
                Objects.equals(lxyb, that.lxyb) &&
                Objects.equals(lxyx, that.lxyx) &&
                Objects.equals(jwd, that.jwd) &&
                Objects.equals(gsjj, that.gsjj) &&
                Objects.equals(baxx, that.baxx) &&
                Objects.equals(cjsj, that.cjsj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gsid, gsmc, lxdz, lxdh, lxcz, gswz, lxyb, lxyx, jwd, gsjj, baxx, cjsj);
    }
}
