package com.example.project_1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BrowseWords extends AppCompatActivity {
    RecyclerView rcWordList;
    Button btnSearch;
    Spinner spnFilterOptions;
    String filter_options[] = {"alphabetical", "random", "favorites"};
    ArrayList<DictionaryItem> words;
    ArrayList<String> wordsAsStringCSV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_words);

        rcWordList = findViewById(R.id.rcWordList);
        btnSearch = findViewById(R.id.btnSearch);
        spnFilterOptions = findViewById(R.id.spnFilterOptions);
        words = new ArrayList<>();

        Intent intent = getIntent();
        wordsAsStringCSV = intent.getStringArrayListExtra("wordList");

        for (String word : wordsAsStringCSV) {
            String[] splitValues =  word.split(",");
            words.add(new DictionaryItem(splitValues[0], splitValues[1]));
        }

        DictionaryAdapter dictionaryAdapter = new DictionaryAdapter(words, this);
        rcWordList.setAdapter(dictionaryAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        rcWordList.setLayoutManager(manager);

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                dictionaryAdapter.notifyDataSetChanged();
            }
        });
        helper.attachToRecyclerView(rcWordList);


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