package com.ces.intern.apitimecloud.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role{
    ADMIN(RolePermission.values()),
    //full
    // ngươi quản lý website


    QC(),
    //full_time, read_task, read_project, read_company

    DESIGNER,//read _task full_time full-user read_project read_company
    DEVELOPER,//read -task full-time full-user read_project read-company


    CEO,//full
    //Người tạo công ty


    PM//full-task full-time read-project full-user read_company
    ;























    private Set<String> permissions;
    Role(RolePermission... permissions){
        this.permissions = Arrays.asList(permissions)
                .stream().map(permission -> permission.getPermission())
                .collect(Collectors.toSet());
    }


}
