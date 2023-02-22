package com.example.korepetytio.client;

public class Teacher extends Client{
    private String dysfunctions;
    private String lessonPrice;
    private String subject;

    public Teacher(String username, String password, String email, ClientRole role,Double grade,
                   String dysfunctions, String lessonPrice, String subject) {
        super(username, password, email, role, grade);
        this.dysfunctions = dysfunctions;
        this.lessonPrice = lessonPrice;
        this.subject = subject;
    }

    public String getDysfunctions() {
        return dysfunctions;
    }

    public String getLessonPrice() {
        return lessonPrice;
    }

    public String getSubject() {
        return subject;
    }
}
