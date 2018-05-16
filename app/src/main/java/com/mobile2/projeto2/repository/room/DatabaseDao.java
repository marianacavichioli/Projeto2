package com.mobile2.projeto2.repository.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.mobile2.projeto2.entity.Syllable;
import com.mobile2.projeto2.entity.Word;
import com.mobile2.projeto2.entity.data.SyllableData;
import com.mobile2.projeto2.entity.data.WordData;
import com.mobile2.projeto2.entity.data.roomjoins.SyllablesFromWord;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by cesar on 5/9/2018.
 */

@Dao
public abstract class DatabaseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(SyllableData... syllableData);

    //TODO: Switch to upsert when we add video to syllables...
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(WordData... wordData);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(SyllablesFromWord... syllablesFromWord);

    @Query("SELECT SyllableData.syllable FROM SyllableData " +
            "INNER JOIN SyllablesFromWord ON SyllableData.syllable = SyllablesFromWord.syllable " +
            "WHERE SyllablesFromWord.word = :word")
    public abstract Single<List<SyllableData>> getSyllablesFromWord(String word);

    @Query("SELECT WordData.* FROM WordData " +
            "WHERE word = :word")
    public abstract Single<WordData> getWordData(String word);

    @Query("SELECT SyllableData.* FROM SyllableData " +
            "WHERE syllable = :syllable")
    public abstract Single<SyllableData> getSyllableData(String syllable);

    @Query("SELECT WordData.word FROM WordData ")
    public abstract Single<List<String>> getAllWordsString();

    @Query("SELECT SyllableData.syllable FROM SyllableData ")
    public abstract Single<List<String>> getAllSyllablesString();

    @Transaction
    public void insertWord(Word word) {
        insert(word.getData());
        for (Syllable syllable : word.getSyllables()) {
            insert(syllable.getData());
            insert(new SyllablesFromWord(word.toString(), syllable.toString()));
        }
    }

    public Single<Word> getWord(String word) {
        return getSyllablesFromWord(word)
                .flatMap(syllableData -> getWordData(word)
                        .map(wordData -> new Word(wordData, syllableData)));
    }

    public Single<Syllable> getSyllable(String syllable) {
        return getSyllableData(syllable)
                .map(Syllable::new);
    }

    public Single<List<Word>> getAllWords() {
        return getAllWordsString()
                .flatMapObservable(Observable::fromIterable)
                .flatMapSingle(this::getWord)
                .toList();
    }

    public Single<List<Syllable>> getAllSyllables() {
        return getAllSyllablesString()
                .flatMapObservable(Observable::fromIterable)
                .flatMapSingle(this::getSyllable)
                .toList();
    }

}
