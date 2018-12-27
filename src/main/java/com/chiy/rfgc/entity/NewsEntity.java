package com.chiy.rfgc.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "news", schema = "rfgc", catalog = "")
public class NewsEntity {
    private Integer id;
    @ApiModelProperty(value = "公司id")
    private Integer gsid;
    @ApiModelProperty(value = "新闻类型")
    private Integer xwlx;
    @ApiModelProperty(value = "新闻标题")
    private String xwbt;
    @ApiModelProperty(value = "新闻内容")
    private String xwnr;
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
    @Column(name = "news_type")
    public Integer getXwlx() {
        return xwlx;
    }

    public void setXwlx(Integer xwlx) {
        this.xwlx = xwlx;
    }

    @Basic
    @Column(name = "news_title")
    public String getXwbt() {
        return xwbt;
    }

    public void setXwbt(String xwbt) {
        this.xwbt = xwbt;
    }

    @Basic
    @Column(name = "news_content")
    public String getXwnr() {
        return xwnr;
    }

    public void setXwnr(String xwnr) {
        this.xwnr = xwnr;
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
        NewsEntity that = (NewsEntity) o;
        return id == that.id &&
                gsid == that.gsid &&
                Objects.equals(xwlx, that.xwlx) &&
                Objects.equals(xwbt, that.xwbt) &&
                Objects.equals(xwnr, that.xwnr) &&
                Objects.equals(cjsj, that.cjsj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gsid, xwlx, xwbt, xwnr, cjsj);
    }
}
