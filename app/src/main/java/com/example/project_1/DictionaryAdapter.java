package com.example.project_1;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.*;

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.DictionaryViewHolder> {
    ArrayList<DictionaryItem> words;
    Context context;

    public DictionaryAdapter(ArrayList<DictionaryItem> words, Context context) {
        this.words = words;
        this.context = context;
    }

    @NonNull
    @Override
    public DictionaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_dictionary,parent,false);
        return new DictionaryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DictionaryViewHolder holder, int position) {
        DictionaryItem dictionaryItem = words.get(position);


        holder.tvWord.setText(dictionaryItem.getWord());
        holder.tvDefinition.setText(dictionaryItem.getDefinition());
        holder.rdoFavorite.setChecked(dictionaryItem.favorite);

        //help
        // AHHHHHHHHHHH
        holder.rdoFavorite.setOnClickListener(e->{
            if (holder.rdoFavorite.isChecked()) {
                dictionaryItem.setFavorite(true);
            }
            else {
                dictionaryItem.setFavorite(false);
            }
        });

    }
    @Override
    public int getItemCount() {
        return words.size();
    }

//    @Override
//    public View getView(int position, View view, ViewGroup viewGroup) {
//        view = LayoutInflater.from(context).inflate(R.layout.layout_dictionary, viewGroup, false);
//        TextView tvWord = view.findViewById(R.id.tvWord);
//        TextView tvNumber = view.findViewById(R.id.tvNumber);
//        RadioButton rdoFavorite = view.findViewById(R.id.rdoFavorite);
//        DictionaryItem d = words.get(position);
//        tvWord.setText(d.getWord());
//        tvNumber.setText(position);
//        rdoFavorite.setChecked(false);
//
//        return view;
//    }

    class DictionaryViewHolder extends RecyclerView.ViewHolder {
        TextView tvWord;
        TextView tvDefinition;
        RadioButton rdoFavorite;
        Button btnImage;
        public DictionaryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvWord = itemView.findViewById(R.id.tvWord);
            tvDefinition = itemView.findViewById(R.id.tvDefinition);
            rdoFavorite = itemView.findViewById(R.id.rdoFavorite);
        }
    }
}
