package com.mobile2.projeto2.entity;

import com.mobile2.projeto2.entity.abstracts.AbstractSyllable;
import com.mobile2.projeto2.entity.data.SyllableData;

/**
 * Created by cesar on 4/21/2018.
 */

public class Syllable extends AbstractSyllable {

    public Syllable(String syllable) {
        super(syllable.toLowerCase());
    }

    public Syllable(SyllableData syllable) {
        super(syllable);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Syllable && ((Syllable) obj).toString().equals(this.toString());
    }
}
