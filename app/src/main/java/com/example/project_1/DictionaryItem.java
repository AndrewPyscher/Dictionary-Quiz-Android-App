package com.example.project_1;

import java.util.ArrayList;

public class DictionaryItem {
    //string to store word
    String word;

    //string to store definition
    String definition;
    //arraylist for specific word's synonyms
    ArrayList<String> synonyms;
    //is this one of the user's favorite words
    boolean favorite;

    public DictionaryItem() {
    }

    //word & definition param constructor
    public DictionaryItem(String word, String definition) {
        this.word = word;
        this.definition = definition;
    }

    //word & definition & synonyms & favorite boolean
    public DictionaryItem(String word, String definition, ArrayList<String> synonyms, boolean favorite) {
        this.word = word;
        this.definition = definition;
        this.synonyms = synonyms;
        this.favorite = favorite;
    }

    //return word
    public String getWord() {
        return word;
    }

    //edit the word
    public void setWord(String word) {
        this.word = word;
    }

    //return word
    public String getDefinition() {
        return definition;
    }

    //edit definition
    public void setDefinitions(String definition) {
        this.definition = definition;
    }

    //return synonyms
    public ArrayList<String> getSynonyms() {
        return synonyms;
    }

    //edit synonyms
    public void setSynonyms(ArrayList<String> synonyms) {
        this.synonyms = synonyms;
    }

    //return favorite
    public boolean isFavorite() {
        return favorite;
    }

    //edit favorite
    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    //return a string that holds dictionary item
    @Override
    public String toString() {
        return "DictionaryItem{" +
                "word='" + word + '\'' +
                ", definition='" + definition + '\'' +
                ", synonyms=" + synonyms +
                ", favorite=" + favorite +
                '}';
    }
}
