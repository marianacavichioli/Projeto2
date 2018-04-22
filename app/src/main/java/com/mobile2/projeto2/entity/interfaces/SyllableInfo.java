package com.mobile2.projeto2.entity.interfaces;

import com.mobile2.projeto2.entity.Syllable;

/**
 * Created by cesar on 4/21/2018.
 */

public interface SyllableInfo {
    boolean containsSyllable(Syllable syllable);

    Syllable getSyllableAt(int position);
}
