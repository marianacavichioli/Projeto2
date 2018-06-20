package com.mobile2.projeto2.entity.abstracts;

import android.support.annotation.Nullable;

import com.mobile2.projeto2.entity.Syllable;
import com.mobile2.projeto2.entity.data.SyllableData;
import com.mobile2.projeto2.entity.data.WordData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cesar on 4/21/2018.
 */

public abstract class AbstractWord {
    private WordData mWord;
    private List<Syllable> mSyllables = new ArrayList<>();


    public AbstractWord(@Nullable String imageFilePath, @Nullable String videoFilePath, String... syllables) {
        for (String syllable : syllables) {
            mSyllables.add(new Syllable(syllable));
        }
        this.mWord = new WordData(generateWord(mSyllables),imageFilePath, videoFilePath);
    }

    public AbstractWord(@Nullable String imageFilePath, @Nullable String videoFilePath, String word) {
        this.mWord = new WordData(word,imageFilePath, videoFilePath);
    }

    public AbstractWord(WordData wordData, List<SyllableData> syllables) {
        for (SyllableData syllable : syllables) {
            mSyllables.add(new Syllable(syllable));
        }
        this.mWord = wordData;
    }

    public String getImageFilePath(){ return mWord.getImageFilePath(); }

    public String getVideoFilePath(){ return mWord.getVideoFilePath(); }

    public WordData getData() {
        return mWord;
    }

    private String generateWord(List<Syllable> syllables) {
        String word = "";
        for (Syllable syllable : syllables) {
            word += syllable.toString();
        }
        return word;
    }

    public List<Syllable> getSyllables() {
        return mSyllables;
    }

    public int syllableCount(){
        return mSyllables.size();
    }

    public boolean containsSyllable(Syllable syllable){
        return mWord.getWord().contains(syllable.toString());
    }

    public Syllable getSyllableAt(int position) {
        if (mSyllables.size() >= position - 1) {
            return mSyllables.get(position);
        } else {
            return new Syllable("");
        }
    }

    @Override
    public String toString() {
        return mWord.getWord();
    }
}
