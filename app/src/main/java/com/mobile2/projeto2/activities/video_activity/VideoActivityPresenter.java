package com.mobile2.projeto2.activities.video_activity;

import android.annotation.SuppressLint;
import android.net.Uri;

import com.mobile2.projeto2.entity.Word;
import com.mobile2.projeto2.repository.GeneralRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class VideoActivityPresenter implements VideoActivityInterface.Presenter {
    private VideoActivityInterface.View mView;
    private Word mWord;
    private List<String> mInGameWords = new ArrayList<>();

    public VideoActivityPresenter(VideoActivityInterface.View view) {
        this.mView = view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void fetchWord(String wordString) {
        GeneralRepository.getWord(wordString)
                .subscribe(word -> {
                    this.mWord = word;
                    setupForWord();
                    mView.setAsset(Uri.fromFile(new File(word.getVideoFilePath())));
                }, throwable -> {
                    throwable.printStackTrace();
                    mView.onError(throwable.getMessage());
                });
    }

    @SuppressLint("CheckResult")
    private void setupForWord() {
        List<String> allWords =  new ArrayList<>();
        int numberOfIncorrectAlternatives = 3;

        mInGameWords.add(mWord.toString());

        GeneralRepository.getAllWords()
                .subscribe(words -> {
                    for (Word word : words) {
                        allWords.add(word.toString());
                    }

                    for (int i = 0; i < numberOfIncorrectAlternatives; i++) {
                        String randomWord = getRandomWord(allWords);
                        if (!mInGameWords.contains(randomWord)) {
                            mInGameWords.add(randomWord);
                        }
                    }

                    Collections.shuffle(mInGameWords);
                    for (String word : mInGameWords) {
                        if (word.equals(mWord.toString())) {
                            mView.addRightButton(word);
                        } else {
                            mView.addWrongButton(word);
                        }
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    mView.onError(throwable.getMessage());
                });

    }

    public static String getRandomWord(List<String> words) {
        int random = new Random().nextInt(words.size());
        return words.get(random);
    }
}
