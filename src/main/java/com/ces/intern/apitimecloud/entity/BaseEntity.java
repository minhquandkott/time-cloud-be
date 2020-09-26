package com.ces.intern.apitimecloud.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    private Integer id;

    @Column(name = "create_at", nullable = false)
    private Date createAt;

    @Column(name = "created_by", nullable = false)
    private Integer createdBy;

    @Column(name = "modify_at", nullable = false)
    private Date modifyAt;

    @Column(name = "modified_by", nullable = false)
    private Integer modifiedBy;

    public void setBasicInfo(Date createAt, Integer createdBy, Date modifyAt, Integer modifiedBy){
        this.createAt = createAt;
        this.createdBy = createdBy;
        this.modifyAt = modifyAt;
        this.modifiedBy = modifiedBy;
    }

}
