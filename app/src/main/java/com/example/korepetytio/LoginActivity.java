package com.example.korepetytio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void backtoMain(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void check_login(View v) {
        email = (EditText) findViewById(R.id.editTextTextPersonName2);
        String tekst = email.getText().toString();
        password = (EditText) findViewById(R.id.editTextTextPersonName3);
        String tekst2 = email.getText().toString();
        if (!tekst.isEmpty() && !tekst2.isEmpty()) {
            Intent i = new Intent(this, AppActivity.class);
            startActivity(i);
        }
    }
}