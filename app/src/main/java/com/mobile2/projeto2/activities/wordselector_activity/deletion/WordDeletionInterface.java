package com.mobile2.projeto2.activities.wordselector_activity.deletion;

import android.annotation.SuppressLint;

import com.mobile2.projeto2.entity.Word;

import java.util.List;

import io.reactivex.Completable;

public interface WordDeletionInterface {
    interface Presenter {
        void fetchWordList();

        void deleteImageWords(List<Word> words);

        void deleteVideoWords(List<Word> words);

        @SuppressLint("CheckResult")
        void deleteWords(List<Word> words);
    }

    interface View{
        void setWordList(List<Word> wordList);

        void onWordsDeleted();

        void onError(String message);
    }
}
