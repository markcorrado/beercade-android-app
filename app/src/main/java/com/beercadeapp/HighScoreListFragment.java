package com.beercadeapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.beercadeapp.api.HighScoreService;
import com.beercadeapp.api.ServiceGenerator;
import com.beercadeapp.model.HighScore;
import com.beercadeapp.model.HighScoreResult;
import com.github.clans.fab.FloatingActionButton;
import com.parse.ParseFile;
import com.parse.ParseQueryAdapter;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class HighScoreListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private List<HighScore>mHighScores;
    private ListView mListView;
    private FloatingActionButton mAddButton;
    private ParseQueryAdapter<HighScore> mHighScoreParseQueryAdapter;
    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types of parameters
    public static HighScoreListFragment newInstance() {
        HighScoreListFragment fragment = new HighScoreListFragment();
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HighScoreListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recycler_view_with_fab, container, false);
        mListView = (ListView) v.findViewById(R.id.recycler_view);
        mAddButton = (FloatingActionButton) v.findViewById(R.id.fab);
//        mListView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                AddHighScoreFragment fragment = new AddHighScoreFragment();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        mHighScoreParseQueryAdapter = new HighScoreParseAdapter(getActivity());
        mListView.setAdapter(mHighScoreParseQueryAdapter);
//        HighScoreService highScoreService = ServiceGenerator.createService(HighScoreService.class, getString(R.string.BASE_URL));
//
//        Call<HighScoreResult> call = highScoreService.listHighScores();
//        call.enqueue(new Callback<HighScoreResult>() {
//            @Override
//            public void onResponse(Response<HighScoreResult> response, Retrofit retrofit) {
//                mHighScores = response.body().highScores;
//                mHighScoreParseQueryAdapter = new HighScoreParseAdapter(getActivity());
//                mListView.setAdapter(mHighScoreParseQueryAdapter);
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                Toast.makeText(getActivity(), "FAILED", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

//    @Override
//    public void onListItemClick(ListView l, View v, int position, long id) {
//        super.onListItemClick(l, v, position, id);
//
//        if (null != mListener) {
//            // Notify the active callbacks interface (the activity, if the
//            // fragment is attached to one) that an item has been selected.
////            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) context).onSectionAttached(1);
    }

    //    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        ((MainActivity) activity).onSectionAttached(
//                getArguments().getInt(ARG_SECTION_NUMBER));
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

    private class HighScoreHolder extends RecyclerView.ViewHolder {
        private HighScore mHighScore;
        private ImageView mImageView;
        private TextView mGameTitleTextView;
        private TextView mInitialsTextView;
        private TextView mPlayerNameTextView;
        private TextView mScoreTextView;

        public HighScoreHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.icon);
            mGameTitleTextView = (TextView) itemView.findViewById(R.id.game_title_text);
            mInitialsTextView = (TextView) itemView.findViewById(R.id.initials_text);
            mPlayerNameTextView = (TextView) itemView.findViewById(R.id.player_name_text);
            mScoreTextView = (TextView) itemView.findViewById(R.id.score_text);
        }

        public void bindHighScore(HighScore highScore) {
            mHighScore = highScore;
            ParseFile photoFile = mHighScore.getPhotoFile();
            mGameTitleTextView.setText(mHighScore.getGameTitle());
            mInitialsTextView.setText(mHighScore.getInitials());
            mPlayerNameTextView.setText(mHighScore.getPlayerName());
            mScoreTextView.setText(String.valueOf(mHighScore.getScore()));
        }
    }

    private class HighScoreAdapter extends RecyclerView.Adapter<HighScoreHolder> {
        @Override
        public HighScoreHolder onCreateViewHolder(ViewGroup parent, int pos) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_high_score, parent, false);
            return new HighScoreHolder(view);
        }

        @Override
        public void onBindViewHolder(HighScoreHolder holder, int pos) {
            HighScore highScore = mHighScores.get(pos);
            holder.bindHighScore(highScore);
        }

        @Override
        public int getItemCount() {
            return mHighScores.size();
        }
    }

}
