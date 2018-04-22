package com.mobile2.projeto2.entity.abs;

import com.mobile2.projeto2.entity.Syllable;
import com.mobile2.projeto2.entity.interfaces.SyllableInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cesar on 4/21/2018.
 */

public abstract class AbstractWord implements SyllableInfo {
    private String mWord = "";
    private List<Syllable> mSyllables = new ArrayList<>();

    public AbstractWord(String... syllables) {
        for (String syllable : syllables) {
            mSyllables.add(new Syllable(syllable));
        }
        generateWord(mSyllables);
    }

    private void generateWord(List<Syllable> syllables) {
        for (Syllable syllable : syllables) {
            mWord += syllable.toString();
        }
    }

    public List<Syllable> getSyllables() {
        return mSyllables;
    }

    public int syllableCount(){
        return mSyllables.size();
    }

    @Override
    public boolean containsSyllable(Syllable syllable){
        return mWord.contains(syllable.toString());
    }

    @Override
    public Syllable getSyllableAt(int position) {
        if (mSyllables.size() >= position - 1) {
            return mSyllables.get(position);
        } else {
            return new Syllable("");
        }
    }

    @Override
    public String toString() {
        return mWord;
    }
}
