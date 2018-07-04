package com.mobile2.projeto2.activities.syllable_activity;

import android.net.Uri;

import com.mobile2.projeto2.entity.Syllable;
import com.mobile2.projeto2.entity.Word;

import java.util.List;

/**
 * Created by cesar on 5/6/2018.
 */

public interface SyllableActivityInterface {

    public interface View {
        void addRightButton(Syllable syllable);

        void addWrongButton(Syllable syllable);

        void setWord(Word word);

        void setAsset(Uri uri);

        void onError(String message);

        void preAnswerSyllables(List<Integer> indexes);
    }

    public interface Presenter {
        void fetchWord(String wordString);
    }
}
