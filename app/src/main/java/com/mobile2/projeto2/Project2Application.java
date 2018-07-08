package com.mobile2.projeto2;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mobile2.projeto2.entity.Syllable;
import com.mobile2.projeto2.entity.Word;
import com.mobile2.projeto2.entity.data.SyllableData;
import com.mobile2.projeto2.entity.data.WordData;
import com.mobile2.projeto2.repository.GeneralRepository;
import com.mobile2.projeto2.repository.room.ProjectDatabase;
import com.mobile2.projeto2.util.Constans;
import com.mobile2.projeto2.util.Password;
import com.mobile2.projeto2.util.SyllableList;

import java.io.File;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

import static com.mobile2.projeto2.util.Constans.*;

/**
 * Created by cesar on 5/9/2018.
 */

public class Project2Application extends Application {

    private static ProjectDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(this, ProjectDatabase.class, "database")
                .fallbackToDestructiveMigration()
                .build();

        String pin = "";
        SharedPreferences sharedPreferences = getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE);
        if (!sharedPreferences.contains(PASSWORD_KEY)) {
            prePopulate();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constans.PASSWORD_KEY, pin);
            editor.commit();
        } else {
            pin = sharedPreferences.getString(PASSWORD_KEY, "");
        }
        Password.setPIN(pin);
    }

    @SuppressLint("CheckResult")
    private void prePopulate() {
        Completable.fromAction(() -> {
            SyllableData syllables[] = new SyllableData[SyllableList.getSyl().length];
            for (int i = 0; i < SyllableList.getSyl().length; i++) {
                syllables[i] = new SyllableData(SyllableList.getSyl()[i]);
            }
            database.getDao().insert(syllables);
        })
                .subscribeOn(Schedulers.io())
                .subscribe(() -> Log.d("ROOM", "DB created, prepopulate complete."),
                        Throwable::printStackTrace);


        Uri ballUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.ball);
        Uri diceUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.dice);
        Uri grapeUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.grape);
        Uri strawberryUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.strawberry);
        GeneralRepository.saveWord(new Word(ballUri.toString() , null, "bo", "la"))
                .subscribe();
        GeneralRepository.saveWord(new Word(diceUri.toString(), null, "da", "do"))
                .subscribe();
        GeneralRepository.saveWord(new Word(grapeUri.toString(), null, "u", "va"))
                .subscribe();
        GeneralRepository.saveWord(new Word(strawberryUri.toString(), null, "mo", "ran", "go"))
                .subscribe();

        Uri cavaloVideoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.cavalo_video);
        Uri pandaVideoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.panda);
        GeneralRepository.saveWord(new Word(null, cavaloVideoUri.toString(), "ca", "va", "lo"))
                .subscribe();
        GeneralRepository.saveWord(new Word(null, pandaVideoUri.toString(), "pan", "da"))
                .subscribe();
    }

    public synchronized static ProjectDatabase getDatabase() {
        return database;
    }
}
