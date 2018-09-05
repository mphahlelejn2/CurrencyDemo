package com.jeffreymphahlele.jeffdemo.photoDetails;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jeffreymphahlele.jeffdemo.R;
import com.jeffreymphahlele.jeffdemo.models.Photo;
import com.jeffreymphahlele.jeffdemo.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PhotoDetailsFragment extends Fragment {

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.circleImage)
    ImageView circleImage;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.owner)
    TextView owner;
    private Photo photo;

    public static com.jeffreymphahlele.jeffdemo.photoDetails.PhotoDetailsFragment getInstance() {
        return new com.jeffreymphahlele.jeffdemo.photoDetails.PhotoDetailsFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        photo=getPhotoFromIntent();
        initParameters(photo);
        loadImages(photo);
    }

    private void loadImages(Photo photo ) {
        Utils.loadImage(getContext(),imageView,Utils.getUrlFromPhotoClass(photo));
        Utils.loadImage(getContext(),circleImage,Utils.getUrlFromPhotoClass(photo));
    }

    private void initParameters(Photo photo ) {
        title.setText(photo.getTitle());
        owner.setText("Owner Id : "+photo.getOwner());
    }

    private Photo getPhotoFromIntent() {
        Gson gson = new Gson();
       return  photo = gson.fromJson(getActivity().getIntent().getStringExtra("data"), Photo.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.photo_details_fragment, container, false);
    }
}
