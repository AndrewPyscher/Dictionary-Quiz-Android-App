package com.example.project_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btnQuiz;
    Button btnBrowseWords;

    Button btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSettings = findViewById(R.id.btnSettings);
        btnQuiz = findViewById(R.id.btnQuiz);
        btnBrowseWords = findViewById(R.id.btnBrowseWords);

        btnQuiz.setOnClickListener(e->{
            //to be set later when quiz class is created
            Intent intent = new Intent(this, Quiz.class);
            startActivity(intent);
        });
        btnBrowseWords.setOnClickListener(e->{
            //to be set later when browse words activity is created
            Intent intent = new Intent(this, BrowseWords.class);
            startActivity(intent);
        });
        btnSettings.setOnClickListener(e->{
            //to be set later when browse words activity is created
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        });
    }
}