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
@Table(name = "task", schema = "public")
@AttributeOverride(name = "id", column = @Column(name = "task_id"))
@SequenceGenerator(name = "generator", sequenceName = "task_id_seq", allocationSize = 1, schema = "public")
public class TaskEntity extends BaseEntity {

    @Column(name = "task_name", nullable = false)
    private String name;

    @ManyToOne(cascade = {
            CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH
    })
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

}
