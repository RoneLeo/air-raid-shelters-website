package com.chiy.rfgc.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "equipmenttype", schema = "rfgc", catalog = "")
public class EquipmenttypeEntity {
    private Integer id;
    @ApiModelProperty(value = "设备类型")
    private String sblx;

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
    public String getSblx() {
        return sblx;
    }

    public void setSblx(String sblx) {
        this.sblx = sblx;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquipmenttypeEntity that = (EquipmenttypeEntity) o;
        return id == that.id &&
                Objects.equals(sblx, that.sblx);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sblx);
    }
}
