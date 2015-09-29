package com.beercadeapp.api;

import com.beercadeapp.model.HighScore;
import com.beercadeapp.model.HighScoreResult;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;

/**
 * Created by markcorrado on 9/28/15.
 */
public interface HighScoreService {
    @Headers({
            "X-Parse-Application-Id: sNK0OcqdlzQ4lXuEmSfsAcHLGrGVHPVA4T2k2lmr",
            "X-Parse-REST-API-Key: Zsal1DBlmfE5s07cxQWal6xMiw6iFlUTcqmw5vNd"
    })
    @GET("classes/HighScore")
    Call<HighScoreResult> listHighScores();
}
