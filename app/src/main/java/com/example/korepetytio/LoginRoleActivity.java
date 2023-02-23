package com.example.korepetytio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginRoleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_role);
    }

    public void teacherLogin(View v) {
        startActivity(new Intent(this, TeacherLoginActivity.class));
    }

    public void studentLogin(View v) {
        startActivity(new Intent(this, StudentLoginActivity.class));
    }

}