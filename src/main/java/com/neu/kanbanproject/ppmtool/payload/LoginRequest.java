package com.neu.kanbanproject.ppmtool.payload;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "username canot be blank!")
    private String username;
    @NotBlank(message = "pswd not be blnk")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
