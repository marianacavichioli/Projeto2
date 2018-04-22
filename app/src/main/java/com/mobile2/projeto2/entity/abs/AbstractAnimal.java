package com.mobile2.projeto2.entity.abs;

import com.mobile2.projeto2.entity.Syllable;
import com.mobile2.projeto2.entity.Word;
import com.mobile2.projeto2.entity.interfaces.SyllableInfo;

/**
 * Created by cesar on 4/21/2018.
 */

public abstract class AbstractAnimal implements SyllableInfo {
    private Word mName;

    public AbstractAnimal(Word mName) {
        this.mName = mName;
    }

    @Override
    public boolean containsSyllable(Syllable syllable) {
        return mName.containsSyllable(syllable);
    }

    @Override
    public Syllable getSyllableAt(int position) {
        return mName.getSyllableAt(position);
    }
}
