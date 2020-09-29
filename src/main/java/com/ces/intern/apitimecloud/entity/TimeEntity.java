package com.ces.intern.apitimecloud.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "time", schema = "public")
@AttributeOverride(name = "id", column = @Column(name = "time_id"))
@SequenceGenerator(name = "generator", sequenceName = "time_id_seq", schema = "public", allocationSize = 1)
public class TimeEntity extends BaseEntity  {

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = {
            CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH
    })
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(cascade = {
            CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH
    })
    @JoinColumn(name = "task_id")
    private TaskEntity task;

}
