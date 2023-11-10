package com.example.project_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class StatsActivity extends AppCompatActivity {

    TextView tvTime, tvPercentageCorrect;

    Button btnPlayAgain, btnBackToMain;

    //integer for how many milliseconds it took user to finish quiz
    Long millisecondsToFinish;

    //variables for number of correct answers, questions from quiz, and percentage of correct answers
    int correctAnswers, numOfQuestions;
    double percentageCorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        tvTime = findViewById(R.id.tvTime);
        tvPercentageCorrect = findViewById(R.id.tvCorrectAnswersPercent);
        btnBackToMain = findViewById(R.id.btnBackToMain);
        btnPlayAgain = findViewById(R.id.btnPlayAgain);

        //get intent from quiz that holds millisecondsToFinish, the number of correctly answered questions, and the total number of questions
        Intent quizIntent = getIntent();
        millisecondsToFinish = quizIntent.getLongExtra("totalTime", -1);
        correctAnswers = quizIntent.getIntExtra("numCorrectAnswers", -1);

        SharedPreferences sp = getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE);
        numOfQuestions = sp.getInt("NUM_QUESTIONS", 10);


        updateTime(millisecondsToFinish);
        updateTimeColor(millisecondsToFinish);

        //five out of 10 as a place holder until quiz activity is working
        updatePercentageCorrect(correctAnswers, numOfQuestions);
        updatePercentageCorrectColor(correctAnswers, numOfQuestions);

        btnPlayAgain.setOnClickListener(e->{
            Intent playAgainIntent = new Intent(this, Quiz.class);
            startActivity(playAgainIntent);
        });

        btnBackToMain.setOnClickListener(e->{
            Intent backToMainIntent = new Intent(this, MainActivity.class);
            startActivity(backToMainIntent);
        });

    }

    //make time background color green if under 30 seconds, yellow if under 1 minute, and red if over 1 minute
    public void updateTimeColor (Long milliseconds) {
        if (milliseconds < 30000) {
            tvTime.setBackgroundColor(Color.GREEN);
        }
        else if (30000 < milliseconds && milliseconds <= 60000) {
            tvTime.setBackgroundColor(Color.YELLOW);
        }
        else if (60000 < milliseconds) {
            tvTime.setBackgroundColor(Color.RED);
        }
    }

    //make percentage correct text color green if above 80%, yellow if above 60%, and red if bellow 60%
    public void updatePercentageCorrectColor(double correct, double total) {
        double rawGrade = (correct / total) * 100;

        if (rawGrade >= 80) {
            tvPercentageCorrect.setBackgroundColor(Color.GREEN);
        }
        else if (60 <= rawGrade && rawGrade < 80) {
            tvPercentageCorrect.setBackgroundColor(Color.YELLOW);
        }
        else if (rawGrade < 60) {
            tvPercentageCorrect.setBackgroundColor(Color.RED);
        }
    }

    //display time on tvTime
    public void updateTime (Long milliseconds) {
        String time = "00:00";

        Long totalSeconds = milliseconds / 1000;
        Long minutes = totalSeconds / 60;
        Long seconds = totalSeconds % 60;

        if (seconds < 10) {
            time = minutes + ":0" + seconds;
        }
        else {
            time = minutes + ":" + seconds;
        }

        tvTime.setText(time);
    }

    //display percentage of correct answers
    public void updatePercentageCorrect(double correct, double total) {
        double rawGrade = (correct / total) * 100;

        tvPercentageCorrect.setText(rawGrade + "%");
    }

}