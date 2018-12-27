package com.chiy.rfgc.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "equipment", schema = "rfgc", catalog = "")
public class EquipmentEntity {
    private Integer id;
    @ApiModelProperty(value = "公司id")
    private Integer gsid;
    @ApiModelProperty(value = "设备类型")
    private Integer sblx;
    @ApiModelProperty(value = "产品名称")
    private String cpmc;
    @ApiModelProperty(value = "产品特点")
    private String cptd;
    @ApiModelProperty(value = "产品特点图片")
    private String cptdtp;
    @ApiModelProperty(value = "适用范围")
    private String syfw;
    @ApiModelProperty(value = "适用范围图片")
    private String syfwtp;
    @ApiModelProperty(value = "主要技术参数")
    private String zyjscs;
    @ApiModelProperty(value = "主要技术参数图片")
    private String zyjscstp;
    @ApiModelProperty(value = "单位")
    private String dw;
    @ApiModelProperty(value = "单位图片")
    private String dwtp;
    @ApiModelProperty(value = "预留备用穿墙管间距尺寸表")
    private String ylb;
    @ApiModelProperty(value = "预留备用穿墙管间距尺寸表图片")
    private String ylbtp;
    @ApiModelProperty(value = "说明")
    private String sm;
    @ApiModelProperty(value = "说明图片")
    private String smtp;
    @ApiModelProperty(value = "安装与使用")
    private String azysy;
    @ApiModelProperty(value = "安装与使用图片")
    private String azysytp;
    @ApiModelProperty(value = "性能")
    private String xn;
    @ApiModelProperty(value = "性能图片")
    private String xntp;
    @ApiModelProperty(value = "分类")
    private String fl;
    @ApiModelProperty(value = "分类图片")
    private String fltp;
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
    @Column(name = "equipment_type")
    public Integer getSblx() {
        return sblx;
    }

    public void setSblx(Integer sblx) {
        this.sblx = sblx;
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
    @Column(name = "product_character")
    public String getCptd() {
        return cptd;
    }

    public void setCptd(String cptd) {
        this.cptd = cptd;
    }

    @Basic
    @Column(name = "product_character_photo")
    public String getCptdtp() {
        return cptdtp;
    }

    public void setCptdtp(String cptdtp) {
        this.cptdtp = cptdtp;
    }

    @Basic
    @Column(name = "application_limit")
    public String getSyfw() {
        return syfw;
    }

    public void setSyfw(String syfw) {
        this.syfw = syfw;
    }

    @Basic
    @Column(name = "application_limit_photo")
    public String getSyfwtp() {
        return syfwtp;
    }

    public void setSyfwtp(String syfwtp) {
        this.syfwtp = syfwtp;
    }

    @Basic
    @Column(name = "technical_parameter")
    public String getZyjscs() {
        return zyjscs;
    }

    public void setZyjscs(String zyjscs) {
        this.zyjscs = zyjscs;
    }

    @Basic
    @Column(name = "technical_parameter_photo")
    public String getZyjscstp() {
        return zyjscstp;
    }

    public void setZyjscstp(String zyjscstp) {
        this.zyjscstp = zyjscstp;
    }

    @Basic
    @Column(name = "unit")
    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    @Basic
    @Column(name = "unit_photo")
    public String getDwtp() {
        return dwtp;
    }

    public void setDwtp(String dwtp) {
        this.dwtp = dwtp;
    }

    @Basic
    @Column(name = "reserved_table")
    public String getYlb() {
        return ylb;
    }

    public void setYlb(String ylb) {
        this.ylb = ylb;
    }

    @Basic
    @Column(name = "reserved_table_photo")
    public String getYlbtp() {
        return ylbtp;
    }

    public void setYlbtp(String ylbtp) {
        this.ylbtp = ylbtp;
    }

    @Basic
    @Column(name = "instruction")
    public String getSm() {
        return sm;
    }

    public void setSm(String sm) {
        this.sm = sm;
    }

    @Basic
    @Column(name = "instruction_photo")
    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    @Basic
    @Column(name = "installation_use")
    public String getAzysy() {
        return azysy;
    }

    public void setAzysy(String azysy) {
        this.azysy = azysy;
    }

    @Basic
    @Column(name = "installation_use_photo")
    public String getAzysytp() {
        return azysytp;
    }

    public void setAzysytp(String azysytp) {
        this.azysytp = azysytp;
    }

    @Basic
    @Column(name = "performance")
    public String getXn() {
        return xn;
    }

    public void setXn(String xn) {
        this.xn = xn;
    }

    @Basic
    @Column(name = "performance_photo")
    public String getXntp() {
        return xntp;
    }

    public void setXntp(String xntp) {
        this.xntp = xntp;
    }

    @Basic
    @Column(name = "classification")
    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    @Basic
    @Column(name = "classification_photo")
    public String getFltp() {
        return fltp;
    }

    public void setFltp(String fltp) {
        this.fltp = fltp;
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
        EquipmentEntity that = (EquipmentEntity) o;
        return id == that.id &&
                gsid == that.gsid &&
                Objects.equals(sblx, that.sblx) &&
                Objects.equals(cpmc, that.cpmc) &&
                Objects.equals(cptd, that.cptd) &&
                Objects.equals(cptdtp, that.cptdtp) &&
                Objects.equals(syfw, that.syfw) &&
                Objects.equals(syfwtp, that.syfwtp) &&
                Objects.equals(zyjscs, that.zyjscs) &&
                Objects.equals(zyjscstp, that.zyjscstp) &&
                Objects.equals(dw, that.dw) &&
                Objects.equals(dwtp, that.dwtp) &&
                Objects.equals(ylb, that.ylb) &&
                Objects.equals(ylbtp, that.ylbtp) &&
                Objects.equals(sm, that.sm) &&
                Objects.equals(smtp, that.smtp) &&
                Objects.equals(azysy, that.azysy) &&
                Objects.equals(azysytp, that.azysytp) &&
                Objects.equals(xn, that.xn) &&
                Objects.equals(xntp, that.xntp) &&
                Objects.equals(fl, that.fl) &&
                Objects.equals(fltp, that.fltp) &&
                Objects.equals(cjsj, that.cjsj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gsid, sblx, cpmc, cptd, cptdtp, syfw, syfwtp, zyjscs, zyjscstp, dw, dwtp, ylb, ylbtp, sm, smtp, azysy, azysytp, xn, xntp, fl, fltp, cjsj);
    }
}
