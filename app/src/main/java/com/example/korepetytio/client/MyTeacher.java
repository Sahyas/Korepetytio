package com.example.korepetytio.client;

public class MyTeacher {
    String username;
    String date;

    public MyTeacher(String username, String date) {
        this.username = username;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public String getDate() {
        return date;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
