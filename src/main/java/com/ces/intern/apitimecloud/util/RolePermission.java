package com.ces.intern.apitimecloud.util;

public enum RolePermission {
    COMPANY_READ("company_read"),
    COMPANY_WRITE("company_write"),

    TIME_READ("time_read"),
    TIME_WRITE("time_write"),

    USER_READ("user_read"),
    USER_WRITE("user_write"),

    TASK_READ("task_read"),
    TASK_WRITE("task_write"),

    PROJECT_READ("project_read"),
    PROJECT_WRITE("project_write");



    private String permission;
    RolePermission(String permission){
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
