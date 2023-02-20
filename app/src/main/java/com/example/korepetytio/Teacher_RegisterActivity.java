package com.example.korepetytio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Teacher_RegisterActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText username;
    private FirebaseFirestore firestore ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_register);
    }


    public void backtoMain(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }




    public void register(View v) {
        email = (EditText)findViewById(R.id.editTextTextPersonName6);
        String tekst =email.getText().toString();
        password = (EditText)findViewById(R.id.editTextTextPersonName5);
        String tekst2 =password.getText().toString();
        username = (EditText)findViewById(R.id.editTextTextPersonName4);
        String tekst3 =username.getText().toString();



//        if(!tekst.isEmpty() && !tekst2.isEmpty() && !tekst3.isEmpty()){
            firestore = FirebaseFirestore.getInstance();
            Teacher teacher = new Teacher(new ClientUsername(tekst2),new ClientPassword(tekst3),new CLientEmail(tekst),
                    ClientRole.TEACHER, Dysfunctions.AUTISM,new LessonPrice(100.0),new Subject("Matematyka"));
            Map<String, Object> user = new HashMap<>();
//            user.put("Teachers", new Teacher(new ClientUsername(tekst2),new ClientPassword(tekst3),new CLientEmail(tekst), ClientRole.TEACHER, Dysfunctions.AUTISM,new LessonPrice(100.0),new Subject("Matematyka")));
            user.put("EMAIL", tekst);
            user.put("PASSWORD", tekst2);
            user.put("USERNAME", tekst3);
            firestore.collection("users").get();

            firestore.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(getApplicationContext(), "Success",Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Failure",Toast.LENGTH_LONG).show();
                }
            });


            Intent i = new Intent(this, AppActivity.class);
            startActivity(i);
        }
//   }
}