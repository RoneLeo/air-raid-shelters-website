package com.chiy.rfgc.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "company", schema = "rfgc", catalog = "")
public class CompanyEntity {
    private Integer id;
    @ApiModelProperty(value = "公司名称")
    private String gsmc;

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
    @Column(name = "company_name")
    public String getGsmc() {
        return gsmc;
    }

    public void setGsmc(String gsmc) {
        this.gsmc = gsmc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyEntity that = (CompanyEntity) o;
        return id == that.id &&
                Objects.equals(gsmc, that.gsmc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gsmc);
    }
}
