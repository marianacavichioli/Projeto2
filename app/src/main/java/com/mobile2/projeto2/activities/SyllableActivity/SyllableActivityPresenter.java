package com.mobile2.projeto2.activities.SyllableActivity;

import android.net.Uri;

import com.mobile2.projeto2.entity.Syllable;
import com.mobile2.projeto2.entity.Word;
import com.mobile2.projeto2.repository.GeneralRepository;

import java.io.File;

/**
 * Created by cesar on 5/6/2018.
 */

public class SyllableActivityPresenter implements SyllableActivityInterface.Presenter {

    private GeneralRepository generalRepository;
    private SyllableActivityInterface.View mView;
    private Word mWord;

    public SyllableActivityPresenter(SyllableActivityInterface.View view) {
        this.mView = view;
        this.mWord = new Word("ZE", "BRA");
        generalRepository = new GeneralRepository();

        mView.setWord(mWord);
        mView.addRightButton(mWord.getSyllableAt(0), 0);
        mView.addRightButton(mWord.getSyllableAt(1), 1);

        mView.addWrongButton(new Syllable("BE"));
        mView.addWrongButton(new Syllable("BE"));
        mView.addWrongButton(new Syllable("BE"));
        mView.addWrongButton(new Syllable("BE"));
        mView.addWrongButton(new Syllable("BE"));
        mView.addWrongButton(new Syllable("BE"));
        mView.addWrongButton(new Syllable("BE"));
        mView.addWrongButton(new Syllable("BE"));

    }

    @Override
    public void fetchWord(String wordString) {
        generalRepository.getWord(wordString)
                .subscribe(word -> {
                    mView.setWord(word);
                    mView.setImage(Uri.fromFile(new File(word.getImageFilePath())));
                }, throwable -> {
                    throwable.printStackTrace();
                    mView.onError(throwable.getMessage());
                });
    }
}
