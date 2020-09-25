package com.ces.intern.apitimecloud.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Builder
public class EmbedEntity {

    @Column(name = "create_at", nullable = false)
    private Date createAt;

    @Column(name = "created_by", nullable = false)
    private Integer createdBy;

    @Column(name = "modify_at", nullable = false)
    private Date modifyAt;

    @Column(name = "modified_by", nullable = false)
    private Integer modifiedBy;
}
