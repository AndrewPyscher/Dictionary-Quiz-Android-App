package com.example.project_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {
    Spinner spinFontColor;

    Switch switchDarkMode;
    RadioGroup rdgQuizOptions;
    RadioButton rdbDefinitions, rdbSynonyms, rdbAll;
    SeekBar seekQuizLength, seekFontSize;
    String color_options[] = {"Gray, Red, Navy, Cyan, Orange, Green"};
    Button btnSave, btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        spinFontColor = findViewById(R.id.spinFontColor);
        seekFontSize = findViewById(R.id.seekFontSize);
        seekQuizLength = findViewById(R.id.seekQuizLength);
        switchDarkMode = findViewById(R.id.switchDarkMode);
        rdgQuizOptions = findViewById(R.id.rdgQuizOptions);
        rdbAll = findViewById(R.id.rdbAll); //default checked
        rdbDefinitions = findViewById(R.id.rdbDefinition);
        rdbSynonyms = findViewById(R.id.rdbSynonyms);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);

        //populate color spinner
        ArrayAdapter<String> colorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,color_options);
        spinFontColor.setAdapter(colorAdapter);
        spinFontColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //change the font color for other views by updating the shared preferences
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //create onclick listeners for the buttons
        //recieve the intent from the main activity
        //open up shared preferences from before and set everything in oncreate

        //create shared preferences
    }
}