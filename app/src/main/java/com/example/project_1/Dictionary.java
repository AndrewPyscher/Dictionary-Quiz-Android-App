package com.example.project_1;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


// singleton class that makes a dictionary object that stores all the words for all activities to share
public class Dictionary {

    static ArrayList<DictionaryItem> dictionary;

    // dictionary arraylist to store all of the words
    public static void createDictionary(){
        dictionary = new ArrayList<>();
    }

    public static ArrayList<DictionaryItem> getDictionary() {
        return dictionary;
    }
}
