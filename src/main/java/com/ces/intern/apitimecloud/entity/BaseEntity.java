package com.ces.intern.apitimecloud.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 4577088713053351885L;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
