package com.example.korepetytio;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_hour2);
        chosenHours = findViewById(R.id.textViewChosenHours);
        selectedDate = Calendar.getInstance();
        setDateButton = findViewById(R.id.setDateButton);
        chosenDate = findViewById(R.id.textViewChosenDate);
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
                            int year = selectedDate.get(Calendar.YEAR);
                            int month = selectedDate.get(Calendar.MONTH);
                            int day = selectedDate.get(Calendar.DAY_OF_MONTH);
                            chosenDate.setText(day + "." + month + "." + year);
                        }
                    }, currentHour, currentMinute, false);
            timePickerDialog.show();
        }
    }
}
