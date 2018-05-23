package com.mobile2.projeto2.activities.syllable_activity;

import android.net.Uri;

import com.mobile2.projeto2.entity.Syllable;
import com.mobile2.projeto2.entity.Word;
import com.mobile2.projeto2.repository.GeneralRepository;

import java.io.File;

/**
 * Created by cesar on 5/6/2018.
 */

public class SyllableActivityPresenter implements SyllableActivityInterface.Presenter {

    private SyllableActivityInterface.View mView;
    private Word mWord;

    public SyllableActivityPresenter(SyllableActivityInterface.View view) {
        this.mView = view;
    }

    @Override
    public void fetchWord(String wordString) {
        GeneralRepository.getWord(wordString)
                .subscribe(word -> {
                    this.mWord = word;
                    setupForWord();
                    mView.setWord(word);
                    mView.setImage(Uri.fromFile(new File(word.getImageFilePath())));
                }, throwable -> {
                    throwable.printStackTrace();
                    mView.onError(throwable.getMessage());
                });
    }

    private void setupForWord() {
        int numberOfAlternatives = mWord.syllableCount() * 2 + 1;
        int index = 0;

        

        for (Syllable syllable : mWord.getSyllables()) {
            mView.addRightButton(syllable, index++);
        }

        for (int i = index; i < numberOfAlternatives; i++) {
            mView.addWrongButton(new Syllable("BLA"));
        }
    }
}
