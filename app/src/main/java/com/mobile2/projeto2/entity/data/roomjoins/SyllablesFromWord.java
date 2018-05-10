package com.mobile2.projeto2.entity.data.roomjoins;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

import com.mobile2.projeto2.entity.data.SyllableData;
import com.mobile2.projeto2.entity.data.WordData;

/**
 * Created by cesar on 4/21/2018.
 */

@Entity(
        primaryKeys = {"word", "syllable"},
        foreignKeys = {@ForeignKey(entity = WordData.class, parentColumns = "word", childColumns = "word"),
                         @ForeignKey(entity = SyllableData.class, parentColumns = "syllable", childColumns = "syllable")}
                         )
public class SyllablesFromWord {
    @NonNull public final String word;
    @NonNull public final String syllable;

    public SyllablesFromWord(String word, String syllable) {
        this.word = word;
        this.syllable = syllable;
    }
}
