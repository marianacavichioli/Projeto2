package com.mobile2.projeto2.entity.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by cesar on 4/21/2018.
 */

@Entity
public class SyllableData {
    @PrimaryKey
    String syllable;

    public SyllableData(String syllable) {
        this.syllable = syllable;
    }

    public String getSyllable() {
        return syllable;
    }
}