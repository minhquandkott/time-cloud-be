package com.ces.intern.apitimecloud.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "discussion", schema ="public")
@AttributeOverride(name = "id", column =  @Column(name="discussion_id"))
@SequenceGenerator(name = "generator", sequenceName = "discussion_id_seq", schema = "public", allocationSize = 1)
public class DiscussionEntity extends BaseEntity{


    @Column(name = "content")
    private String content;

    @Column(name = "type")
    private Integer type;

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
    @JoinColumn(name = "project_id")
    private ProjectEntity project;
}
