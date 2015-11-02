package com.beercadeapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        HighScoreViewHolder viewHolder;

        if (v == null) {
            v = View.inflate(getContext(), R.layout.list_item_high_score, null);

            viewHolder = new HighScoreViewHolder();
            viewHolder.titleTextView = (TextView) v.findViewById(R.id.game_title_text);
            viewHolder.playerNameTextView = (TextView) v.findViewById(R.id.player_name_text);
            viewHolder.scoreTextView = (TextView) v.findViewById(R.id.score_text);
            viewHolder.dateTextView = (TextView) v.findViewById(R.id.date_text);
            viewHolder.highScoreImageView = (ParseImageView) v.findViewById(R.id.icon);
            v.setTag(viewHolder);
        } else {
            viewHolder = (HighScoreViewHolder) v.getTag();
        }

        ParseFile photoFile = highScore.getPhotoFile();
        if (photoFile != null) {
            viewHolder.highScoreImageView.setParseFile(photoFile);
            viewHolder.highScoreImageView.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    // nothing to do
                }
            });
        }

        viewHolder.titleTextView.setText(highScore.getGameTitle());
        viewHolder.playerNameTextView.setText(highScore.getPlayerName());
        viewHolder.scoreTextView.setText(String.valueOf(highScore.getScore()));
        viewHolder.dateTextView.setText(df.format(highScore.getDatePlayed()));
        return v;
    }

    static class HighScoreViewHolder {
        ParseImageView highScoreImageView;
        TextView titleTextView;
        TextView playerNameTextView;
        TextView scoreTextView;
        TextView dateTextView;
    }
}
