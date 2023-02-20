package com.example.korepetytio.rent;

import com.example.korepetytio.client.Client;

import java.time.LocalDateTime;
import java.util.UUID;

public class Rent {
    private UUID id;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private Client student;
    private Client teacher;

    public Rent(LocalDateTime beginTime, LocalDateTime endTime, Client student, Client teacher) {
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.student = student;
        this.teacher = teacher;
    }
}
