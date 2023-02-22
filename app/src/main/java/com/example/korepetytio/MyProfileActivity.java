package com.example.korepetytio;


import static com.example.korepetytio.LoginActivity.client;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;


public class MyProfileActivity extends AppCompatActivity {
    private Button changeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        TextView myTextView = findViewById(R.id.textView2);
        myTextView.setText("Nazwa użytkownika: " + client.getUsername() + "\n" +
                "Email użytkownika: " + client.getEmail()+ "\n" +
                "Hasło użytkownika: " + client.getPassword() + "\n" +
                "Rola użytkownika: " + client.getRole());
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