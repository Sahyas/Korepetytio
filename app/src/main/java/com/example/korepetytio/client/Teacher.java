package com.example.korepetytio.client;

public class Teacher extends Client{
    private Dysfunctions dysfunctions;
    private LessonPrice lessonPrice;
    private Subject subject;

    public Teacher(String id, String username, String password, String email, ClientRole role,
                   Dysfunctions dysfunctions, LessonPrice lessonPrice, Subject subject) {
        super(id, username, password, email, role);
        this.dysfunctions = dysfunctions;
        this.lessonPrice = lessonPrice;
        this.subject = subject;
    }
}
