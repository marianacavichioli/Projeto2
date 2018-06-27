package com.mobile2.projeto2.entity.data.roomjoins;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.mobile2.projeto2.entity.data.SyllableData;
import com.mobile2.projeto2.entity.data.WordData;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by cesar on 4/21/2018.
 */

@Entity(
        foreignKeys = {@ForeignKey(entity = WordData.class, parentColumns = "word", childColumns = "word", onDelete = CASCADE),
                         @ForeignKey(entity = SyllableData.class, parentColumns = "syllable", childColumns = "syllable", onDelete = CASCADE)}
                         )
public class SyllablesFromWord {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @NonNull public final String word;
    @NonNull public final String syllable;
    public int position;

    public SyllablesFromWord(String word, String syllable, int position) {
        this.word = word;
        this.syllable = syllable;
        this.position = position;
    }
}
