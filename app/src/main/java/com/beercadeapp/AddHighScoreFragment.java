package com.beercadeapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddHighScoreFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddHighScoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddHighScoreFragment extends Fragment {
    static final int REQUEST_TAKE_PHOTO = 1;

    private ImageButton mImageButton;
    private EditText mGameTitleText;
    private EditText mInitialsTitleText;
    private EditText mPlayerNameTitleText;
    private EditText mHighScoreTitleText;
    private Button mSendEmailButton;
    String mCurrentPhotoPath = "";

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddHighScoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddHighScoreFragment newInstance() {
        AddHighScoreFragment fragment = new AddHighScoreFragment();
        return fragment;
    }

    public AddHighScoreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_high_score, container, false);
        mImageButton = (ImageButton) v.findViewById(R.id.image_preview);
        mSendEmailButton = (Button) v.findViewById(R.id.send_email_button);
        mGameTitleText = (EditText) v.findViewById(R.id.game_title_text);
        mInitialsTitleText = (EditText) v.findViewById(R.id.initials_text);
        mPlayerNameTitleText = (EditText) v.findViewById(R.id.player_name_text);
        mHighScoreTitleText = (EditText) v.findViewById(R.id.score_text);

        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        mSendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmailIntent();
            }
        });
        return v;
    }

    private void sendEmailIntent() {
        if(mGameTitleText.getText().toString().isEmpty() ||
                mInitialsTitleText.getText().toString().isEmpty() ||
                mPlayerNameTitleText.getText().toString().isEmpty() ||
                mHighScoreTitleText.getText().toString().isEmpty() ||
                mCurrentPhotoPath.isEmpty()) {
            Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, "beercade@beercadeemail.com");
            intent.putExtra(Intent.EXTRA_SUBJECT, "High Score for: " + mGameTitleText.getText().toString());
            String bodyText = "Hello! I, " + mInitialsTitleText.getText().toString() + " " + mPlayerNameTitleText.getText().toString() + " achieved " +
                    mHighScoreTitleText.getText().toString() + " on " +
                    mGameTitleText.getText().toString();
            intent.putExtra(Intent.EXTRA_TEXT, bodyText);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(mCurrentPhotoPath));
            startActivity(Intent.createChooser(intent, "Send Email"));
        }
    }

    @Override
    public void onStart() {
        super.onStart();

//        Picasso.with(getActivity())
//                .load(R.drawable.ic_image_camera_alt)
////                .placeholder(R.drawable.ic_image_camera_alt)
//                .into(mImageView);

    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                //TODO: Handle the error well.
                // Error occurred while creating the File
//                ...
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == -1) {
            Picasso.with(getActivity()).load(mCurrentPhotoPath).fit().into(mImageButton);
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            mImageView.setImageBitmap(imageBitmap);
        }
    }
}
