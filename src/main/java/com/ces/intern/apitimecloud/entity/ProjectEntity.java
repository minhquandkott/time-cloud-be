package com.ces.intern.apitimecloud.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
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


    @ManyToOne(cascade = {
            CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH
    })
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    @Embedded
    private EmbedEntity embedEntity;
}
