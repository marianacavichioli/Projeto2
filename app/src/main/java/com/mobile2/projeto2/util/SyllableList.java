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
            "bra", "bre", "bri", "bro", "bru",
            "bla", "ble", "bli", "blo", "blu",
            "ca", "ce", "ci", "co", "cu",
            "cra", "cre", "cri", "cro", "cru",
            "cla", "cle", "cli", "clo", "clu",
            "cha", "che", "chi", "cho", "chu",
            "ma", "me", "mi", "mo", "mu",
            "na", "ne", "ni", "no", "nu",
            "la", "le", "li", "lo", "lu",
            "da", "de", "di", "do", "du",
            "dra", "dre", "dri", "dro", "dru",
            "fa", "fe", "fi", "fo", "fu",
            "fra", "fre", "fri", "fro", "fru",
            "fla", "fle", "fli", "flo", "flu",
            "ga", "ge", "gi", "go", "gu",
            "gua", "gue",
            "gra", "gre", "gri", "gro", "gru",
            "gla", "gle", "gli", "glo", "glu",
            "ja", "je", "ji", "jo", "ju",
            "que",
            "ra", "re", "ri", "ro", "ru",
            "sa", "se", "si", "so", "so",
            "ta", "te", "ti", "to", "tu",
            "tra", "tre", "tri", "tro", "tru",
            "va", "ve", "vi", "vo", "vu",
            "vra", "vre", "vri", "vro", "vru",
            "sil",
            "tar",
    };

    public static String[] getSyl() {
        return syl;
    }

    public static Syllable getRandomSyllable() {
        int random = new Random().nextInt(syl.length);
        return new Syllable(syl[random]);
    }
}
