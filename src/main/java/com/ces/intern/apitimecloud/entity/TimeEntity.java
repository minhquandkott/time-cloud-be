package com.ces.intern.apitimecloud.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@Table(name = "time", schema = "public")
public class TimeEntity implements Serializable {
    private static final long serialVersionUID = -8468078557836858453L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "time_generator")
    @SequenceGenerator(name = "time_generator", sequenceName = "time_id_seq", schema = "public", allocationSize = 1)
    @Column(name = "time_id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
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

    @Embedded
    private EmbedEntity embedEntity;

}
