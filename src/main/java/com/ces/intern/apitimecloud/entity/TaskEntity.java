package com.ces.intern.apitimecloud.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@Entity(name = "task")
public class TaskEntity implements Serializable {
    private static final long serialVersionUID = 145489298398401000L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "taskId")
    private Integer id;

    @Column(name = "taskName")
    private String name;

    @Column(name = "createAt")
    private Date createAt;

    @Column(name = "modifyAt")
    private Date modifyAt;

    @ManyToOne(fetch = FetchType.LAZY,
                cascade = {CascadeType.DETACH, CascadeType.MERGE,
                            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "projectId")
    private ProjectEntity project;

    @OneToMany(fetch = FetchType.LAZY,
                mappedBy = "task",
                cascade = CascadeType.ALL)
    private List<TimeEntity> times;

    public TaskEntity(){}

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

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public List<TimeEntity> getTimes() {
        return times;
    }

    public void setTimes(List<TimeEntity> times) {
        this.times = times;
    }
}
