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
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by cesar on 5/6/2018.
 */

public class SyllableActivityPresenter implements SyllableActivityInterface.Presenter {

    private SyllableActivityInterface.View mView;
    private int level;
    private Word mWord;
    private List<Syllable> mInGameSyllables = new ArrayList<>();
    private List<Syllable> mInGamePreAnsweredSyllables = new ArrayList<>();
    private List<Integer> mIndexAnswered = new ArrayList<>();

    public SyllableActivityPresenter(SyllableActivityInterface.View view, int level) {
        this.mView = view;
        this.level = level;
    }

    @SuppressLint("CheckResult")
    @Override
    public void fetchWord(String wordString) {
        GeneralRepository.getWord(wordString)
                .subscribe(word -> {
                    this.mWord = word;
                    mView.setWord(word);
                    setupForWord();
                    mView.setAsset(Uri.parse(word.getImageFilePath()));
                }, throwable -> {
                    throwable.printStackTrace();
                    mView.onError(throwable.getMessage());
                });
    }

    private void setupForWord() {
        int numberOfAlternatives = 9;
        int numberOfAnsweredAlternatives = (int) Math.ceil(mWord.syllableCount() / level);
        numberOfAnsweredAlternatives = numberOfAnsweredAlternatives == mWord.syllableCount() ? --numberOfAnsweredAlternatives : numberOfAnsweredAlternatives;
        int numberOfIncorrectAlternatives = numberOfAlternatives - mWord.syllableCount() + numberOfAnsweredAlternatives;


        for (int i = 0; i < numberOfAnsweredAlternatives; i++) {
            Syllable randomSyllable = getRandomSyllable();
            mInGamePreAnsweredSyllables.add(randomSyllable);
        }

        mView.preAnswerSyllables(mIndexAnswered);

        mInGameSyllables.addAll(mWord.getSyllables());
        Collections.sort(mIndexAnswered);
        Collections.reverse(mIndexAnswered);
        for (int integer : mIndexAnswered) {
            mInGameSyllables.remove(integer);
        }

        for (int i = 0; i < numberOfIncorrectAlternatives; i++) {
            Syllable randomSyllable = SyllableList.getRandomSyllable();
            if (!mInGameSyllables.contains(randomSyllable) && !mWord.containsSyllable(randomSyllable)) {
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

    private Syllable getRandomSyllable() {
        int random;
        do {
            random = new Random().nextInt(mWord.syllableCount());
        } while (mIndexAnswered.contains(random));
        mIndexAnswered.add(random);
        return mWord.getSyllableAt(random);
    }
}
