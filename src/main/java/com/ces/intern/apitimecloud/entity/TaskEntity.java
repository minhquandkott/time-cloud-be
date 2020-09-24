package com.ces.intern.apitimecloud.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "task", schema = "public")
public class TaskEntity implements Serializable {
    private static final long serialVersionUID = 145489298398401000L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_generator")
    @SequenceGenerator(name = "task_generator", sequenceName = "task_id_seq", allocationSize = 1, schema = "public")
    @Column(name = "task_id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "task_name", nullable = false)
    private String name;

    @ManyToOne(cascade = {
            CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH
    })
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    @Embedded
    private EmbedEntity embedEntity;

}
