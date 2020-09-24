package com.ces.intern.apitimecloud.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
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
