package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {

    public static int OK = 0;
    public static int NOT_OK = 1;

    public static String TASK_TITLE = "title";
    public static String TASK_DATE = "date";

    private EditText mTitle = null;
    private EditText mDate = null;
    private Button mButton = null;

    private DatePickerDialog.OnDateSetListener listener;

    private TaskManager taskManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        mTitle = findViewById(R.id.editTextTextPersonName3);
        mDate = findViewById(R.id.editTextDate);
        mButton = findViewById(R.id.button);
        taskManager = new TaskManager(this);

        Calendar calendar = Calendar.getInstance();

        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int date = calendar.get(Calendar.DATE);

        mDate.setOnClickListener(v -> {
            DatePickerDialog dlg = new DatePickerDialog(this, R.style.Theme_AppCompat, listener, year, month, date);
            //dlg.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
            dlg.show();
        });

        listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dayOfMonth+"/"+month+"/"+year;
                mDate.setText(date);
            }
        };

        mButton.setOnClickListener(v -> {
            String title = mTitle.getText().toString();
            String strDate = mDate.getText().toString();
            try {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(TASK_TITLE, title);
                bundle.putString(TASK_DATE, strDate);
                intent.putExtras(bundle);
                setResult(OK, intent);
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
                setResult(NOT_OK);
            } finally {
                finish();
            }
        });
        return;
    }
}