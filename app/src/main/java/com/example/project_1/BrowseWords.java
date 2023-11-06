package com.example.project_1;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class BrowseWords extends AppCompatActivity {
    RecyclerView rcWordList;
    Button btnSearch;
    Spinner spnFilterOptions;
    String filter_options[] = {"alphabetical", "random", "favorites"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_words);

        rcWordList = findViewById(R.id.rcWordList);
        btnSearch = findViewById(R.id.btnSearch);
        spnFilterOptions = findViewById(R.id.spnFilterOptions);

        ArrayAdapter<String> filterAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, filter_options);
        spnFilterOptions.setAdapter(filterAdapter);
        spnFilterOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}