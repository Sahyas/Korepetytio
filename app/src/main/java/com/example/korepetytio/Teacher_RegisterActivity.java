package com.example.korepetytio;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.korepetytio.client.CLientEmail;
import com.example.korepetytio.client.ClientPassword;
import com.example.korepetytio.client.ClientRole;
import com.example.korepetytio.client.ClientUsername;
import com.example.korepetytio.client.Dysfunctions;
import com.example.korepetytio.client.LessonPrice;
import com.example.korepetytio.client.Subject;
import com.example.korepetytio.client.Teacher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Teacher_RegisterActivity extends AppCompatActivity {

    private Button button7;
    private EditText editTextTextPersonName5;
    private EditText editTextTextPersonName6;
    private EditText editTextTextPersonName4;
    private FirebaseFirestore firestore ;

    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_register);

        button7 = (Button) findViewById(R.id.button7);
        editTextTextPersonName5 = (EditText) findViewById(R.id.editTextTextPersonName5);
        editTextTextPersonName6 = (EditText) findViewById(R.id.editTextTextPersonName6);
        editTextTextPersonName4 = (EditText) findViewById(R.id.editTextTextPersonName4);
        loadingBar = new ProgressDialog(this);

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please write your name...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please write your email...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            register(name, email, password);
        }
    }


    public void register(String username, String email, String password) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> user = new HashMap<>();
        user.put("username", username);
        user.put("email", email);
        user.put("password", password);


// Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        Intent intent = new Intent(Teacher_RegisterActivity.this, LoginActivity.class);
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