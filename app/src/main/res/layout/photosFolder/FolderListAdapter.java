package com.jeffreymphahlele.jeffdemo.photosFolder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jeffreymphahlele.jeffdemo.R;
import com.jeffreymphahlele.jeffdemo.models.Photo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FolderListAdapter extends RecyclerView.Adapter<com.jeffreymphahlele.jeffdemo.photosFolder.FolderListAdapter.ViewHolder> {

    private Map<String, List<Photo>> photoMapOfArrayList;
    private Context context;
    private List<String> foldersList=new ArrayList<>();

    public FolderListAdapter(Context context, Map<String, List<Photo>> photoMapOfArrayList) {
         this.photoMapOfArrayList= photoMapOfArrayList;
         this.context=context;
         foldersList.addAll(photoMapOfArrayList.keySet());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View card= LayoutInflater.from(parent.getContext()).inflate(R.layout.folder_card,parent,false);
        return new ViewHolder(card) ;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.folder_name.setText(foldersList.get(position));
        holder.view.setOnClickListener(v -> openGridViewActivity(position));
    }

    private void openGridViewActivity(int position) {
        List<Photo> photosList=photoMapOfArrayList.get(foldersList.get(position));
        Gson gson = new Gson();
        String jsonString = gson.toJson(photosList);
        Intent i=new Intent(context, com.jeffreymphahlele.jeffdemo.photoGridView.PhotosGridViewActivity.class);
        i.putExtra("data", jsonString );
        context.startActivity(i);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.folder_name)
        TextView folder_name;
        @BindView(R.id.cardView)
        public View view;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    @Override
    public int getItemCount() {
        return foldersList.size();
    }


}

