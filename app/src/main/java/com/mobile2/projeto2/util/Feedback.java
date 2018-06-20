package com.mobile2.projeto2.util;

/**
 * Created by cesar.andrade on 20/06/18.
 */

public class Feedback{
    String word;
    Constans.ActType type;
    int missCounter;

    public Feedback(String word, Constans.ActType type, int missCounter) {
        this.word = word;
        this.type = type;
        this.missCounter = missCounter;
    }
}