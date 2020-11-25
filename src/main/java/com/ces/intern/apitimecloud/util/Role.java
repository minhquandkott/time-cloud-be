package com.ces.intern.apitimecloud.util;

import com.ces.intern.apitimecloud.entity.RoleEntity;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role{
    ADMIN(new RoleEntity(1, "ADMIN", "2ECC71")),
    DEV(new RoleEntity(4, "DEV", "9B59B6")),
    DESIGNER(new RoleEntity(5, "DESIGNER", "1ABC9C")),
    TESTER(new RoleEntity(6, "TESTER", "E67E22")),
    HR(new RoleEntity(7, "HR", "5962B6")),
    SEO(new RoleEntity(8, "SEO", "361D2E")),
    MEMBER(new RoleEntity(9, "MEMBER", "86A397"));

    private RoleEntity roleEntity;

    Role(RoleEntity roleEntity){
        this.roleEntity = roleEntity;
    }


    public RoleEntity getRoleEntity() {
        return roleEntity;
    }
}
