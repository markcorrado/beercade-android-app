package com.beercadeapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.beercadeapp.model.HighScore;
import com.github.clans.fab.FloatingActionButton;
import com.parse.ParseQueryAdapter;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class HighScoreListFragment extends Fragment {

    private List<HighScore>mHighScores;
    private ListView mListView;
    private FloatingActionButton mAddButton;
    private ParseQueryAdapter<HighScore> mHighScoreParseQueryAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private OnFragmentInteractionListener mListener;

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
        View v = inflater.inflate(R.layout.listview_with_fab, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.main_swipe_refresh_layout);
        mListView = (ListView) v.findViewById(R.id.list_view);
        mAddButton = (FloatingActionButton) v.findViewById(R.id.fab);

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

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadGameList();
            }
        });
        return v;
    }

    private void loadGameList() {
        ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });

        if(isConnected) {
            mHighScoreParseQueryAdapter.loadObjects();
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getActivity(), getString(R.string.connection_error), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mHighScoreParseQueryAdapter = new HighScoreParseAdapter(getActivity());
        mHighScoreParseQueryAdapter.setAutoload(false);
        mHighScoreParseQueryAdapter.addOnQueryLoadListener(new ParseQueryAdapter.OnQueryLoadListener<HighScore>() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onLoaded(List<HighScore> objects, Exception e) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        mListView.setAdapter(mHighScoreParseQueryAdapter);
        loadGameList();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) context).onSectionAttached(1);
    }

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
}
