package com.example.project_1;

import java.util.ArrayList;

public class DictionaryItem {
    String word;

    String definition;
    ArrayList<String> synonyms;
    boolean favorite;

    public DictionaryItem(String word, String definition, ArrayList<String> synonyms, boolean favorite) {
        this.word = word;
        this.definition = definition;
        this.synonyms = synonyms;
        this.favorite = favorite;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinitions(String definition) {
        this.definition = definition;
    }

    public ArrayList<String> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(ArrayList<String> synonyms) {
        this.synonyms = synonyms;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
