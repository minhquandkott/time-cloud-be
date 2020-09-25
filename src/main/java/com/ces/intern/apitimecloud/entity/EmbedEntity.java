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
public class EmbedEntity {

    @Column(name = "create_at", nullable = false)
    private Date createAt;

    @Column(name = "create_by", nullable = false)
    private Integer createBy;

    @Column(name = "modify_at", nullable = false)
    private Date modifyAt;

    @Column(name = "modify_by", nullable = false)
    private Integer modifyBy;
}
