package com.ces.intern.apitimecloud.http.response;

import java.util.Date;

public class ErrorResponse {
    private String error;
    private String message;
    private Date timestamp;
    private int status;
    private String path;

    public ErrorResponse(){}

    public ErrorResponse(String error, String message, Date timestamp, int status, String path) {
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
        this.status = status;
        this.path = path;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
