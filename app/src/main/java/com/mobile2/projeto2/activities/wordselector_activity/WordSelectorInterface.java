package com.mobile2.projeto2.activities.wordselector_activity;

import com.mobile2.projeto2.entity.Word;

import java.util.List;

interface WordSelectorInterface {

    interface Presenter {
        void fetchWordList();
    }

    interface View{
        void setWordList(List<Word> wordList);

        void onError(String message);
    }
}
