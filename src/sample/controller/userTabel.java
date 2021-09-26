package sample.controller;

import java.sql.Timestamp;

public class userTabel {
    String username,password;
    int phone;
    Timestamp create_time;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return password;
    }

    public void setUserpassword(String userpassword) {
        this.password = userpassword;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public userTabel(String username, String password, int phone, Timestamp create_time) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.create_time = create_time;
    }
}
