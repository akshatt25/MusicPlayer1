package com.example.musicplayer1;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity2 extends AppCompatActivity {
    ImageButton playb;
    ImageButton prev;
    ImageButton next;
    SeekBar seekBar;
    TextView tittlet;
    TextView ont;
    TextView totalt;
    ArrayList<AudioModel> SongList;
    AudioModel currentSong;
    MediaPlayer media =MyMediaPlayer.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //3263B0
        playb=findViewById(R.id.play);
        prev=findViewById(R.id.prev);
        next=findViewById(R.id.next);
        seekBar=findViewById(R.id.seekBar);
        tittlet=findViewById(R.id.titletext);
        totalt=findViewById(R.id.totalt);
        ont=findViewById(R.id.ont);


        SongList=(ArrayList<AudioModel>)getIntent().getSerializableExtra("LIST");
        tittlet.setSelected(true);
        setResourceWithMusic();
        MainActivity2.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(media!=null)
                {
                    seekBar.setProgress(media.getCurrentPosition());
                    ont.setText(convertT(media.getCurrentPosition()+""));
                    new Handler().postDelayed(this,50);

                }
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(media!=null && fromUser)
                {
                    media.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    void setResourceWithMusic()
    {

        currentSong = SongList.get(MyMediaPlayer.currentIndex);
        tittlet.setText(currentSong.getTitle());
        totalt.setText(convertT(currentSong.getDuration()));

        playb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playpause();
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prev();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });

        playmusic();
    }
    public static String convertT(String dur)
    {
        Long millis = Long.parseLong(dur);
       return String.format("%02d:%02d",
               TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
               TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
        }
    private void playmusic()
    {
        playb.setImageResource(R.drawable.img_3);
        media.reset();
        try{
            media.setDataSource(currentSong.getPath());
            media.prepare();

            media.start();
            seekBar.setProgress(0);
            seekBar.setMax(media.getDuration());
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    private void next()
    {
        if(MyMediaPlayer.currentIndex==SongList.size()-1)
        {
            return;
        }
        MyMediaPlayer.currentIndex +=1;
        media.reset();
        setResourceWithMusic();
    }
    private void prev()
    {
        if(MyMediaPlayer.currentIndex==0){
            return;
        }
        MyMediaPlayer.currentIndex-=1;
        media.reset();
        setResourceWithMusic();

    }
    private void playpause()
    {
        if(media.isPlaying())
        {
            media.pause();
            playb.setImageResource(R.drawable.play);
        }
        else
        {
            media.start();
            playb.setImageResource(R.drawable.img_3);
        }
    }
}