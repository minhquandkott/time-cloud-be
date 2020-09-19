package com.ces.intern.apitimecloud.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role{
    ADMIN("ADMIN"),
    //full
    // ngươi quản lý website


    QC("QC"),
    //full_time, read_task, read_project, read_company

    DESIGNER("DESIGNER"),//read _task full_time full-user read_project read_company
    DEVELOPER("DEVELOPER"),//read -task full-time full-user read_project read-company


    CEO("CEO"),//full
    //Người tạo công ty


    PM("PM"),//full-task full-time read-project full-user read_company

    MEMBER("MEMBER")
    ;

    private String role;

    Role(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }
    //    private Set<String> permissions;
//    Role(RolePermission... permissions){
//        this.permissions = Arrays.asList(permissions)
//                .stream().map(permission -> permission.getPermission())
//                .collect(Collectors.toSet());
//    }


}
