package com.ces.intern.apitimecloud.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;



@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "user", schema = "public")
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 7559503200327808496L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name = "user_generator", sequenceName = "user_id_seq", allocationSize = 1, schema = "public")
    @Column(name = "user_id", unique = true, nullable = false)
    private Integer id;


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
