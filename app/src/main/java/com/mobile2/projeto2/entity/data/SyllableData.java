package com.mobile2.projeto2.entity.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by cesar on 4/21/2018.
 */

@Entity
public class SyllableData {
    @NonNull @PrimaryKey
    private String syllable;
    private String videoFilePath;

    public SyllableData(String syllable) {
        this.syllable = syllable;
    }

    public String getSyllable() {
        return syllable;
    }

    public void setSyllable(String syllable) {
        this.syllable = syllable;
    }

    public String getVideoFilePath() {
        return videoFilePath;
    }

    public void setVideoFilePath(String videoFilePath) {
        this.videoFilePath = videoFilePath;
    }
}