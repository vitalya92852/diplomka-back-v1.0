package com.example.demo1222.Exception;

import lombok.Data;

import java.util.Date;

@Data
public class AppErrorException {
    private int status;
    private String message;
    private Date timestamp;

    public AppErrorException(int status, String message){
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }

}
