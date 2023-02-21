package com.example.korepetytio;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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
    private List<String> teachers = new ArrayList<>();
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
                                teachers.add(String.valueOf(document.getData().get("username")));
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            Log.d(TAG, "CALA LISTA" + teachers);
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
        adapter = new ArrayAdapter<String>(this, R.layout.single_teacher, teachers);
        list.setAdapter(adapter);
    }
}