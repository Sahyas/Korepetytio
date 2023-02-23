package com.example.korepetytio;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.korepetytio.client.Dysfunctions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Student_RegisterActivity extends AppCompatActivity {
    private Button button7;
    private EditText editTextTextPersonName8;
    private EditText editTextTextPersonName9;
    private EditText editTextTextPersonName7;
    Spinner spinner3;
    private String dysfunctions;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        spinner3 = (Spinner) findViewById(R.id.spinner3);
        String[] options = {Dysfunctions.AUTISM.toString(), Dysfunctions.VISUALLY_IMPAIRED.toString(), Dysfunctions.NO_DYSFUNCTIONS.toString()};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter);

        button7 = findViewById(R.id.button7);
        editTextTextPersonName8 = findViewById(R.id.editTextTextPersonName8);
        editTextTextPersonName9 = findViewById(R.id.editTextTextPersonName9);
        editTextTextPersonName7 = findViewById(R.id.editTextTextPersonName7);

        loadingBar = new ProgressDialog(this);

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dysfunctions = spinner3.getSelectedItem().toString();
                CreateAccount();
            }
        });
    }

    public void backtoMain(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void CreateAccount() {
        String username = editTextTextPersonName8.getText().toString();
        String email = editTextTextPersonName9.getText().toString();
        String password = editTextTextPersonName7.getText().toString();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please write your name...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please write your email...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            register(username, email, password, dysfunctions);
        }
    }


    public void register(String username, String email, String password, String dysfunctions) {
        double grade = 0;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("students")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            Log.w(TAG, "This " + email + "already exists");
                            Intent intent = new Intent(Student_RegisterActivity.this, MainActivity.class);
                            startActivity(intent);

                        } else {
                            Map<String, Object> user = new HashMap<>();
                            user.put("username", username);
                            user.put("email", email);
                            user.put("password", password);
                            user.put("grade", grade);
                            user.put("dysfunctions", dysfunctions);
                            user.put("myTeachers", "");

                            // Add a new document with a generated ID
                            db.collection("students")
                                    .add(user)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                            Intent intent = new Intent(Student_RegisterActivity.this, StudentLoginActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error adding document", e);
                                        }
                                    });
                        }
                    }
                });
    }

}