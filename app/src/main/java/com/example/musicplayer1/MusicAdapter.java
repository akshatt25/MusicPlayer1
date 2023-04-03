package com.example.musicplayer1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder>
{
    //arraylist of audiomodel type named songlist
    ArrayList<AudioModel> SongsList;
    Context context;

    public MusicAdapter(ArrayList<AudioModel> songsList, Context context) {
        SongsList = songsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        //here , return type is view holdrer therefore viewholder class has been created and it takes view as a parameter , so a view is craeated through
        //layoutInflater and passed in view holder
        View view = LayoutInflater.from(context).inflate(R.layout.mylayout,parent,false);
        return new MusicAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        //setting the tittle for song file
        //to get title from a particular file in songlist we need a audiomodeltype obj which can hold it
        AudioModel SongData = SongsList.get(position);
        holder.textTitle.setText(SongData.getTitle());
//        int a = Integer.parseInt(SongData.getAlbumArt());
        holder.img.setBackgroundResource(R.drawable.img_5);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyMediaPlayer.getInstance().reset();
                MyMediaPlayer.currentIndex=position;
                //intent
                Intent intent = new Intent(context ,MainActivity2.class);
                intent.putExtra("LIST",SongsList);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        //song list is an array list which holds are data as audio model type
        return SongsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
         ImageView img;
         TextView textTitle;
         public ViewHolder(View itemView) {
             super(itemView);
             img = itemView.findViewById(R.id.imageView);
             textTitle = itemView.findViewById(R.id.textView);

         }
     }
}
