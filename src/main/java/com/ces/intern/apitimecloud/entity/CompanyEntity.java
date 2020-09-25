package com.ces.intern.apitimecloud.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "company", schema = "public")
public class CompanyEntity implements Serializable {

    private static final long serialVersionUID = -615940442147612868L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_generator")
    @SequenceGenerator(name = "company_generator", sequenceName = "company_id_seq", schema = "public", allocationSize = 1)
    @Column(name="company_id",unique = true, nullable = false )
    private Integer id;

    @Column(name="company_name", nullable = false)
    private String name;

    @Column(name="avatar")
    private String avatar;

    @Column(name="description")
    private String description;

    @Column(name="company_logo")
    private String logo;

    @Embedded
    private EmbedEntity embedEntity;

}
