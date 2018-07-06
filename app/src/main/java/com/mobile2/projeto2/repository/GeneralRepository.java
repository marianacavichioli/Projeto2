package com.mobile2.projeto2.repository;

import android.support.annotation.NonNull;

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
    private final static DatabaseDao databaseDao = Project2Application.getDatabase().getDao();

    public static Single<Word> getWord(String word) {
        return databaseDao.getWord(word)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Completable saveWord(Word word) {
        return Completable.fromAction(() -> databaseDao.insertWord(word))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<List<Word>> getAllWords() {
        return databaseDao.getAllWords()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<List<Word>> getAllWordsWithAsset() {
        return databaseDao.getAllWordsWithAsset()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<List<Word>> getAllWordsWithImages() {
        return databaseDao.getAllWordsWithImages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<List<Syllable>> getAllSyllables() {
        return databaseDao.getAllSyllables()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Completable deleteWords(List<Word> words) {
        return Completable.fromAction(() -> {
            final WordData[] wordData = getWordDataArray(words);
            databaseDao.delete(wordData);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Completable deleteImageFromWords(List<Word> words) {
        return Completable.fromAction(() -> {
            final WordData[] wordData = getWordDataArray(words);
            for (WordData data : wordData) {
                data.setImageFilePath(null);
            }
            databaseDao.update(wordData);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Completable deleteVideoFromWords(List<Word> words) {
        return Completable.fromAction(() -> {
            final WordData[] wordData = getWordDataArray(words);
            for (WordData data : wordData) {
                data.setVideoFilePath(null);
            }
            databaseDao.update(wordData);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    private static WordData[] getWordDataArray(List<Word> words) {
        WordData[] wordData = new WordData[words.size()];
        for (int i = 0; i < words.size(); i++) {
            wordData[i] = words.get(i).getData();
        }
        return wordData;
    }
}
