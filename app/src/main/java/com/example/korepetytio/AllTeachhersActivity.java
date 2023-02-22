package com.example.korepetytio;

import static android.content.ContentValues.TAG;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
                                teachersList.add("Nauczyciel:  " + document.getData().get("username") + "\nOcena nauczyciela to: " + document.getData().get("grade")
                                        + "\nCena nauczyciela to: " + document.getData().get("price") + "\nPrzedmiot nauczyciela to :" + document.getData().get("subject")
                                        + "\nDysfunkcja: " + document.getData().get("dysfunctions"));
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
        ListView lv = (ListView) findViewById(R.id.listView1);
        Log.d(TAG, "duuuuupa" + teachers);
        //adapter = new ArrayAdapter<String>(this, R.layout.single_teacher, teachersList);
        lv.setAdapter(new MyListAdapter(this, R.layout.single_teacher, teachersList));
    }

    public void backtoChooseList(View v) {
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
    }

    private class MyListAdapter extends ArrayAdapter<String> {

        public Client teacher;
        private int layout;
        public MyListAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
            layout = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder mainViewholder = null;
            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                //viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.list_item_thumbnail);
                viewHolder.title = (TextView) convertView.findViewById(R.id.Row);
                viewHolder.button = (Button) convertView.findViewById(R.id.list_item_btn);
                viewHolder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "Clicked" + position, Toast.LENGTH_SHORT).show();
                    }
                });
                convertView.setTag(viewHolder);
            }else{
                mainViewholder = (ViewHolder) convertView.getTag();
                mainViewholder.title.setText(getItem(position));
            }
            return convertView;
        }
    }
    public class ViewHolder {
        ImageView thumbnail;
        TextView title;
        Button button;
    }
}