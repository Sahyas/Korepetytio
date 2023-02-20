package com.example.korepetytio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class RoleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);
    }
    public void teacherRegister(View v){
        Intent i = new Intent(this, Teacher_RegisterActivity.class);
        startActivity(i);
    }
}