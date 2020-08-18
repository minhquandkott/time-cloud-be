package com.ces.intern.apitimecloud.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(name="company")
public class CompanyEntity implements Serializable {

    private static final long serialVersionUID = -615940442147612868L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="companyId", nullable = false)
    private Integer id;

    @Column(name="companyName", nullable = false)
    private String name;

    @Column(name="avatar")
    private String avatar;

    @Column(name="description")
    private String description;

    @Column(name="companyLogo")
    private String logo;

    @Column(name="createAt", nullable = false)
    private Date createAt;

    @Column(name="creatBy", nullable = false)
    private Integer createBy;

    @Column(name="modifyAt", nullable = false)
    private Date modifyAt;

    @OneToMany(mappedBy = "company",
                fetch = FetchType.LAZY,
                cascade = {CascadeType.DETACH, CascadeType.MERGE,
                            CascadeType.PERSIST, CascadeType.REFRESH})
    private List<ProjectEntity> projects;

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
}
