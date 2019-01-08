package com.chiy.rfgc.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "equipmenttype", schema = "rfgc", catalog = "")
public class EquipmenttypeEntity {
    private Integer id;
    @ApiModelProperty(value = "设备类型")
    private String name;
    @ApiModelProperty(value = "公司id")
    private Integer gsid;

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
    @Column(name = "type")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "company_id")
    public Integer getGsid() {
        return gsid;
    }

    public void setGsid(Integer gsid) {
        this.gsid = gsid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EquipmenttypeEntity)) return false;
        EquipmenttypeEntity that = (EquipmenttypeEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(gsid, that.gsid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, gsid);
    }
}
