package com.example.project_1;

import androidx.appcompat.app.AppCompatActivity;

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
    SeekBar seekQuizLength, seekFontSize;
    String color_options[] = {"Gray, Red, Navy, Purple, Orange, Green, Black"};
    String selectedColor = "";
    Button btnSave, btnCancel;
    TextView txtFontSizeNum, txtQuestionNum;
    String fontColor = "";

    String colorHex = "";
    Integer fontSize, numQuestions;
    Boolean darkMode;

    Boolean rdbAllStatus, rdbSynonymStatus, rdbDefinitionStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //initialize views
        spinFontColor = findViewById(R.id.spinFontColor);
        seekFontSize = findViewById(R.id.seekFontSize);
        seekQuizLength = findViewById(R.id.seekQuizLength);
        switchDarkMode = findViewById(R.id.switchDarkMode);
        rdgQuizOptions = findViewById(R.id.rdgQuizOptions);
        rdbAll = findViewById(R.id.rdbAll); //default checked
        rdbDefinitions = findViewById(R.id.rdbDefinitions);
        rdbSynonyms = findViewById(R.id.rdbSynonyms);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);
        txtFontSizeNum = findViewById(R.id.txtFontSizeNum);
        txtQuestionNum = findViewById(R.id.txtQuestionNum);

        //get intent from main activity
        Intent i = getIntent();

        //create shared preferences
        SharedPreferences sharedPref;
        sharedPref = getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE);

        //bring in previous shared preferences
        String fontColorSetting = sharedPref.getString("FONT_COLOR", "#000000");
        Integer fontSizeSetting = sharedPref.getInt("FONT_SIZE", 12);

        seekFontSize.setProgress(fontSizeSetting);

        Boolean darkModeSetting = sharedPref.getBoolean("DARK_MODE", false);
        switchDarkMode.setChecked(darkModeSetting);

        Integer quizQuestions = sharedPref.getInt("QUIZ_QUESTION_PREF", 10);
        seekQuizLength.setProgress(quizQuestions);

        Boolean all = sharedPref.getBoolean("QUIZ_MODE_ALL", true);
        rdbAll.setChecked(all);

        Boolean synonym = sharedPref.getBoolean("QUIZ_MODE_SYNONYMS", false);
        rdbSynonyms.setChecked(synonym);

        Boolean definition = sharedPref.getBoolean("QUIZ_MODE_DEFINITIONS", false);
        rdbDefinitions.setChecked(definition);

        //create new intent for going back to the main screen
        Intent intentToMain = new Intent(this, MainActivity.class );

        //populate color spinner
        ArrayAdapter<String> colorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,color_options);
        spinFontColor.setAdapter(colorAdapter);
        spinFontColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //change the font color for other views by updating the shared preferences
                fontColor = spinFontColor.getSelectedItem() +"";
                if(fontColor == "Gray"){
                    colorHex = "#707B7C";
                } else if (fontColor == "Red") {
                    colorHex = "#B03A2E";
                }else if (fontColor == "Navy") {
                    colorHex = "#1A5276";
                }else if (fontColor == "Purple") {
                    colorHex = "#6C3483";
                }else if (fontColor == "Orange") {
                    colorHex = "#D35400";
                }else if (fontColor == "Green") {
                    colorHex = "#1E8449";
                }else if (fontColor == "Black") {
                    colorHex = "#000000";
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        switchDarkMode.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switchDarkMode.isChecked()){
                    darkMode = true;
                }
                else
                    darkMode = false;
            }
        });

        //create onclick listeners for the buttons
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentToMain);
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save shared preferences
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("FONT_COLOR", String.valueOf(colorHex));
                editor.putInt("FONT_SIZE", fontSize);
                editor.putBoolean("DARK_MODE", darkMode);
                editor.putBoolean("QUIZ_MODE_ALL", rdbAll.isChecked());
                editor.putBoolean("QUIZ_MODE_DEFINITIONS", rdbDefinitions.isChecked());
                editor.putBoolean("QUIZ_MODE_SYNONYMS", rdbSynonyms.isChecked());
                editor.putInt("NUM_QUESTIONS", numQuestions);
                editor.apply();
                startActivity(intentToMain);
            }
        });

        seekFontSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtFontSizeNum.setText(String.valueOf(progress));
                fontSize = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekQuizLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtQuestionNum.setText(String.valueOf(progress));
                numQuestions = progress;
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