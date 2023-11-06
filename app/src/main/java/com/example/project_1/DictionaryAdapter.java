package com.example.project_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

public class DictionaryAdapter extends BaseAdapter {
    ArrayList<DictionaryItem> words;
    Context context;

    public DictionaryAdapter(ArrayList<DictionaryItem> words, Context context) {
        this.words = words;
        this.context = context;
    }

    @Override
    public int getCount() {
        return words.size();
    }

    @Override
    public Object getItem(int i) {
        return words.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.layout_dictionary, viewGroup, false);
        TextView tvWord = view.findViewById(R.id.tvWord);
        TextView tvNumber = view.findViewById(R.id.tvNumber);
        RadioButton rdoFavorite = view.findViewById(R.id.rdoFavorite);
        DictionaryItem d = words.get(position);
        tvWord.setText(d.getWord());
        tvNumber.setText(position);
        rdoFavorite.setChecked(false);

        return view;
    }
}
