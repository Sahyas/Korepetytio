package com.example.korepetytio.client;

import java.util.ArrayList;
import java.util.List;

public class Student extends Client {
    private Dysfunctions dysfunctions;
    public static List<MyTeacher> myTeachers = new ArrayList<>();

    public Student( String username, String password, String email, ClientRole role, Double grade,
                   Dysfunctions dysfunctions) {
        super(username, password, email, role, grade);
        this.dysfunctions = dysfunctions;
    }

    public Dysfunctions getDysfunctions() {
        return dysfunctions;
    }

    public List<MyTeacher> getMyTeachers() {
        return myTeachers;
    }

    public void addTeacher(MyTeacher teacher) {
        myTeachers.add(teacher);
    }
}
