package com.beercadeapp.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by markcorrado on 9/23/15.
 */
@ParseClassName("HighScore")
public class HighScore extends ParseObject {

    public HighScore() {
        // A default constructor is required.
    }

    String initials;
    int score;
    String gameTitle;
    String playerName;

    @Override
    public String toString() {
        return gameTitle + " " + initials + " " + score + "";
    }
}