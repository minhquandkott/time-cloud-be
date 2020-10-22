package com.ces.intern.apitimecloud.entity;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="project_user",schema = "public")
public class ProjectUserEntity implements Serializable {

    private static final long serialVersionUID = -6471238985708210627L;
    @EmbeddedId
    private EmbedId embedId = new EmbedId();

    @ManyToOne(cascade = {
            CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH
    })
    @MapsId("projectId")
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    @ManyToOne(cascade = {
            CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH
    })
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "is_doing", nullable = false)
    private Boolean isDoing;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmbedId implements Serializable {

        private static final long serialVersionUID = -5330810955777359L;
        @Column(name = "project_id")
        private Integer projectId;

        @Column(name = "user_id")
        private Integer userId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EmbedId embedId = (EmbedId) o;
            return Objects.equals(projectId, embedId.projectId) &&
                    Objects.equals(userId, embedId.userId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(projectId, userId);
        }
    }
}
