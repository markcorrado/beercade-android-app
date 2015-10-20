package com.beercadeapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutUsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutUsFragment extends Fragment {

    private SupportMapFragment fragment;
    private GoogleMap mMap;
    private Button mNavigateButton;

    public static AboutUsFragment newInstance() {
        AboutUsFragment fragment = new AboutUsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    public AboutUsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        fragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if (fragment == null) {
            fragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.container, fragment).commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_about_us, container, false);
        mNavigateButton = (Button) v.findViewById(R.id.navigate_button);
        mNavigateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToLocation();
            }
        });
        return v;
    }

    private void navigateToLocation() {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=41.285604, -96.007326");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            mMap = fragment.getMap();

            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(41.285604, -96.007326)).title("Beercade"));
        marker.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(41.285604, -96.007326), 16));
    }
}
