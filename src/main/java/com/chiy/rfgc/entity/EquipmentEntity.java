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
        if (!(o instanceof EquipmentEntity)) return false;
        EquipmentEntity that = (EquipmentEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(gsid, that.gsid) &&
                Objects.equals(sblx, that.sblx) &&
                Objects.equals(cpmc, that.cpmc) &&
                Objects.equals(cjsj, that.cjsj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gsid, sblx, cpmc, cjsj);
    }
}
