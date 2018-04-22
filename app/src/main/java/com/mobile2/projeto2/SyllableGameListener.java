package com.mobile2.projeto2;

import com.mobile2.projeto2.entity.Syllable;

/**
 * Created by cesar on 4/22/2018.
 */

public interface SyllableGameListener {
    void onHitTheRightSyllable(Syllable syllable);

    void onGameWon();
}
