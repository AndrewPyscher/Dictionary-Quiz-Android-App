package com.example.project_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

    ArrayList<Integer> nums;
    ArrayList<String> listOfWords, selectedWords;
    Button btnQuiz, btnBrowseWords;

    ExecutorService executorService;
    static RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSettings = findViewById(R.id.btnSettings);
        btnQuiz = findViewById(R.id.btnQuiz);
        btnBrowseWords = findViewById(R.id.btnBrowseWords);


        queue =  Volley.newRequestQueue(this);
        nums = new ArrayList<>();
        listOfWords = new ArrayList<>();
        selectedWords = new ArrayList<>();


        executorService = Executors.newSingleThreadExecutor();


         //start thread
        runOnUiThread(()->{
            Dictionary.createDictionary();
            readFile();


        });

        btnSettings.setOnClickListener(e->{
            //to be set later when browse words activity is created
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        });

        btnQuiz.setOnClickListener(e->{
            if(Dictionary.dictionary.size() >= 4){
                Intent quiz = new Intent(this, Quiz.class);
                startActivity(quiz);
            }
        });

    }

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
            Random r = new Random();
            int random;
            while(selectedWords.size() < 100){
                random = r.nextInt(listOfWords.size() -1);
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

    public static void getDef(String word){
            try {
                String url = "https://api.dictionaryapi.dev/api/v2/entries/en/" + word;
                JsonArrayRequest r = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
                    try {
                        JSONObject arr = response.getJSONObject(0);
                        JSONArray meaning = arr.getJSONArray("meanings");
                        JSONObject first = meaning.getJSONObject(0);
                        JSONArray definitions = first.getJSONArray("definitions");
                        JSONObject def = definitions.getJSONObject(0);
                        String definition = def.getString("definition");

                        getSyn(word, definition);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }, error -> {
                    Log.d("error", "error: " + Arrays.toString(error.getStackTrace()));
                });

                queue.add(r);

            }catch (Exception e){

            }
    }

    public static void getSyn(String word, String definition){
        try {
            String url = "https://api.api-ninjas.com/v1/thesaurus?word="+ word + "&X-Api-Key=hIHbhVmlkuSbUvkEnuvyhg==YAsEJ2nPEgRNcOpk";
            String finalDefinition = definition;
            JsonObjectRequest r = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
                try {
                    String word1 = response.getString("word");
                    JSONArray synonyms = response.getJSONArray("synonyms");
                    ArrayList<String> temp = new ArrayList<>();
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
                Log.d("error", "error: " + Arrays.toString(error.getStackTrace()));
            });

            queue.add(r);

        }catch (Exception e){
            definition = "null";
        }
    }



}
