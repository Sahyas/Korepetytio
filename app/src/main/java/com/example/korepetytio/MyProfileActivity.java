package com.example.korepetytio;


import static com.example.korepetytio.StudentLoginActivity.currentOnlineStudent;
import static com.example.korepetytio.TeacherLoginActivity.currentOnlineTeacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MyProfileActivity extends AppCompatActivity {
    private Button changeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);


//        db.collection("students")
//                .whereEqualTo("email", client.getEmail())
//                .get()
        TextView myTextView = findViewById(R.id.textView2);
        if (currentOnlineTeacher != null) {
            myTextView.setText("Username: " + currentOnlineTeacher.getUsername() + "\n" +
                    "Email: " + currentOnlineTeacher.getEmail() + "\n" +
                    "Password: " + currentOnlineTeacher.getPassword() + "\n" +
                    "Role: " + currentOnlineTeacher.getRole() + "\n" +
                    "Dysfunction: " + currentOnlineTeacher.getDysfunctions() + "\n" +
                    "Subject: " + currentOnlineTeacher.getSubject() + "\n" +
                    "Price: " + currentOnlineTeacher.getLessonPrice());
        } else {
            myTextView.setText("Username: " + currentOnlineStudent.getUsername() + "\n" +
                    "Email: " + currentOnlineStudent.getEmail() + "\n" +
                    "Password: " + currentOnlineStudent.getPassword() + "\n" +
                    "Role: " + currentOnlineStudent.getRole() + "\n" +
                    "Dysfunction: " + currentOnlineStudent.getDysfunctions());
        }
        RatingBar ratingBarLayout = findViewById(R.id.rating_bar);
        RatingBar ratingBar = findViewById(R.id.rating_bar);

        if (currentOnlineTeacher != null) {
            double grade = currentOnlineTeacher.getGrade();
            ratingBarLayout.setVisibility(View.VISIBLE);
            ratingBar.setRating((float) grade); // Przykładowa wartość oceny
        } else {
            ratingBarLayout.setVisibility(View.GONE);
        }

    }

    public void backtoMenu(View v) {
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
    }

    public void changePassword(View v) {
        Intent i = new Intent(this, ChangePasswordActivity.class);
        startActivity(i);
    }
}
