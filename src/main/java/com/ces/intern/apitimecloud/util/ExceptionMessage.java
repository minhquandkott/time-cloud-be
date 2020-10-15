package com.ces.intern.apitimecloud.util;

public enum ExceptionMessage {



    NOT_FOUND_RECORD("Not Found Record"),
    RECORD_ALREADY_EXISTS("Record Already Exists"),
    MISSING_REQUIRE_FIELD("Missing Require Field !"),
    INTERNAL_SERVER_ERROR("Internal Server Error"),
    USERNAME_PASSWORD_INVALIDATE("Username or Password Incorrect"),
    USERNAME_PASSWORD_ALREADY_EXIST("Username or Password Already Exist");


    private String message;

    ExceptionMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
