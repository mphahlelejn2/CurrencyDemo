package com.jeffreymphahlele.jeffdemo.photoGridView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeffreymphahlele.jeffdemo.R;
import com.jeffreymphahlele.jeffdemo.models.Photo;
import com.jeffreymphahlele.jeffdemo.photoDetails.PhotoDetailsActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PhotosGridViewFragment extends Fragment {

    @BindView(R.id.gridview)
    public GridView androidGridView;
    public ArrayList<com.jeffreymphahlele.jeffdemo.models.Photo> photosList;

    public static com.jeffreymphahlele.jeffdemo.photoGridView.PhotosGridViewFragment getInstance() {
        return new com.jeffreymphahlele.jeffdemo.photoGridView.PhotosGridViewFragment();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        photosList = getPhotosListFromIntent();
        androidGridView.setAdapter(new com.jeffreymphahlele.jeffdemo.photoGridView.PhotosGridViewAdapter(getActivity(), com.jeffreymphahlele.jeffdemo.util.Utils.getUrlPhotoListFromListOfPhotoObjects(photosList)));
        androidGridView.setOnItemClickListener((parent, v, position, id) -> openIndividualImage(photosList.get(position)));
    }

    private void openIndividualImage(com.jeffreymphahlele.jeffdemo.models.Photo photo) {
        Intent i=new Intent(getContext(),PhotoDetailsActivity.class);
        Gson gson = new Gson();
        i.putExtra("data", gson.toJson(photo));
        startActivity(i);
    }

    private ArrayList<com.jeffreymphahlele.jeffdemo.models.Photo> getPhotosListFromIntent() {
        Bundle bundle = getActivity().getIntent().getExtras();
        String jsonString = bundle.getString("data");

        Gson gson = new Gson();
        Type listOfPhotoType = new TypeToken<List<Photo>>() {}.getType();
       return gson.fromJson(jsonString,listOfPhotoType );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.grid_view_fragment, container, false);
    }
}
