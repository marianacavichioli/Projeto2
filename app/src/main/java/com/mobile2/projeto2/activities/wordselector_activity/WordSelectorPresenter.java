package com.mobile2.projeto2.activities.wordselector_activity;

import android.annotation.SuppressLint;

import com.mobile2.projeto2.entity.Word;
import com.mobile2.projeto2.repository.GeneralRepository;

import java.util.ArrayList;

public class WordSelectorPresenter implements WordSelectorInterface.Presenter {

    WordSelectorInterface.View mView;


    public WordSelectorPresenter(WordSelectorInterface.View mView) {
        this.mView = mView;
    }

    @SuppressLint("CheckResult")
    @Override
    public void fetchWordList() {
        GeneralRepository.getAllWords()
                .subscribe(mView::setWordList, throwable -> {
                    throwable.printStackTrace();
                    mView.onError(throwable.getMessage());
                });
    }

}
