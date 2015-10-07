package com.beercadeapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beercadeapp.model.HighScore;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by markcorrado on 10/7/15.
 */
public class HighScoreParseAdapter extends ParseQueryAdapter<HighScore> {
    private DateFormat df = new SimpleDateFormat("M/dd/yyyy", Locale.US);

    public HighScoreParseAdapter(Context context) {
        super(context, new ParseQueryAdapter.QueryFactory<HighScore>() {
            public ParseQuery<HighScore> create() {
                ParseQuery query = new ParseQuery("HighScore");
                query.orderByAscending("gameTitle");
                return query;
            }
        });
    }

    @Override
    public View getItemView(HighScore highScore, View v, ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), R.layout.list_item_high_score, null);
        }

        super.getItemView(highScore, v, parent);

        ParseImageView highScoreImageView = (ParseImageView) v.findViewById(R.id.icon);
        ParseFile photoFile = highScore.getPhotoFile();
        if (photoFile != null) {
            highScoreImageView.setParseFile(photoFile);
            highScoreImageView.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    // nothing to do
                }
            });
        }

        TextView titleTextView = (TextView) v.findViewById(R.id.game_title_text);
        TextView initialsTextView = (TextView) v.findViewById(R.id.initials_text);
        TextView playerNameTextView = (TextView) v.findViewById(R.id.player_name_text);
        TextView scoreTextView = (TextView) v.findViewById(R.id.score_text);
        TextView dateTextView = (TextView) v.findViewById(R.id.date_text);

        titleTextView.setText(highScore.getGameTitle());
        initialsTextView.setText(highScore.getInitials());
        playerNameTextView.setText(highScore.getPlayerName());
        scoreTextView.setText(String.valueOf(highScore.getScore()));
        dateTextView.setText(df.format(highScore.getDatePlayed()));
        return v;
    }
}
