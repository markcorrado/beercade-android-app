package com.beercadeapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.beercadeapp.api.HighScoreService;
import com.beercadeapp.api.ServiceGenerator;
import com.beercadeapp.model.HighScore;
import com.beercadeapp.model.HighScoreResult;

import java.util.ArrayList;
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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<HighScore>mHighScores;
    private RecyclerView mRecyclerView;
    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types of parameters
    public static HighScoreListFragment newInstance() {
        HighScoreListFragment fragment = new HighScoreListFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
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

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        HighScoreService highScoreService = ServiceGenerator.createService(HighScoreService.class, getString(R.string.BASE_URL));

        Call<HighScoreResult> call = highScoreService.listHighScores();
        call.enqueue(new Callback<HighScoreResult>() {
            @Override
            public void onResponse(Response<HighScoreResult> response, Retrofit retrofit) {
                Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                mHighScores = response.body().highScores;
                mRecyclerView.setAdapter(new HighScoreAdapter());
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getActivity(), "FAILED", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recycler_view_with_fab, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mRecyclerView.setAdapter(new HighScoreAdapter());
        return v;
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
        private TextView mHighScoreTextView;

        public HighScoreHolder(View itemView) {
            super(itemView);

            mHighScoreTextView = (TextView) itemView.findViewById(R.id.high_score_text);
        }

        public void bindHighScore(HighScore highScore) {
            mHighScore = highScore;
            mHighScoreTextView.setText(mHighScore.toString());
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
