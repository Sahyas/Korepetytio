package com.example.korepetytio;

import static android.content.ContentValues.TAG;

import static com.example.korepetytio.ChooseHourActivity.currentMyTeachers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.korepetytio.client.ClientRole;
import com.example.korepetytio.client.Dysfunctions;
import com.example.korepetytio.client.Student;
import com.example.korepetytio.client.Teacher;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class StudentLoginActivity extends AppCompatActivity {
    private Button button6;
    private EditText editTextTextPersonName2;
    private EditText editTextTextPersonName3;
    private ProgressDialog loadingBar;
    public static Student currentOnlineStudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button6 = findViewById(R.id.button6);
        editTextTextPersonName2 = findViewById(R.id.editTextTextPersonName2);
        editTextTextPersonName3 = findViewById(R.id.editTextTextPersonName3);

        loadingBar = new ProgressDialog(this);

        button6.setOnClickListener(new View.OnClickListener() {
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
        String email = editTextTextPersonName2.getText().toString();
        String password = editTextTextPersonName3.getText().toString();
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
        db.collection("students")
                .whereEqualTo("email", email)
                .whereEqualTo("password", password)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            //
                            String name = String.valueOf(queryDocumentSnapshots.getDocuments().get(0).getData().get("username"));
                            Double grade = (Double) queryDocumentSnapshots.getDocuments().get(0).getData().get("grade");
                            currentMyTeachers = String.valueOf(queryDocumentSnapshots.getDocuments().get(0).getData().get("myTeachers"));
                            String dysfunction = String.valueOf(queryDocumentSnapshots.getDocuments().get(0).getData().get("dysfunctions"));
                            if(dysfunction == "null") {
                                dysfunction = "NO_DYSFUNCTIONS";
                            }
                            currentOnlineStudent = new Student(name, password, email, ClientRole.STUDENT, grade, Dysfunctions.valueOf(dysfunction));
                            Log.d(TAG, "TAAAAA:        " + name);
                            Log.d(TAG, "TAAAAA:        " + currentOnlineStudent);
                            Intent intent = new Intent(StudentLoginActivity.this, MenuActivity.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(StudentLoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }
}