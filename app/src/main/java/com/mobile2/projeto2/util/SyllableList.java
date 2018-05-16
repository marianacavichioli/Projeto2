package com.mobile2.projeto2.util;

import com.mobile2.projeto2.entity.Syllable;

import java.util.Random;

/**
 * Created by cesar on 4/22/2018.
 */

public class SyllableList {
    private static String[] syl = {
            "a", "e", "i", "o", "u",
            "pa", "pe", "pi", "po", "pu",
            "ba", "be", "bi", "bo", "bu",
            "ca", "ce", "ci", "co", "cu",
            "ma", "me", "mi", "mo", "mu",
            "na", "ne", "ni", "no", "nu",
            "la", "le", "li", "lo", "lu",
            "da", "de", "di", "do", "du",
            "fa", "fe", "fi", "fo", "fu",
            "ga", "ge", "gi", "go", "gu",
            "ja", "je", "ji", "jo", "ju",
            "que",
            "ra", "re", "ri", "ro", "ru",
            "sa", "se", "si", "so", "so",
            "ta", "te", "ti", "to", "tu",
            "va", "ve", "vi", "vo", "vu",
            "bra", "sil",
            "tar", "ga",
            "gue"
    };

    public static String[] getSyl() {
        return syl;
    }

    public static Syllable getRandomSyllable() {
        int random = new Random().nextInt(syl.length);
        return new Syllable(syl[random]);
    }
}
