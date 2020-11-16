package com.ces.intern.apitimecloud.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "interact", schema = "public")
public class InteractEntity {


    @EmbeddedId
    private EmbedId id = new EmbedId();

    @ManyToOne(cascade = {
            CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH
    })
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(cascade = {
            CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH
    })
    @MapsId("discussionId")
    @JoinColumn(name = "discussion_id")
    private DiscussionEntity discussion;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmbedId implements Serializable {
        private static final long serialVersionUID = -5535178767632113317L;
        @Column(name = "user_id")
        private Integer userId;

        @Column(name = "discussion_id")
        private Integer discussionId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EmbedId embedId = (EmbedId) o;
            return userId.equals(embedId.userId) &&
                    discussionId.equals(embedId.discussionId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, discussionId);
        }
    }
}
