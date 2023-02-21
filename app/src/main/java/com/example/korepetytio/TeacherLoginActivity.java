package com.example.korepetytio;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class TeacherLoginActivity extends AppCompatActivity {

    private Button buttonT2;
    private EditText editTextTextPersonNameT2;
    private EditText editTextTextPersonNameT1;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);

        buttonT2 = findViewById(R.id.buttonT2);
        editTextTextPersonNameT2 = findViewById(R.id.editTextTextPersonNameT2);
        editTextTextPersonNameT1 = findViewById(R.id.editTextTextPersonNameT1);

        loadingBar = new ProgressDialog(this);

        buttonT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_login();
            }
        });
    }

    public void backtoMain(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void check_login() {
        String email = editTextTextPersonNameT2.getText().toString();
        String password = editTextTextPersonNameT1.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please write your email...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Login");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            login(email, password);
        }

    }
    private void login(String email, String password) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("teachers")
                .whereEqualTo("email", email)
                .whereEqualTo("password", password)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            Intent intent = new Intent(TeacherLoginActivity.this, MenuActivity.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(TeacherLoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }
}