package com.example.websocket.entity;

import lombok.Getter;

@Getter
public class User {

    private String id;
    private String password;
    private String dtype;

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }
}
