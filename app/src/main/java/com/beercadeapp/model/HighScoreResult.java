package com.beercadeapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by markcorrado on 9/28/15.
 */
public class HighScoreResult {
    @SerializedName(value="results")
    public List<HighScore> highScores;

    public void setHighScores(List<HighScore> highScores) {
        this.highScores = highScores;
    }
}
