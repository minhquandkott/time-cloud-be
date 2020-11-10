package com.ces.intern.apitimecloud.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "comment", schema ="public")
@AttributeOverride(name = "id", column =  @Column(name="comment_id"))
@SequenceGenerator(name = "generator", sequenceName = "comment_id_seq", schema = "public", allocationSize = 1)
public class CommentEntity extends BaseEntity{


    @Column(name = "content")
    private String content;

    @ManyToOne(cascade = {
            CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH
    })
    @JoinColumn(name = "discussion_id")
    private DiscussionEntity discussion;

    @ManyToOne(cascade = {
            CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH
    })
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
