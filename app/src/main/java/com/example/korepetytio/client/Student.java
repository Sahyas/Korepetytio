package com.example.korepetytio.client;

public class Student extends Client{
    private Dysfunctions dysfunctions;

    public Student( String username, String password, String email, ClientRole role,
                   Dysfunctions dysfunctions) {
        super(username, password, email, role);
        this.dysfunctions = dysfunctions;
    }
}
