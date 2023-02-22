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
import com.example.korepetytio.client.Subject;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Teacher_RegisterActivity extends AppCompatActivity {

    private Button button7;
    private EditText editTextTextPersonName5;
    private EditText editTextTextPersonName6;
    private EditText editTextTextPersonName4;
    private String dysfunctions, subject;
    private EditText price;
    Spinner spinner;
    Spinner spinner2;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_register);

        spinner = (Spinner) findViewById(R.id.spinner);
        String[] options = {Dysfunctions.AUTISM.toString(), Dysfunctions.VISUALLY_IMPAIRED.toString(), Dysfunctions.NO_DYSFUNCTIONS.toString()};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        String[] options2 = {Subject.English.toString(), Subject.Mathematics.toString(), Subject.Polish.toString(), Subject.IT.toString()};

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        button7 = findViewById(R.id.button7);
        editTextTextPersonName5 = findViewById(R.id.editTextTextPersonName5);
        editTextTextPersonName6 = findViewById(R.id.editTextTextPersonName6);
        editTextTextPersonName4 = findViewById(R.id.editTextTextPersonName4);
        price = findViewById(R.id.editTextTextPersonName12);

        loadingBar = new ProgressDialog(this);



        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dysfunctions = spinner.getSelectedItem().toString();
                subject = spinner2.getSelectedItem().toString();
                CreateAccount();
            }
        });
    }

    public void backtoMain(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void CreateAccount() {
        String name = editTextTextPersonName5.getText().toString();
        String email = editTextTextPersonName6.getText().toString();
        String password = editTextTextPersonName4.getText().toString();
        String lessonPrice = price.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please write your name...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please write your email...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(lessonPrice)){
            Toast.makeText(this, "Please write your lessonPrice...", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            register(name, email, password, lessonPrice, dysfunctions, subject);
        }
    }


    public void register(String username, String email, String password, String lessonPrice, String dysfunctions, String subject) {
        double grade = 1;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("teachers")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            Log.w(TAG, "This " + email + "already exists");
                            Intent intent = new Intent(Teacher_RegisterActivity.this, MainActivity.class);
                            startActivity(intent);

                        } else {
                            Map<String, Object> user = new HashMap<>();
                            user.put("username", username);
                            user.put("email", email);
                            user.put("password", password);
                            user.put("grade", grade);
                            user.put("dysfunctions", dysfunctions);
                            user.put("subject", subject);
                            user.put("price", lessonPrice);

                            // Add a new document with a generated ID
                            db.collection("teachers")
                                    .add(user)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                            Intent intent = new Intent(Teacher_RegisterActivity.this, TeacherLoginActivity.class);
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