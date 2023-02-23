package com.example.korepetytio.client;

import java.util.List;

public class Student extends Client{
    private Dysfunctions dysfunctions;
    private List<Teacher> myTeachers;

    public Student( String username, String password, String email, ClientRole role, Double grade,
                   Dysfunctions dysfunctions) {
        super(username, password, email, role, grade);
        this.dysfunctions = dysfunctions;
    }

    public Dysfunctions getDysfunctions() {
        return dysfunctions;
    }

    public List<Teacher> getMyTeachers() {
        return myTeachers;
    }
}
