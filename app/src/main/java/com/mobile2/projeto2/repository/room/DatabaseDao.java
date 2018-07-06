package com.mobile2.projeto2.repository.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

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

    @Insert(onConflict = OnConflictStrategy.FAIL)
    public abstract void insert(WordData... wordData);

    public void upsert(WordData wordData) {
        try {
            insert(wordData);
        } catch (Exception e) {
            if (wordData.getImageFilePath() != null && !wordData.getImageFilePath().isEmpty()) {
                updateWordImage(wordData.getWord(), wordData.getImageFilePath());
            }
            if (wordData.getVideoFilePath() != null && !wordData.getVideoFilePath().isEmpty()) {
                updateWordVideo(wordData.getWord(), wordData.getVideoFilePath());
            }
        }
    }

    @Query("UPDATE WordData " +
            "SET videoFilePath = :path " +
            "WHERE word = :word")
    protected abstract void updateWordVideo(String word, String path);

    @Query("UPDATE WordData " +
            "SET imageFilePath = :path " +
            "WHERE word = :word")
    protected abstract void updateWordImage(String word, String path);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(SyllablesFromWord... syllablesFromWord);

    @Delete
    public abstract void delete(WordData... wordData);

    @Update
    public abstract void update(WordData... wordData);

    @Query("SELECT SyllableData.syllable FROM SyllableData " +
            "INNER JOIN SyllablesFromWord ON SyllableData.syllable = SyllablesFromWord.syllable " +
            "WHERE SyllablesFromWord.word = :word " +
            "ORDER BY SyllablesFromWord.position ")
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

    public void insertWord(Word word) {
        upsert(word.getData());
        for (int i = 0; i < word.syllableCount(); i++) {
            Syllable syllable = word.getSyllableAt(i);
            insert(syllable.getData());
            insert(new SyllablesFromWord(word.toString(), syllable.toString(), i));
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

    public Single<List<Word>> getAllWordsWithAsset() {
        return getAllWordsString()
                .flatMapObservable(Observable::fromIterable)
                .flatMapSingle(this::getWord)
                .filter(word -> word.getImageFilePath() != null || word.getVideoFilePath() != null)
                .toList();
    }

    public Single<List<Syllable>> getAllSyllables() {
        return getAllSyllablesString()
                .flatMapObservable(Observable::fromIterable)
                .flatMapSingle(this::getSyllable)
                .toList();
    }

    public Single<List<Word>> getAllWordsWithImages() {
        return getAllWordsString()
                .flatMapObservable(Observable::fromIterable)
                .flatMapSingle(this::getWord)
                .filter(word -> word.getImageFilePath() != null && !word.getImageFilePath().isEmpty())
                .toList();
    }
}
