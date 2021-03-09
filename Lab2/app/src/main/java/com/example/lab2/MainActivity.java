package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public void onSendButtonClick(View view) {
        Intent intent = new Intent(this, ResultActivity.class);
        EditText mEdit = (EditText)findViewById(R.id.messageInput);
        intent.putExtra(ResultActivity.MESSAGE, mEdit.getText().toString());
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}