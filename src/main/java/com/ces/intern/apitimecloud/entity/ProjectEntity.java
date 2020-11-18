package com.ces.intern.apitimecloud.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "project", schema ="public")
@AttributeOverride(name = "id", column =  @Column(name="project_id"))
@SequenceGenerator(name = "generator", sequenceName = "project_id_seq", schema = "public", allocationSize = 1)
public class ProjectEntity extends BaseEntity {

    @Column(name="project_name", nullable = false)
    private String name;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "project_color")
    private String color;

    @Column(name = "done")
    private Boolean done;

    @ManyToOne(cascade = {
            CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH
    })
    @JoinColumn(name = "company_id")
    private CompanyEntity company;



}
