package com.example.korepetytio.client;

public class Student extends Client{
    private Dysfunctions dysfunctions;

    public Student(ClientUsername username, ClientPassword clientPassword, CLientEmail cLientEmail,
                   ClientRole role, Dysfunctions dysfunctions) {
        super(username, clientPassword, cLientEmail, role);
        this.dysfunctions = dysfunctions;
    }
}
