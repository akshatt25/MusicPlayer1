package com.example.musicplayer1;

import android.media.Image;

import java.io.Serializable;

public class AudioModel implements Serializable {
//serializable so it can be passed to another activity
    String path;
    String title;
    String duration;
    public AudioModel(String path, String title, String duration ) {
        this.path = path;
        this.title = title;
        this.duration = duration;
    }

    public AudioModel() {

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }


}
