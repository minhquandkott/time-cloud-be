package com.ces.intern.apitimecloud.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class EmbedEntity {
    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "create_by")
    private Integer createBy;

    @Column(name = "modify_at")
    private Date modifyAt;

    @Column(name = "modify_by")
    private Integer modifyBy;
}
