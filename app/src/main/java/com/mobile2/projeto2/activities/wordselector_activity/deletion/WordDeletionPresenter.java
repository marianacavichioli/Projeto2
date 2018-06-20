package com.mobile2.projeto2.activities.wordselector_activity.deletion;

import android.annotation.SuppressLint;

import com.mobile2.projeto2.entity.Word;
import com.mobile2.projeto2.repository.GeneralRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WordDeletionPresenter implements WordDeletionInterface.Presenter {
    WordDeletionInterface.View mView;
    List<Word> wordList = new ArrayList<>();

    public WordDeletionPresenter(WordDeletionInterface.View mView) {
        this.mView = mView;
    }

    @SuppressLint("CheckResult")
    @Override
    public void fetchWordList() {
        GeneralRepository.getAllWords()
                .subscribe(wordList1 -> {
                    this.wordList = wordList1;
                    mView.setWordList(wordList);
                }, throwable -> {
                    throwable.printStackTrace();
                    mView.onError(throwable.getMessage());
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void deleteImageWords(List<Word> words) {
        GeneralRepository.deleteImageFromWords(words)
                .subscribe(() -> {
                    for (Word word : wordList) {
                        if (isValid(word.getImageFilePath())) {
                            File imageFile = new File(word.getImageFilePath());
                            if (imageFile.exists()) {
                                imageFile.delete();
                            }
                        }
                    }
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void deleteVideoWords(List<Word> words) {
        GeneralRepository.deleteVideoFromWords(words)
                .subscribe(() -> {
                    for (Word word : wordList) {
                        if (isValid(word.getVideoFilePath())) {
                            File videoFile = new File(word.getVideoFilePath());
                            if (videoFile.exists()) {
                                videoFile.delete();
                            }
                        }
                    }
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void deleteWords(List<Word> words) {
        GeneralRepository.deleteWords(words)
                .subscribe(() -> {
                    for (Word word : wordList) {
                        if (isValid(word.getImageFilePath())) {
                            File imageFile = new File(word.getImageFilePath());
                            if (imageFile.exists()) {
                                imageFile.delete();
                            }
                        }
                        if (isValid(word.getVideoFilePath())) {
                            File videoFile = new File(word.getVideoFilePath());
                            if (videoFile.exists()) {
                                videoFile.delete();
                            }
                        }
                    }
                    mView.onWordsDeleted();
                },Throwable::printStackTrace);
    }

    private boolean isValid(String s){
        return s != null && !s.isEmpty();
    }
}
