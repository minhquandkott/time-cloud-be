package com.ces.intern.apitimecloud.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user", schema = "public")
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
@SequenceGenerator(name = "generator", sequenceName = "user_id_seq", allocationSize = 1, schema = "public")
public class UserEntity extends BaseEntity {

    @Column(name = "user_name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "gender")
    private boolean gender;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;


}
