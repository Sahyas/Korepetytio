package com.example.korepetytio;

import static com.example.korepetytio.TeacherLoginActivity.currentOnlineTeacher;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.korepetytio.client.ClientRole;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ChangePasswordActivity extends AppCompatActivity {
    private Button button13;
    private EditText password1;
    private EditText password2;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        button13 = findViewById(R.id.button13);
        password1 = findViewById(R.id.editTextTextPersonName10);
        password2 = findViewById(R.id.editTextTextPersonName11);

        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validPassword();
            }
        });

    }
    public void backtoMain(View v) {
        Intent i = new Intent(this, MyProfileActivity.class);
        startActivity(i);
    }

    private void validPassword() {
        String newPassword = password1.getText().toString();
        String newPasswordRe = password2.getText().toString();
        if (TextUtils.isEmpty(newPassword)) {
            Toast.makeText(this, "Please write your new password...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(newPasswordRe)) {
            Toast.makeText(this, "Please write your new password...", Toast.LENGTH_SHORT).show();
        } else if (!newPassword.equals(newPasswordRe)) {
            Toast.makeText(this, "The passwords are difrent...", Toast.LENGTH_SHORT).show();
        } else {
//            loadingBar.setTitle("Change password");
//            loadingBar.setMessage("Please wait, while we are checking the credentials.");
//            loadingBar.setCanceledOnTouchOutside(false);
//            loadingBar.show();
            changePassword(newPassword, currentOnlineTeacher.getEmail());
        }
    }

    public void changePassword(String newPassword, String email) {
        String collection;
        if(currentOnlineTeacher.getRole()== ClientRole.STUDENT){
            collection = "students";
        }
        else {
            collection = "teachers";
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference studentsRef = db.collection(collection);
        Query query = studentsRef.whereEqualTo("email", email);
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    // zaktualizuj pole "password" w dokumencie dla użytkownika o danym adresie e-mail
                    String documentId = documentSnapshot.getId();
                    DocumentReference documentReference = studentsRef.document(documentId);
                    documentReference.update("password", newPassword);
                    currentOnlineTeacher.setPassword(newPassword);
                    Intent intent = new Intent(ChangePasswordActivity.this, MyProfileActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}