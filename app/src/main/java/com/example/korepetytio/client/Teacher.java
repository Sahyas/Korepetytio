package com.example.korepetytio.client;

public class Teacher extends Client{
    private Dysfunctions dysfunctions;
    private LessonPrice lessonPrice;
    private Subject subject;

    public Teacher(String username, String password, String email, ClientRole role,Double grade,
                   Dysfunctions dysfunctions, LessonPrice lessonPrice, Subject subject) {
        super(username, password, email, role, grade);
        this.dysfunctions = dysfunctions;
        this.lessonPrice = lessonPrice;
        this.subject = subject;
    }
}
