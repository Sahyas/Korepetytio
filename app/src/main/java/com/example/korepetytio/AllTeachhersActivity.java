package com.example.korepetytio;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.korepetytio.client.Client;
import com.example.korepetytio.client.ClientRole;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AllTeachhersActivity extends Activity {

    private ListView list;
    private ArrayAdapter<String> adapter;
    private List<Client> teachers = new ArrayList<>();

    private List<String> teachersList = new ArrayList<>();
    private Button refreshButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_teachhers);
        allTeachers();

        refreshButton = findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
    }

    public void allTeachers() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("teachers")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (!documentSnapshots.isEmpty()) {
                            for (DocumentSnapshot document : documentSnapshots.getDocuments()) {
                                Log.d(TAG, "TAAAAA" + String.valueOf(document.getData().get("username")));
//                                teachers.add(String.valueOf(document.getData().get("username")));

                                teachers.add(new Client(String.valueOf(document.getData().get("username")), String.valueOf(document.getData().get("password")),
                                        String.valueOf(document.getData().get("email")), ClientRole.TEACHER, (Double) document.getData().get("grade")));
                               teachersList.add("Nauczyciel: " + document.getData().get("username") + "\nOcena nauczyciela to:" + document.getData().get("grade"));
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            Log.d(TAG, "CALA LISTA" + teachers.get(0).getUsername() + " " + teachers.get(0).getGrade());
                        } else {
                            Log.d(TAG, "Error getting documents: ");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error getting data!!!", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void refresh() {
        list = (ListView) findViewById(R.id.listView1);
        Log.d(TAG, "duuuuupa" + teachers);
        adapter = new ArrayAdapter<String>(this, R.layout.single_teacher, teachersList);
        list.setAdapter(adapter);
    }

    public void backtoChooseList(View v) {
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
    }
}