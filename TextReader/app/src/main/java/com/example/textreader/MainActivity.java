package com.example.textreader;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private static int OPEN_DIRECTORY_REQUEST_CODE = 1;
    private Button mChoose;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mChoose = findViewById(R.id.choose);
        mChoose.setOnClickListener(v -> {
            try {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("*/*");
                startActivityForResult(i, OPEN_DIRECTORY_REQUEST_CODE);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);

        try {
            Uri uri = resultData.getData();
            InputStream i = getContentResolver().openInputStream(uri);
            String data = convertStreamToString(i);

            Intent intent = new Intent(this, ReadingActivity.class);
            Bundle args = new Bundle();
            args.putString(ReadingActivity.DATA, data);
            intent.putExtras(args);
            startActivity(intent);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
