package com.example.korepetytio;


import static com.example.korepetytio.LoginActivity.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.korepetytio.client.ClientRole;


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
        if(client.getRole()== ClientRole.TEACHER){
        myTextView.setText("Nazwa użytkownika: " + client.getUsername() + "\n" +
                "Email użytkownika: " + client.getEmail()+ "\n" +
                "Hasło użytkownika: " + client.getPassword() + "\n" +
                "Rola użytkownika: " + client.getRole()+ "\n" +
                "Dysfunkcja użytkownika: " + client.getDysfunctions()+ "\n" +
                "Przedmiot użytkownika: " + client.getSubject()+ "\n" +
                "Cena użytkownika: " + client.getLessonPrice());
        }
        else {
            myTextView.setText("Nazwa użytkownika: " + client.getUsername() + "\n" +
                    "Email użytkownika: " + client.getEmail()+ "\n" +
                    "Hasło użytkownika: " + client.getPassword() + "\n" +
                    "Rola użytkownika: " + client.getRole()+ "\n" +
                    "Dysfunkcja użytkownika: " + client.getDysfunctions());
        }
        RatingBar ratingBar = findViewById(R.id.rating_bar);
        double grade = client.getGrade();
        ratingBar.setRating((float) grade);

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
