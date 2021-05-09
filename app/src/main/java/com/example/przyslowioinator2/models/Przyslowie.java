package com.example.przyslowioinator2.models;

public class Przyslowie {
    private final String tresc;
    private final String wiktionaryLink;

    private boolean expanded;

    public Przyslowie(String tresc) {
        this.tresc = Character.toUpperCase(tresc.charAt(0)) + tresc.substring(1) + ".";
        this.wiktionaryLink = "https://pl.wiktionary.org/wiki/" + tresc.replace(' ', '_') + "#pl";
        this.expanded = false;
    }

    public String getWiktionaryLink() {
        return wiktionaryLink;
    }

    public String getTresc() {
        return tresc;
    }

    public boolean isExpanded(){
        return expanded;
    }

    public void setExpanded(boolean expanded){
        this.expanded = expanded;
    }
}
