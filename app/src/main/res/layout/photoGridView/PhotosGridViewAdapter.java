package com.jeffreymphahlele.jeffdemo.photoGridView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.jeffreymphahlele.jeffdemo.R;

import java.util.ArrayList;
import java.util.List;


public class PhotosGridViewAdapter extends BaseAdapter {

    private Context context;
    private List<String> urlPhotoList;

    public PhotosGridViewAdapter(Context c, ArrayList<String> urlPhotoList) {
        this.context = c;
        this.urlPhotoList= urlPhotoList;
    }

    public int getCount() {
        return urlPhotoList.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView mImageView;

        if (convertView == null) {
            mImageView = new ImageView(context);
            float width = context.getResources().getDimension(R.dimen.chart_width);
            mImageView.setLayoutParams(new GridView.LayoutParams((int) width, (int) width));
            mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            mImageView = (ImageView) convertView;
        }
        com.jeffreymphahlele.jeffdemo.util.Utils.loadImage(context,mImageView,urlPhotoList.get(position));
        return mImageView;
    }


}

