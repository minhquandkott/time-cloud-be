package com.ces.intern.apitimecloud.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "project", schema ="public")
public class ProjectEntity implements Serializable {
    private static final long serialVersionUID = 5851584549644561471L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_generator")
    @SequenceGenerator(name = "project_generator", sequenceName = "project_id_seq", schema = "public", allocationSize = 1)
    @Column(name="project_id", nullable = false, unique = true)
    private Integer id;

    @Column(name="project_name", nullable = false)
    private String name;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "create_at", nullable = false)
    private Date creatAt;

    @Column(name = "create_by", nullable = false)
    private Integer createBy;

    @Column(name = "modify_at", nullable = false)
    private Date modifyAt;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "company_id")
    private CompanyEntity company;



    public ProjectEntity(){}

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

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Date getCreatAt() {
        return creatAt;
    }

    public void setCreatAt(Date creatAt) {
        this.creatAt = creatAt;
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

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

}
