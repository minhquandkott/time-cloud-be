package com.ces.intern.apitimecloud.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "company", schema = "public")
public class CompanyEntity implements Serializable {

    private static final long serialVersionUID = -615940442147612868L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_generator")
    @SequenceGenerator(name = "company_generator", sequenceName = "company_id_seq", schema = "public", allocationSize = 1)
    @Column(name="company_id",unique = true, nullable = false )
    private Integer id;

    @Column(name="company_name", nullable = false)
    private String name;

    @Column(name="avatar")
    private String avatar;

    @Column(name="description")
    private String description;

    @Column(name="company_logo")
    private String logo;

    @Column(name="create_at", nullable = false)
    private Date createAt;

    @Column(name="create_by", nullable = false)
    private Integer createBy;

    @Column(name="modify_at", nullable = false)
    private Date modifyAt;


    public CompanyEntity(){}

    public String getAvatar() {
        return avatar;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Date getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }

    @Override
    public String toString() {
        return "CompanyEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", description='" + description + '\'' +
                ", logo='" + logo + '\'' +
                ", createAt=" + createAt +
                ", createBy=" + createBy +
                ", modifyAt=" + modifyAt +
                '}';
    }
}
