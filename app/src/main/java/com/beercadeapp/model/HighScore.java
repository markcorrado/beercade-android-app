package com.beercadeapp.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;

/**
 * Created by markcorrado on 9/23/15.
 */
@ParseClassName("HighScore")
public class HighScore extends ParseObject {

    public HighScore() {
        // A default constructor is required.
    }

    public String getInitials() {
        return getString("initials");
    }

    public int getScore() {
        return getInt("score");
    }

    public String getGameTitle() {
        return getString("gameTitle");
    }

    public String getPlayerName() {
        return getString("playerName");
    }

    public ParseFile getPhotoFile() {
        return getParseFile("gameImage");
    }
}