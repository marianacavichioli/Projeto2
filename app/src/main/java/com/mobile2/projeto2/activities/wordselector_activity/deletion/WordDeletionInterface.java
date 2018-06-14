package com.mobile2.projeto2.activities.wordselector_activity.deletion;

import com.mobile2.projeto2.entity.Word;

import java.util.List;

import io.reactivex.Completable;

public interface WordDeletionInterface {
    interface Presenter {
        void fetchWordList();

        void deleteWords(List<Word> words);
    }

    interface View{
        void setWordList(List<Word> wordList);

        void onWordsDeleted();

        void onError(String message);
    }
}
