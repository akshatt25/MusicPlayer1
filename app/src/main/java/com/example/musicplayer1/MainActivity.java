package com.example.musicplayer1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.Manifest;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView NoSongs;
    TextView text;
    ArrayList<AudioModel> SongList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        NoSongs = findViewById(R.id.textView2);

        getSupportActionBar().hide();
        //permission given or not
        if(checkPermission()==false)
        {
            requestPermission();
            return;
        }
        //mediaStore this media provider provides indexed collection of all media types from storage device
        //cursor ka kaam cursor k liye string[] , string creation
        String[] projection ={MediaStore.Audio.Media.DATA,
        MediaStore.Audio.Media.DURATION,
        MediaStore.Audio.Media.TITLE};



        String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";
        //cursor provides read and write acsses to result set returned by database query
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI , projection , selection , null , null
        );
        //cursor is reading our data ,this data needs to be stored in an arraylist ,the data stored will have 3 properties
        //as defined in projection ,so we create AudioModel
        //created an ArrrayList - SongList
        //crating obj of AudioModel
        //AudioModel SongData = new AudioModel(cursor.getString(0), cursor.getString(2), cursor.getString(1));
        //ab jitni bhi files hongi wo sb cursor me awr ab cursor ka hr data ek ek krke songData me ayega toh loop

        while(cursor.moveToNext())
        {
            AudioModel SongData = new AudioModel(cursor.getString(0), cursor.getString(2), cursor.getString(1) );
            if(new File(SongData.getPath()).exists())
            {
                SongList.add(SongData);
            }
        }

        //No Song Found
        if(SongList.size()==0)
        {
            NoSongs.setVisibility(View.VISIBLE);
        }
        //setting RecyclerView with Adapter and Layout
        //creating MusicAdapter and MyLayout
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MusicAdapter(SongList , getApplicationContext()));
    }
    //permission request 2 ways one clear and one if denied then text shown
    void requestPermission()
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this ,Manifest.permission.READ_EXTERNAL_STORAGE))
        {
            Toast.makeText(this, "Give Permission to App From Settings", Toast.LENGTH_SHORT).show();
        }

        else
        {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);

        }
    }
    boolean checkPermission()
    {
        int result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(result== PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }
        else
            return false;
    }
}