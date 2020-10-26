package com.ces.intern.apitimecloud.util;

public enum ExceptionMessage {



    NOT_FOUND_RECORD("Not Found Record"),
    RECORD_ALREADY_EXISTS("Record Already Exists"),
    MISSING_REQUIRE_FIELD("Missing Require Field !"),
    INTERNAL_SERVER_ERROR("Internal Server Error"),
    USERNAME_PASSWORD_INVALIDATE("Username or Password Incorrect"),
    EMAIL_ALREADY_EXIST("Email Already Exist"),
    FIELD_NOT_CORRECT("Format Not Correct");


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
