package com.example.korepetytio.client;

import java.util.UUID;

public class Client {
    private CLientId clientId;
    private ClientUsername username;
    private ClientPassword clientPassword;
    private CLientEmail cLientEmail;
    private ClientRole role;
    private boolean isEnable = true;

    public Client(ClientUsername username, ClientPassword clientPassword, CLientEmail cLientEmail, ClientRole role) {
        this.clientId = new CLientId(UUID.randomUUID());
        this.username = username;
        this.clientPassword = clientPassword;
        this.cLientEmail = cLientEmail;
        this.role = role;
    }
}
