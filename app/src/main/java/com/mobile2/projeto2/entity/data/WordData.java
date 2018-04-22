package com.mobile2.projeto2.entity.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by cesar on 4/21/2018.
 */

@Entity
public class WordData {
    @PrimaryKey
    private String word;

    public WordData(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }
}
