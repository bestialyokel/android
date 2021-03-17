package com.example.lab2_dop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class CalculatingActivity extends AppCompatActivity {

    public static final String OP_CODE = "OP_CODE";

    public static final String RESULT = "RESULT";

    public static final String ADD = "ADD";
    public static final String SUB = "SUB";

    public static final String ARG1 = "ARG1";
    public static final String ARG2 = "ARG2";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        double a = bundle.getDouble(ARG1);
        double b = bundle.getDouble(ARG2);

        String op = bundle.getString(OP_CODE);

        double result = 0;

        switch (op) {
            case ADD:
                result = a+b;
                break;
            case SUB:
                result = a-b;
                break;
        }

        Intent intentReturn = new Intent();
        Bundle bundleReturn = new Bundle();

        bundleReturn.putDouble(RESULT, result);

        intentReturn.putExtras(bundleReturn);

        setResult(0, intentReturn);
        finish();
    }
}