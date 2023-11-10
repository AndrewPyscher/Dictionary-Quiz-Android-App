package com.example.project_1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BrowseWords extends AppCompatActivity {
    RecyclerView rcWordList;
    Button btnSearch;
    Spinner spnFilterOptions;
    String filter_options[] = {"alphabetical", "random", "favorites"};
    ArrayList<DictionaryItem> words;
    ArrayList<String> wordsAsStringCSV;

    EditText edtSearchWord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_words);

        rcWordList = findViewById(R.id.rcWordList);
        btnSearch = findViewById(R.id.btnSearch);
        spnFilterOptions = findViewById(R.id.spnFilterOptions);
        words = Dictionary.getDictionary();
        edtSearchWord = findViewById(R.id.edtSearchWord);

        DictionaryAdapter dictionaryAdapter = new DictionaryAdapter(words, this);
        rcWordList.setAdapter(dictionaryAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        rcWordList.setLayoutManager(manager);

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                dictionaryAdapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                words = Dictionary.getDictionary();
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

                if (spnFilterOptions.getItemAtPosition(i).toString().equalsIgnoreCase("alphabetical")) {
                    //enter logic for sorting list alphabetically
                    ArrayList<DictionaryItem> alphabeticalWords = new ArrayList<>();

                    for (int j = 0; j < words.size(); j++) {
                        alphabeticalWords.add(words.get(j));
                    }

                    Collections.sort(alphabeticalWords, Comparator.comparing(DictionaryItem::getWord));

                    DictionaryAdapter alphabeticalAdapter = new DictionaryAdapter(alphabeticalWords, getApplicationContext());
                    rcWordList.setAdapter(alphabeticalAdapter);
                }
                else if (spnFilterOptions.getItemAtPosition(i).toString().equalsIgnoreCase("favorites")) {
                    //enter logic for sorting list to only include favorites
                    ArrayList<DictionaryItem> favoriteWords = new ArrayList<>();

                    for (int j = 0; j < words.size(); j++) {
                        if (words.get(j).favorite) {
                            favoriteWords.add(words.get(j));
                        }
                    }

                    DictionaryAdapter favoriteAdapter = new DictionaryAdapter(favoriteWords, getApplicationContext());
                    rcWordList.setAdapter(favoriteAdapter);
                }
                else if (spnFilterOptions.getItemAtPosition(i).toString().equalsIgnoreCase("random")) {
                    //enter logic for sorting list randomly
                    rcWordList.setAdapter(dictionaryAdapter);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
      
        //search a word or partial word in the dictionary
        btnSearch.setOnClickListener(e->{
            //store search word
            String search = edtSearchWord.getText()+"";
            //Log.d("MYTAG", String.valueOf(words));
            //create a new arraylist for storing the search results
            ArrayList<DictionaryItem> searchedWords = new ArrayList<>();
                for (int i = 0; i < words.size(); i++) {
                   //get the word
                  if(words.get(i).word.contains(search)){
                      searchedWords.add(words.get(i)); //add to the arraylist

                  }
                }
                //create a new adapter with the results
            DictionaryAdapter searchedAdapter = new DictionaryAdapter(searchedWords, getApplicationContext());
            rcWordList.setAdapter(searchedAdapter);


            });




    }
}