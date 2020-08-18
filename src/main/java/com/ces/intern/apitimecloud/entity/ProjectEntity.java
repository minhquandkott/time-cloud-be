package com.ces.intern.apitimecloud.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "project")
public class ProjectEntity implements Serializable {

    private static final long serialVersionUID = 5851584549644561471L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="projectId")
    private Integer id;

    @Column(name="projectName")
    private String name;

    @Column(name = "clientName")
    private String clientName;

    @Column(name = "createAt")
    private Date creatAt;

    @Column(name = "createBy")
    private Integer createBy;

    @Column(name = "modifyAt")
    private Date modifyAt;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                        CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "companyId")
    private CompanyEntity company;


    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL},
            mappedBy = "project")
    private List<TaskEntity> tasks;


    @ManyToMany(fetch = FetchType.LAZY,
                cascade = {CascadeType.DETACH,CascadeType.MERGE,
                            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "project_user",
            joinColumns = @JoinColumn(name = "projectId"),
            inverseJoinColumns = @JoinColumn(name = "userId")
    )
    private List<UserEntity> users;

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

    public List<TaskEntity> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskEntity> tasks) {
        this.tasks = tasks;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }
}
