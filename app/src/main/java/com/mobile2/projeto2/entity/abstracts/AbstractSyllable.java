package com.mobile2.projeto2.entity.abstracts;

import com.mobile2.projeto2.entity.data.SyllableData;

/**
 * Created by cesar on 4/21/2018.
 */

public abstract class AbstractSyllable {

    private final SyllableData syllable;

    public AbstractSyllable(String syllable) {
        this.syllable = new SyllableData(syllable);
    }

    public AbstractSyllable(SyllableData syllable) {
        this.syllable = syllable;
    }

    public SyllableData getData() {
        return syllable;
    }

    @Override
    public String toString() {
        return syllable.getSyllable();
    }
}
