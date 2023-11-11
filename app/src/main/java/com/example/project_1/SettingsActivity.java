package com.example.project_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {
    Spinner spinFontColor;
    Switch switchDarkMode;
    RadioGroup rdgQuizOptions;
    RadioButton rdbDefinitions, rdbSynonyms, rdbAll;
    SeekBar seekQuizLength;
    String color_options[] = {"Black","Red","Navy","Purple","Orange","Green", "White"};
    Button btnSave, btnCancel;
    TextView  txtNumQuestions;
    boolean beginningState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //initialize views
        txtNumQuestions = findViewById(R.id.txtNumQuestions);
        spinFontColor = findViewById(R.id.spinFontColor);
        seekQuizLength = findViewById(R.id.seekQuizLength);
        switchDarkMode = findViewById(R.id.switchDarkMode);
        rdgQuizOptions = findViewById(R.id.rdgQuizOptions);
        rdbAll = findViewById(R.id.rdbAll); //default checked
        rdbDefinitions = findViewById(R.id.rdbDefinitions);
        rdbSynonyms = findViewById(R.id.rdbSynonyms);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);


        //create spinner listener before intents to ensure proper behavior
        ArrayAdapter<String> colorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,color_options);
        spinFontColor.setAdapter(colorAdapter);
        spinFontColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //get intent from main activity
        Intent i = getIntent();

        //create shared preferences
        SharedPreferences sharedPref;
        sharedPref = getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE);

        //bring in previous shared preferences
         Integer fontColorSetting = sharedPref.getInt("FONT_COLOR", 0);
         spinFontColor.setSelection(fontColorSetting);



        Boolean darkModeSetting = sharedPref.getBoolean("DARK_MODE", false);
        //continue to debug this
        switchDarkMode.setChecked(darkModeSetting);
      //  beginningState = darkModeSetting;


        Integer quizQuestions = sharedPref.getInt("NUM_QUESTIONS", 10);
        seekQuizLength.setProgress(quizQuestions);
        txtNumQuestions.setText("Number of Questions: "+quizQuestions);

        Boolean all = sharedPref.getBoolean("QUIZ_MODE_ALL", true);
        rdbAll.setChecked(all);

        Boolean synonym = sharedPref.getBoolean("QUIZ_MODE_SYNONYMS", false);
        rdbSynonyms.setChecked(synonym);

        Boolean definition = sharedPref.getBoolean("QUIZ_MODE_DEFINITIONS", false);
        rdbDefinitions.setChecked(definition);

        //create new intent for going back to the main screen
        Intent intentToMain = new Intent(this, MainActivity.class );

        //create a listener for the dark mode switch
        switchDarkMode.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        //create onclick listeners for the buttons
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if user toggled the dark mode since being in settings page, but does not save the changes
                //review this IF statement after intent settings are implemented in main
                if(beginningState != switchDarkMode.isChecked()){
                    switchDarkMode.setChecked(beginningState);
                }
                startActivity(intentToMain);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(switchDarkMode.isChecked())
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                else
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                // save shared preferences
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("FONT_COLOR", spinFontColor.getSelectedItemPosition());
                editor.putBoolean("DARK_MODE", switchDarkMode.isChecked());
                editor.putBoolean("QUIZ_MODE_ALL", rdbAll.isChecked());
                editor.putBoolean("QUIZ_MODE_DEFINITIONS", rdbDefinitions.isChecked());
                editor.putBoolean("QUIZ_MODE_SYNONYMS", rdbSynonyms.isChecked());
                editor.putInt("NUM_QUESTIONS", seekQuizLength.getProgress());
                editor.apply();
                startActivity(intentToMain);
            }
        });


        //create
        seekQuizLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtNumQuestions.setText("Number of Questions: "+String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}