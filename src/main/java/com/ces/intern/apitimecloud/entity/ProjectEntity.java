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
public class ProjectEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 5851584549644561471L;

    @Column(name="project_name", nullable = false)
    private String name;

    @Column(name = "client_name")
    private String clientName;


    @ManyToOne(cascade = {
            CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH
    })
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

}
