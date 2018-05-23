package com.mobile2.projeto2.activities.syllable_activity;

import android.net.Uri;

import com.mobile2.projeto2.entity.Syllable;
import com.mobile2.projeto2.entity.Word;

/**
 * Created by cesar on 5/6/2018.
 */

public interface SyllableActivityInterface {

    public interface View {
        void addRightButton(Syllable syllable, int indexInTheWord);

        void addWrongButton(Syllable syllable);

        void setWord(Word word);

        void setImage(Uri uri);

        void onError(String message);
    }

    public interface Presenter {
        void fetchWord(String wordString);
    }
}
