package com.ces.intern.apitimecloud.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    ADMIN,
    QA,
    DESIGNER,
    DEVELOP,
    HR,
    CEO,
    PM;


    private Set<String> permissions;
    Role(RolePermission... permissions){
        this.permissions = Arrays.asList(permissions)
                .stream().map(permission -> permission.getPermission())
                .collect(Collectors.toSet());
    }


}
