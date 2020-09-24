package com.ces.intern.apitimecloud.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "role" , schema = "public")
public class RoleEntity {
    @Id
    @Column(name = "role_id")
    private Integer id;

    @Column(name = "role_name")
    private String name;
}
