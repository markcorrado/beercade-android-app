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

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
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