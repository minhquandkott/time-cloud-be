package com.ces.intern.apitimecloud.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "time", schema = "public")
public class TimeEntity implements Serializable {
    private static final long serialVersionUID = -8468078557836858453L;



    @EmbeddedId
    private Id id;

    @Column(name = "start_time", nullable = false)
    private Date startTime;

    @Column(name = "end_time", nullable = false)
    private Date endTime;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,CascadeType.MERGE,
                    CascadeType.PERSIST,CascadeType.REFRESH
            })
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,CascadeType.MERGE,
                    CascadeType.PERSIST,CascadeType.REFRESH
            })
    @MapsId("taskId")
    @JoinColumn(name = "task_id")
    private TaskEntity task;

    public TimeEntity(){}



    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public TaskEntity getTask() {
        return task;
    }

    public void setTask(TaskEntity task) {
        this.task = task;
    }



    @Embeddable
    public static class Id implements Serializable{
        private static final long serialVersionUID = 2634409230448550149L;


        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "time_generator")
        @SequenceGenerator(name = "time_generator", sequenceName = "time_id_seq", schema = "public", allocationSize = 1)
        @Column(name = "time_id", unique = true, nullable = false)
        private Integer id;

        @Column(name = "user_id")
        private Integer userId;

        @Column(name = "task_id")
        private Integer taskId;

        public Id(){}

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getTaskId() {
            return taskId;
        }

        public void setTaskId(Integer taskId) {
            this.taskId = taskId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Id id1 = (Id) o;
            return id.equals(id1.id) &&
                    userId.equals(id1.userId) &&
                    taskId.equals(id1.taskId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, userId, taskId);
        }
    }
}
