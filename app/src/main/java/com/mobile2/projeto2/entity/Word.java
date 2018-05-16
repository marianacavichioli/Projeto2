package com.mobile2.projeto2.entity;

import com.mobile2.projeto2.entity.abstracts.AbstractWord;
import com.mobile2.projeto2.entity.data.SyllableData;
import com.mobile2.projeto2.entity.data.WordData;

import java.util.List;

/**
 * Created by cesar on 4/21/2018.
 */

public class Word extends AbstractWord {

    public Word(String... syllables) {
        super(syllables);
    }

    public Word(WordData wordData, List<SyllableData> syllables) {
        super(wordData, syllables);
    }
}
