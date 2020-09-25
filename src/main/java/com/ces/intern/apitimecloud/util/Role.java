package com.ces.intern.apitimecloud.util;

import com.ces.intern.apitimecloud.entity.RoleEntity;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role{
    ADMIN(new RoleEntity(1, "ADMIN")),
    CEO(new RoleEntity(2, "CEO")),
    PM(new RoleEntity(3, "PM")),
    DEV(new RoleEntity(4, "DEV")),
    DESIGNER(new RoleEntity(5, "DESIGNER")),
    TESTER(new RoleEntity(6, "TESTER")),
    HR(new RoleEntity(7, "HR")),
    SEO(new RoleEntity(8, "SEO")),
    MEMBER(new RoleEntity(9, "MEMBER"));

    private RoleEntity roleEntity;

    Role(RoleEntity roleEntity){
        this.roleEntity = roleEntity;
    }


    public RoleEntity getRoleEntity() {
        return roleEntity;
    }
}
