package com.example.korepetytio;

import static android.content.ContentValues.TAG;
import static com.example.korepetytio.TeacherLoginActivity.currentOnlineTeacher;
import static com.example.korepetytio.client.Student.myTeachers;
import static com.example.korepetytio.StudentLoginActivity.currentOnlineStudent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.korepetytio.client.MyTeacher;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ChooseHourActivity extends AppCompatActivity implements View.OnClickListener {
    public static String teacherName;
    private TextView text;
    private TextView chosenHours;
    private Button buttonSet;
    private int hour = 0;
    private int min = 0;
    private Calendar selectedDate;
    private Button setDateButton;
    private TextView chosenDate;
    private Button acceptButton;
    private int day;
    private int month;
    private int year;
    public static String currentMyTeachers;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_hour2);
        chosenHours = findViewById(R.id.textViewChosenHours);
        selectedDate = Calendar.getInstance();
        setDateButton = findViewById(R.id.setDateButton);
        chosenDate = findViewById(R.id.textViewChosenDate);
        acceptButton = findViewById(R.id.acceptButton);
        text = findViewById(R.id.textViewChooseHours);
        text.setText("");
        text.setText("Choose reception hours for" + " " + teacherName.trim() + ":");

        buttonSet = findViewById(R.id.button_set);
        buttonSet.setOnClickListener(this);

        setDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int currentYear = calendar.get(Calendar.YEAR);
                int currentMonth = calendar.get(Calendar.MONTH);
                int currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ChooseHourActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectedDate.set(year, month, dayOfMonth);
                    }
                }, currentYear, currentMonth, currentDayOfMonth);

                datePickerDialog.show();
            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = day + "." + month + "." + year + " " + hour + ":" + min;
                Log.d(TAG, date);
                MyTeacher teacher = new MyTeacher(teacherName, date);
                myTeachers.add(teacher);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference studentsRef = db.collection("students");
                Query query = studentsRef.whereEqualTo("email", currentOnlineStudent.getEmail());
                query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            // zaktualizuj pole "password" w dokumencie dla użytkownika o danym adresie e-mail
                            String documentId = documentSnapshot.getId();
                            DocumentReference documentReference = studentsRef.document(documentId);
                            documentReference.update("myTeachers", currentMyTeachers + "|" + teacherName + date);
                            currentMyTeachers = currentMyTeachers + " |" + teacherName + date;
                            Intent intent = new Intent(ChooseHourActivity.this, MyProfileActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == buttonSet) {
            Calendar calendar = Calendar.getInstance();
            int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            int currentMinute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(),
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            hour = hourOfDay;
                            min = minute;
                            Log.d(TAG, String.valueOf(hour) + min);
                            chosenHours = findViewById(R.id.textViewChosenHours);
                            chosenHours.setText("");
                            chosenHours.setText(hour + ":" + min);
                            year = selectedDate.get(Calendar.YEAR);
                            month = selectedDate.get(Calendar.MONTH);
                            day = selectedDate.get(Calendar.DAY_OF_MONTH);
                            chosenDate.setText(day + "." + month + "." + year);
                        }
                    }, currentHour, currentMinute, false);
            timePickerDialog.show();
        }
    }
}
