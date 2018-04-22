package com.mobile2.projeto2.entity.abs;

import com.mobile2.projeto2.entity.data.SyllableData;

/**
 * Created by cesar on 4/21/2018.
 */

public abstract class AbstractSyllable {

    private SyllableData syllable;

    public AbstractSyllable(String syllable) {
        this.syllable = new SyllableData(syllable);
    }

    @Override
    public String toString() {
        return syllable.getSyllable();
    }
}
