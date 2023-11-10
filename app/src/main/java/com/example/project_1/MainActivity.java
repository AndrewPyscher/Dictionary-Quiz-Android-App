package com.example.project_1;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity {
    Button btnSettings;

    // arraylist to store all of the indexes of words that were used
    ArrayList<Integer> nums;
    // listOfWords is an array list of everyword
    // selectedWords is an arraylist of 100 random words
    ArrayList<String> listOfWords, selectedWords;
    Button btnQuiz, btnBrowseWords;
    Button btnApi1, btnApi2;
    ExecutorService executorService;
    static RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSettings = findViewById(R.id.btnSettings);
        btnQuiz = findViewById(R.id.btnQuiz);
        btnBrowseWords = findViewById(R.id.btnBrowseWords);
        btnApi1 = findViewById(R.id.btnApi1);
        btnApi2 = findViewById(R.id.btnApi2);

        queue =  Volley.newRequestQueue(this);
        nums = new ArrayList<>();
        listOfWords = new ArrayList<>();
        selectedWords = new ArrayList<>();

        // set up executor service for thread
        executorService = Executors.newSingleThreadExecutor();

         //start thread to populate singleton Dictionary object
        runOnUiThread(()->{
            Dictionary.createDictionary();
            readFile();

        });

        // listener that uses an implicit intent to take the user to the dictionary api page
        btnApi1.setOnClickListener(e->{
            Uri uri = Uri.parse("https://dictionaryapi.dev/");
            Intent it = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(it);
        });
        // listener that uses an implicit intent to take the user to the synonym api page
        btnApi2.setOnClickListener(e->{
            Uri uri = Uri.parse("https://api-ninjas.com/api/thesaurus");
            Intent it = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(it);
        });



        // set listener for browse words. There is a check to wait until there is 10 words loaded from the api
        // before you can launch the activity.
        btnBrowseWords.setOnClickListener(e-> {
            if(Dictionary.dictionary.size() >= 10) {
                Intent intent = new Intent(this, BrowseWords.class);
                ArrayList<String> wordListAsStrings = new ArrayList<>();
                for (DictionaryItem dictionaryItem : Dictionary.getDictionary()) {
                    wordListAsStrings.add(dictionaryItem.getWord() + ","
                            + dictionaryItem.getDefinition());
                }

                startActivity(intent);
            } else {
                Toast.makeText(this, "Please wait a few seconds and try again...", Toast.LENGTH_LONG).show();
            }
        });

        btnSettings.setOnClickListener(e->{
            //to be set later when browse words activity is created
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        });

        // set listener for the quiz. There is a check to wait until there is 10 words loaded from the api
        // before you can launch the activity.
        btnQuiz.setOnClickListener(e->{
            if(Dictionary.dictionary.size() >= 10){
                Intent quiz = new Intent(this, Quiz.class);
                startActivity(quiz);
            }else{
                Toast.makeText(this, "Please wait a few seconds and try again...", Toast.LENGTH_LONG).show();
            }
        });

        SharedPreferences sharedPref = getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE);
        Boolean darkModeSetting = sharedPref.getBoolean("DARK_MODE", false);
        if(darkModeSetting)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    // method to read file
    // takes in a list of words and checks if its at least 3 characters
    // after the file is read in, randomly select 100 words to add to the dictionary
    public void readFile(){
        InputStream is = getResources().openRawResource(R.raw.words);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try {
            String line = br.readLine();
            while(line!=null){
                // only add words with at least 3 characters
                if(line.length() > 2 ){
                    listOfWords.add(line);
                }
                line = br.readLine();
            }
            // select 100 random words
            Random r = new Random();
            int random;
            while(selectedWords.size() < 100){
                random = r.nextInt(listOfWords.size() -1);
                // make sure the same word isn't selected twice
                if(!nums.contains(random)){
                    nums.add(random);
                    selectedWords.add(listOfWords.get(random));
                    String word = listOfWords.get(random);

                    getDef(word);

                }
            }

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    // method that calls an api to get the definition for a words
    public static void getDef(String word){
            try {
                String url = "https://api.dictionaryapi.dev/api/v2/entries/en/" + word;
                JsonArrayRequest r = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
                    try {
                        // convert the response into json and get the definition from it.
                        JSONObject arr = response.getJSONObject(0);
                        JSONArray meaning = arr.getJSONArray("meanings");
                        JSONObject first = meaning.getJSONObject(0);
                        JSONArray definitions = first.getJSONArray("definitions");
                        JSONObject def = definitions.getJSONObject(0);
                        String definition = def.getString("definition");
                        // if the word has a definition, call the synonym api
                        getSyn(word, definition);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    // if the word doesn't have a definition.
                }, error -> {
                    Log.d("error", "error: " + Arrays.toString(error.getStackTrace()));
                });

                queue.add(r);

            }catch (Exception e){

            }
    }
    // method that calls an synonym to get the definition for a words
    public static void getSyn(String word, String definition){
        try {
            String url = "https://api.api-ninjas.com/v1/thesaurus?word=" + word + "&X-Api-Key=eyXZ3Gk1kOSWZbvIczcYhSwZMrf2ht9UpplA2syl";
            String finalDefinition = definition;
            JsonObjectRequest r = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
                try {
                    // convert the response into json and get the synonym from it.
                    JSONArray synonyms = response.getJSONArray("synonyms");
                    ArrayList<String> temp = new ArrayList<>();
                    // if the word has at least 3 synonyms, add it to the dictionary
                    // if not, do not add it
                    if(synonyms.length() > 2) {
                        temp.add(synonyms.getString(0));
                        temp.add(synonyms.getString(1));
                        temp.add(synonyms.getString(2));
                        Dictionary.dictionary.add(new DictionaryItem(word, finalDefinition, temp, false));
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }, error -> {
            });

            queue.add(r);

        }catch (Exception e){
        }
    }
}
