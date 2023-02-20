package com.example.korepetytio.client;

public class Teacher extends Client{
    private Dysfunctions dysfunctions;
    private LessonPrice lessonPrice;
    private Subject subject;

    public Teacher(ClientUsername username, ClientPassword clientPassword, CLientEmail cLientEmail, ClientRole role,
                   Dysfunctions dysfunctions, LessonPrice lessonPrice, Subject subject) {
        super(username, clientPassword, cLientEmail, role);
        this.dysfunctions = dysfunctions;
        this.lessonPrice = lessonPrice;
        this.subject = subject;
    }
}
