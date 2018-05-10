package com.mobile2.projeto2.repository;

import com.mobile2.projeto2.Project2Application;
import com.mobile2.projeto2.entity.Syllable;
import com.mobile2.projeto2.entity.Word;
import com.mobile2.projeto2.entity.data.SyllableData;
import com.mobile2.projeto2.entity.data.WordData;
import com.mobile2.projeto2.repository.room.DatabaseDao;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by cesar on 5/9/2018.
 */

public class GeneralRepository {
    DatabaseDao databaseDao;

    public GeneralRepository() {
        this.databaseDao = Project2Application.getDatabase().getDao();
    }

    public Single<Word> getWord(String word){
        return databaseDao.getWord(word)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable saveWord(Word word){
        return Completable.fromAction(() -> databaseDao.insertWord(word))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<Word>> getAllWords(){
        return databaseDao.getAllWords()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<Syllable>> getAllSyllables(){
        return databaseDao.getAllSyllables()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}