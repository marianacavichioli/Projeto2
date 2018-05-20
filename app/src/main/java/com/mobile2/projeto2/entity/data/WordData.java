package com.mobile2.projeto2.entity.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by cesar on 4/21/2018.
 */

@Entity
public class WordData {
    @NonNull @PrimaryKey
    private String word;
    private String imageFilePath;
    private String videoFilePath;

    //public WordData(String word) {
    //    this.word = word;
    //}

    public WordData(String word, String imageFilePath) {
        this.word = word;
        this.imageFilePath = imageFilePath;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public String getVideoFilePath() {
        return videoFilePath;
    }

    public void setVideoFilePath(String videoFilePath) {
        this.videoFilePath = videoFilePath;
    }
}
