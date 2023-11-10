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

public class Dictionary {

    static ArrayList<DictionaryItem> dictionary;

    public static void createDictionary(){

        dictionary = new ArrayList<>();

     // check if length over 3

     // use api to check defintion

     // use api to check synomymmgsodg

     // if passes all three of those add to list

    }

    public static ArrayList<DictionaryItem> getDictionary() {
        return dictionary;
    }
}
