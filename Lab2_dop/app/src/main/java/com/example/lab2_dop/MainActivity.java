package com.example.lab2_dop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int OK = 1;

    private Button mAddButton = null;
    private Button mSubButton = null;

    private EditText mNum1 = null;
    private EditText mNum2 = null;

    private TextView mResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {

        }

        mAddButton = (Button) findViewById(R.id.button);
        mSubButton = (Button) findViewById(R.id.button2);
        mResult = (TextView) findViewById(R.id.result);
        mNum1 = (EditText) findViewById(R.id.num1);
        mNum2 = (EditText) findViewById(R.id.num2);


        mAddButton.setOnClickListener(v -> {
                Intent intent = new Intent(this, CalculatingActivity.class);
                Bundle bundle = new Bundle();
                String num1 = mNum1.getText().toString();
                String num2 = mNum2.getText().toString();

                if (num1.isEmpty() || num2.isEmpty()) {
                    return;
                }

                double arg1 = Double.valueOf(num1);
                double arg2 = Double.valueOf(num2);
                bundle.putDouble(CalculatingActivity.ARG1, arg1);
                bundle.putDouble(CalculatingActivity.ARG2, arg2);
                bundle.putString(CalculatingActivity.OP_CODE, CalculatingActivity.ADD);
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
        });

        mSubButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CalculatingActivity.class);
            Bundle bundle = new Bundle();
            String num1 = mNum1.getText().toString();
            String num2 = mNum2.getText().toString();

            if (num1.isEmpty() || num2.isEmpty()) {
                return;
            }

            double arg1 = Double.valueOf(num1);
            double arg2 = Double.valueOf(num2);
            bundle.putDouble(CalculatingActivity.ARG1, arg1);
            bundle.putDouble(CalculatingActivity.ARG2, arg2);
            bundle.putString(CalculatingActivity.OP_CODE, CalculatingActivity.SUB);
            intent.putExtras(bundle);
            startActivityForResult(intent, 0);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            Bundle b = data.getExtras();
            double result = b.getDouble(CalculatingActivity.RESULT);
            mResult.setText(String.valueOf(result));
        }
    }
}