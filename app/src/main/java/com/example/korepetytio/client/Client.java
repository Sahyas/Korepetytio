package com.example.korepetytio.client;

import java.util.UUID;

public class Client {
    private final String id;
    private String username;
    private String password;
    private String email;
    private ClientRole role;
    private boolean isEnable = true;

    public Client(String id, String username, String password, String email, ClientRole role) {
        this.id = String.valueOf(UUID.randomUUID());
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public String getId() {return id;}

    public String getUsername() {return username;}

    public void setName(String username) {this.username = username;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public ClientRole getRole() {return role;}

    public void setRole(ClientRole role) {this.role = role;}
}