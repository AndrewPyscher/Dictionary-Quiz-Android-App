package com.example.project_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class StatsActivity extends AppCompatActivity {

    TextView tvTime, tvPercentageCorrect;

    Button btnPlayAgain, btnBackToMain;

    //integer for how many milliseconds it took user to finish quiz
    int millisecondsToFinish;

    //variables for number of correct answers, questions from quiz, and percentage of correct answers
    int correctAnswers, numOfQuestions;
    double percentageCorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        tvTime = findViewById(R.id.tvTime);
        tvPercentageCorrect = findViewById(R.id.tvCorrectAnswersPercent);

    }
}