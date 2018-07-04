package com.mobile2.projeto2.util;

import com.mobile2.projeto2.R;

import java.util.Random;

public class Constans {

    public static final String EXTRA_WORD_STRING = "WORD_STRING";
    public static final String EXTRA_NEW_PIN = "NEW_PIN";

    public static final String SHAREDPREF_NAME = "com.mobile2.projeto2.SHAREDPREF";
    public static final String PASSWORD_KEY = "PASSWORD_KEY";
    public static final String EXTRA_SYLLABLE_LEVEL = "SYLLABLE_LEVEL";


    public enum ActType {
        IMAGE, VIDEO
    }

    public static int getRandomGif() {
        int random = new Random().nextInt(3);
        switch (random) {
            case 0:
                return R.drawable.cachorro;
            case 1:
                return R.drawable.menino;
            case 2:
                return R.drawable.menina;
            default:
                return R.drawable.cachorro;
        }
    }
}
