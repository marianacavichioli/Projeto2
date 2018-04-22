package com.mobile2.projeto2.util;

import com.mobile2.projeto2.entity.Syllable;

import java.util.Random;

/**
 * Created by cesar on 4/22/2018.
 */

public class SyllableList {
    private static String [] syl = {"da", "do", "ze", "bra"};

    public static Syllable getRandomSyllable(){
        int random = new Random().nextInt(syl.length);
        return new Syllable(syl[random]);
    }
}
