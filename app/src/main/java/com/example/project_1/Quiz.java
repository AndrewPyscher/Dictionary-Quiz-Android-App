package com.example.project_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Quiz extends AppCompatActivity {
    Button btnSubmit, btnStart;
    RadioGroup rdoGroup;
    ArrayList<DictionaryItem> choices; //holds the list of choices for a given question
    ArrayList<DictionaryItem> words; //holds the list of words that the user will be quizzed on
    DictionaryItem correctAnswer;
    int remainingQuestions, numCorrectAnswers;
    long timeStart;
    SharedPreferences sp;
    SharedPreferences.Editor spEditor;
    TextView tvQuizWord, tvQuestionLabel, tvHeader;
    boolean synonym, definition, all;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnStart = findViewById(R.id.btnStart);
        rdoGroup = findViewById(R.id.radioGroup);
        tvQuizWord = findViewById(R.id.tvQuizWord);
        tvQuestionLabel = findViewById(R.id.tvQuestionLabel);
        tvHeader = findViewById(R.id.tvHeader);

        sp = getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE);
        spEditor = sp.edit();
        remainingQuestions = sp.getInt("QUIZ_QUESTION_PREF", 10); //whatever the shared preference is named in settings activity
        definition = sp.getBoolean("QUIZ_MODE_DEFINITIONS", false);
        synonym = sp.getBoolean("QUIZ_MODE_SYNONYMS", false);
        all = sp.getBoolean("QUIZ_MODE_ALL", true);

        initialize();
        words = Dictionary.dictionary;

        btnStart.setOnClickListener(e->{
            btnStart.setVisibility(View.INVISIBLE);
            rdoGroup.setVisibility(View.VISIBLE);
            tvQuizWord.setVisibility(View.VISIBLE);
            tvQuestionLabel.setVisibility(View.VISIBLE);
            btnSubmit.setVisibility(View.VISIBLE);
            tvHeader.setVisibility(View.VISIBLE);
            timeStart = System.currentTimeMillis();
        });

        while(remainingQuestions > 0){
            generateNewQuestion();
            remainingQuestions--;
        }

        if(remainingQuestions == 0){
            long totalTime = System.currentTimeMillis() - timeStart;
            Intent statsActivity = new Intent(this, StatsActivity.class);
            statsActivity.putExtra("totalTime", totalTime);
            statsActivity.putExtra("numCorrectAnswers", numCorrectAnswers);
            startActivity(statsActivity);
        }

        btnSubmit.setOnClickListener(v->{
            if(choiceSelected()){
                checkAnswer();
            }else{
                Toast.makeText(this, "Please select an answer.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initialize(){
        rdoGroup.setVisibility(View.INVISIBLE);
        tvQuizWord.setVisibility(View.INVISIBLE);
        tvQuestionLabel.setVisibility(View.INVISIBLE);
        btnSubmit.setVisibility(View.INVISIBLE);
        if(synonym){
            tvHeader.setText("Select the synonym that correctly matches the word");
        }else if(definition){
            tvHeader.setText("Select the definition that correctly matches the word");
        }else{
            tvHeader.setText("Select the definition or synonym that correctly matches the word");
        }
    }

    private boolean choiceSelected(){
        //makes sure that a radiobutton is selected
        return rdoGroup.getCheckedRadioButtonId() != -1;
    }

    private DictionaryItem getANewItem(){
        //pick a random word to quiz the user on
        Random randomElement = new Random();
        int index = randomElement.nextInt(words.size()-1);
        return words.get(index);
    }

    private void checkAnswer(){
        //checks the answer that the user picked against the correct answer
        RadioButton checked = (RadioButton) rdoGroup.getChildAt(rdoGroup.getCheckedRadioButtonId());
        if (rdoGroup.getChildAt(pos) == checked){
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            numCorrectAnswers++;
        }else{
            Toast.makeText(this, "Incorrect. The correct answer was: " + correctAnswer, Toast.LENGTH_LONG).show();
        }
    }

    private void generateNewQuestion(){
        correctAnswer = getANewItem(); //gets a new correct answer
        tvQuizWord.setText(correctAnswer.getWord()); //show the word that the user will have to guess the syn/def for

        choices = new ArrayList<>(); //will hold a list of dictionaryItems that will be displayed as options on the quiz
        choices.add(correctAnswer); //adds the correct dictionaryItem
        for(int i = 0; i < 3; i++){ //will populate arraylist with 3 incorrect dictionaryItems
            DictionaryItem temp = getANewItem(); //gets a new dictionaryItem
            if(!temp.word.equals(correctAnswer.word)){ //makes sure that the added choice is not the correct dictionaryItem
                choices.add(temp);
            }else{
                choices.add(getANewItem());
            }
        }
        Collections.shuffle(choices); //shuffle the arraylist so that the choices are randomly ordered
        pos = choices.indexOf(correctAnswer); //gets the position of the correctAnswer in the arraylist

        for(int i = 0; i < choices.size(); i++){
            RadioButton rb = (RadioButton) rdoGroup.getChildAt(i);
            if(definition){
                rb.setText(choices.get(i).getDefinition());
            }else if (synonym){
                rb.setText(choices.get(i).getSynonyms().get(0));
            }else{ //user selected to have both definitions and synonyms on the quiz, so pick one at random to show
                if(choose() == 0){
                    rb.setText(choices.get(i).getDefinition());
                }else{
                    rb.setText(choices.get(i).getSynonyms().get(0));
                }
            }
        }
    }

    private int choose(){
        //randomly returns a 0 or 1
        Random randomNum = new Random();
        return randomNum.nextInt(2);
    }

}