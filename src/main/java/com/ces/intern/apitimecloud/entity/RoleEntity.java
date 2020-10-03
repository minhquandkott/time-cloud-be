package com.ces.intern.apitimecloud.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role" , schema = "public")
public class RoleEntity {
    @Id
    @Column(name = "role_id")
    private Integer id;

    @Column(name = "role_name")
    private String name;

    @Column(name = "role_color")
    private String color;


}
