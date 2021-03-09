package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    //для удобства
    public static final String MESSAGE = "msg";

    public void onShareButtonClick(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String msg = getIntent().getStringExtra(ResultActivity.MESSAGE);
        intent.putExtra(ResultActivity.MESSAGE, msg);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String msg = intent.getStringExtra(ResultActivity.MESSAGE);
        if (!msg.equals(null)) {
            TextView text = (TextView)findViewById(R.id.textView2);
            text.setText(msg);
        }
    }
}