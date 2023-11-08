package com.example.project_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
    String correctAnswer;
    int remainingQuestions, numCorrectAnswers;
    long timeStart;
    SharedPreferences sp;
    SharedPreferences.Editor spEditor;
    TextView tvQuizWord, tvQuestionLabel, tvHeader;
    boolean synonym, definition, all;
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


        btnSubmit.setOnClickListener(v->{
            if(choiceSelected()){
                checkAnswer();
            }else{
                Toast.makeText(this, "Please select an answer.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean choiceSelected(){
        //makes sure that a radiobutton is selected
        return rdoGroup.getCheckedRadioButtonId() != -1;
    }

    private DictionaryItem getANewWord(){
        //pick a random word to quiz the user on
        Random randomElement = new Random();
        int index = randomElement.nextInt(words.size());
        return words.get(index);
    }

    private void checkAnswer(){
        //checks the answer that the user picked against the correct answer
        RadioButton checked = (RadioButton) rdoGroup.getChildAt(rdoGroup.getCheckedRadioButtonId());
        if(checked.getText().equals(correctAnswer)){
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            numCorrectAnswers++;
        }else{
            Toast.makeText(this, "Incorrect. The correct answer was: " + correctAnswer, Toast.LENGTH_LONG).show();
        }
    }

    private void generateNewQuestion(){
        DictionaryItem newWord = getANewWord();
        tvQuizWord.setText(newWord.getWord());

        choices = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            //will populate arraylist with 3 incorrect dictionaryItems
            DictionaryItem temp = getANewWord();
            if(!temp.word.equals(newWord.word)){ //makes sure that the added choice is not the correct dictionaryItem
                choices.add(temp);
            }else{
                choices.add(getANewWord());
            }
        }
        choices.add(newWord); //adds the correct dictionaryItem
        Collections.shuffle(choices); //shuffle the arraylist so that the choices are randomly ordered
        if(definition){
            //get the definition
            correctAnswer = newWord.getDefinition();
        }else if(synonym){
            //get the synonym
            correctAnswer = newWord.getSynonyms().get(0);
        }else{ //user selected to have both definitions and synonyms on the quiz, so pick one at random be correct
            if(choose() == 0){
                correctAnswer = newWord.getDefinition();
            }else{
                correctAnswer = newWord.getSynonyms().get(0);
            }
        }

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
}