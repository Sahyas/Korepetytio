package com.example.korepetytio;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.korepetytio.client.Client;
import com.example.korepetytio.client.Dysfunctions;
import com.example.korepetytio.client.Subject;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GuestViewActivity extends AppCompatActivity {

    private ListView list;
    private ArrayAdapter<String> adapter;
    Spinner spinner4;
    Spinner spinner5;
    private String dysfunctions = "ALL", subject = "ALL";

    private List<String> teachersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_view);

        spinner4 = (Spinner) findViewById(R.id.spinner4);
        String[] options = {"ALL", Dysfunctions.AUTISM.toString(), Dysfunctions.VISUALLY_IMPAIRED.toString(), Dysfunctions.NO_DYSFUNCTIONS.toString()};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter);

        spinner5 = (Spinner) findViewById(R.id.spinner5);
        String[] options2 = {"ALL", Subject.English.toString(), Subject.Mathematics.toString(), Subject.Polish.toString(), Subject.IT.toString()};

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner5.setAdapter(adapter2);

        Button refreshButton = findViewById(R.id.refreshButtonGuest);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
    }

    public void allTeachers() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        teachersList.clear();
        db.collection("teachers")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (!documentSnapshots.isEmpty()) {
                            for (DocumentSnapshot document : documentSnapshots.getDocuments()) {
                                if(Objects.equals(subject, document.getData().get("subject")) && Objects.equals(dysfunctions, document.getData().get("dysfunctions"))){
                                    teachersList.add("Teacher:  " + document.getData().get("username") + "\nUsers' rating: " + document.getData().get("grade")
                                            + "\nPrice per hour: " + document.getData().get("price") + "\nTeaches: " + document.getData().get("subject")
                                            + "\nDysfunction: " + document.getData().get("dysfunctions"));
                                }
                                else if(Objects.equals(subject, "ALL") && Objects.equals(dysfunctions, document.getData().get("dysfunctions"))){
                                    teachersList.add("Teacher:  " + document.getData().get("username") + "\nUsers' rating: " + document.getData().get("grade")
                                            + "\nPrice per hour: " + document.getData().get("price") + "\nTeaches: " + document.getData().get("subject")
                                            + "\nDysfunction: " + document.getData().get("dysfunctions"));
                                }
                                else if(Objects.equals(dysfunctions, "ALL") && Objects.equals(subject, document.getData().get("subject"))){
                                    teachersList.add("Teacher:  " + document.getData().get("username") + "\nUsers' rating: " + document.getData().get("grade")
                                            + "\nPrice per hour: " + document.getData().get("price") + "\nTeaches: " + document.getData().get("subject")
                                            + "\nDysfunction: " + document.getData().get("dysfunctions"));
                                }
                                else if(Objects.equals(dysfunctions, "ALL") && Objects.equals(subject, "ALL")){
                                    teachersList.add("Teacher:  " + document.getData().get("username") + "\nUsers' rating: " + document.getData().get("grade")
                                            + "\nPrice per hour: " + document.getData().get("price") + "\nTeaches: " + document.getData().get("subject")
                                            + "\nDysfunction: " + document.getData().get("dysfunctions"));
                                }
                            }
                            if(teachersList.size() == 0) {
                                teachersList.add("No search results");
                            }
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
        ListView lv = (ListView) findViewById(R.id.listView1Guest);
        lv.setAdapter(new MyListAdapterGuest(this, R.layout.single_teacher_guest, teachersList));
    }

    public void backtoMain(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public void search(View v) {
        dysfunctions = spinner4.getSelectedItem().toString();
        subject = spinner5.getSelectedItem().toString();
        allTeachers();
        refresh();
    }

private class MyListAdapterGuest extends ArrayAdapter<String> {

    public Client teacher;
    private int layout;
    public MyListAdapterGuest(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        layout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        GuestViewActivity.ViewHolder mainViewHolder = null;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            mainViewHolder = new ViewHolder();
            mainViewHolder.title = (TextView) convertView.findViewById(R.id.RowGuest);
            convertView.setTag(mainViewHolder);
        }else{
            mainViewHolder = (GuestViewActivity.ViewHolder) convertView.getTag();
        }
        mainViewHolder.title.setText(getItem(position));
        return convertView;
    }
}

    public class ViewHolder {
        ImageView thumbnail;
        TextView title;
        Button button;
    }
}