package com.example.project_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

public class Quiz extends AppCompatActivity {
    Button btnSubmit, btnStart;
    RadioGroup rdoGroup;
    RadioButton rdoOptionA, rdoOptionB, rdoOptionC, rdoOptionD;
    ArrayList<DictionaryItem> choices; //holds the list of choices for a given question
    ArrayList<DictionaryItem> words; //holds the list of words that the user will be quizzed on
    String correctAnswer, type;
    Boolean favorites;
    int remainingQuestions, numCorrectAnswers;
    long timeStart;
    SharedPreferences sp;
    SharedPreferences.Editor spEditor;
    TextView tvQuizWord, tvQuestionLabel, tvHeader;
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
        ArrayList<String> wordList = getWords(); //populate 'words' with either a list of favorites or just a random list of words
        //the number of words in the list should be the same as the number of questions that the user wants to do
        words = convertListToItems(wordList);
        sp = getSharedPreferences("SETTINGS", Context.MODE_PRIVATE);
        spEditor = sp.edit();
        remainingQuestions = sp.getInt("NumberOfQuestions", 10); //whatever the shared preference is named in settings activity
        type = sp.getString("TypeOfQuestions", "Definitions");
        favorites = sp.getBoolean("FavoritesOnly", false);
        initialize();

        btnStart.setOnClickListener(e->{
            btnStart.setVisibility(View.INVISIBLE);
            rdoGroup.setVisibility(View.VISIBLE);
            tvQuizWord.setVisibility(View.VISIBLE);
            tvQuestionLabel.setVisibility(View.VISIBLE);
            btnSubmit.setVisibility(View.VISIBLE);
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
        if(rdoGroup.getCheckedRadioButtonId() == -1){
            return false;
        }
        return true;
    }

    private DictionaryItem getANewWord(){
        //pick a word to quiz the user on
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

    private ArrayList<String> getWords(){
        if(favorites){
            return (ArrayList<String>) sp.getStringSet("FavoriteWords", null);
        }else{
            ArrayList<String> wordList = new ArrayList<>();
            //return list of any words (size will be equal to number of questions)

        }
        return null;
    }

    private void generateNewQuestion(){
        DictionaryItem newWord = getANewWord();
        choices = new ArrayList<>();
        for(int i = 0; i < choices.size()-1; i++){
            //used to hold the list of options that will be shown on a given question
            choices.add(getANewWord());
        }
        choices.add(newWord);
        Collections.shuffle(choices);
        if(type.equals("Definitions")){
            //get the definition
            correctAnswer = newWord.getDefinitions().get(0);
        }else{
            //get the synonym
            correctAnswer = newWord.getSynonyms().get(0);
        }
        tvQuizWord.setText(newWord.getWord());

        for(int i = 0; i < choices.size(); i++){
            RadioButton rb = (RadioButton) rdoGroup.getChildAt(i);
            if(type.equals("Definitions")){
                rb.setText(choices.get(i).getDefinitions().get(0));
            }else{
                rb.setText(choices.get(i).getSynonyms().get(0));
            }
        }
    }

    private void initialize(){
        rdoGroup.setVisibility(View.INVISIBLE);
        tvQuizWord.setVisibility(View.INVISIBLE);
        tvQuestionLabel.setVisibility(View.INVISIBLE);
        btnSubmit.setVisibility(View.INVISIBLE);
        if(type.equals("Synonyms")){
            tvHeader.setText("Select the synonym that correctly matches the word");
        }else{
            tvHeader.setText("Select the definition that correctly matches the word");
        }
    }

    private ArrayList<DictionaryItem> convertListToItems(ArrayList<String> list){
        //takes in an arraylist of strings and converts it to an arraylist of DictionaryItems
        //not sure if this is possible really
        return null;
    }
}