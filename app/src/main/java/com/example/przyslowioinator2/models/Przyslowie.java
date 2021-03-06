package com.example.przyslowioinator2.models;

import com.example.przyslowioinator2.utils.FavouritesUtils;
import com.example.przyslowioinator2.utils.PrzyslowiaUtils;

import java.util.Comparator;

public class Przyslowie {
    private final int id;
    private final String text;
    private final String formattedText;
    private final String wiktionaryLink;

    private boolean expanded;

    public Przyslowie(int id, String text) {
        this.id = id;
        this.text = text;
        this.formattedText = Character.toUpperCase(text.charAt(0)) + text.substring(1) + ".";
        this.wiktionaryLink = "https://pl.wiktionary.org/wiki/" + text.replace(' ', '_') + "#pl";
        this.expanded = false;
    }

    public int getId(){
        return id;
    }

    public String getText(){
        return text;
    }

    public String getWiktionaryLink() {
        return wiktionaryLink;
    }

    public String getFormattedText() {
        return formattedText;
    }

    public boolean isExpanded(){
        return expanded;
    }

    public void setExpanded(boolean expanded){
        this.expanded = expanded;
    }

    public static class PrzyslowieComparator implements Comparator<Przyslowie>
    {
        @Override
        public int compare(Przyslowie o1, Przyslowie o2) {
            return o1.getFormattedText().compareToIgnoreCase(o2.getFormattedText());
        }
    }
}
