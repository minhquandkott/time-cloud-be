package com.ces.intern.apitimecloud.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "company", schema = "public")
@AttributeOverride(name = "id", column =  @Column(name="company_id"))
@SequenceGenerator(name = "generator", sequenceName = "company_id_seq", schema = "public", allocationSize = 1)
public class CompanyEntity extends BaseEntity {

    @Column(name="company_name", nullable = false)
    private String name;

    @Column(name="avatar")
    private String avatar;

    @Column(name="description")
    private String description;

    @Column(name="company_logo")
    private String logo;

}
