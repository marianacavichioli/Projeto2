package com.mobile2.projeto2.activities.syllable_activity;

import android.annotation.SuppressLint;
import android.net.Uri;

import com.mobile2.projeto2.entity.Syllable;
import com.mobile2.projeto2.entity.Word;
import com.mobile2.projeto2.repository.GeneralRepository;
import com.mobile2.projeto2.util.SyllableList;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by cesar on 5/6/2018.
 */

public class SyllableActivityPresenter implements SyllableActivityInterface.Presenter {

    private SyllableActivityInterface.View mView;
    private Word mWord;
    private List<Syllable> mInGameSyllables = new ArrayList<>();

    public SyllableActivityPresenter(SyllableActivityInterface.View view) {
        this.mView = view;
    }

    @SuppressLint("CheckResult")
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
        int numberOfIncorrectAlternatives = numberOfAlternatives - mWord.syllableCount();

        mInGameSyllables.addAll(mWord.getSyllables());

        for (int i = 0; i < numberOfIncorrectAlternatives; i++) {
            Syllable randomSyllable = SyllableList.getRandomSyllable();
            if (!mInGameSyllables.contains(randomSyllable)) {
                mInGameSyllables.add(randomSyllable);
            }
        }

        Collections.shuffle(mInGameSyllables);
        for (Syllable mInGameSyllable : mInGameSyllables) {
            if (mWord.containsSyllable(mInGameSyllable)) {
                mView.addRightButton(mInGameSyllable);
            } else {
                mView.addWrongButton(mInGameSyllable);
            }
        }
    }
}
