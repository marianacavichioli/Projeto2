package com.mobile2.projeto2.entity;

import com.mobile2.projeto2.entity.abs.AbstractSyllable;

/**
 * Created by cesar on 4/21/2018.
 */

public class Syllable extends AbstractSyllable {

    public Syllable(String syllable) {
        super(syllable.toLowerCase());
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Syllable && ((Syllable) obj).toString().equals(this.toString());
    }
}
