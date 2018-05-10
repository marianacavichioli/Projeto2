package com.mobile2.projeto2;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mobile2.projeto2.entity.Syllable;
import com.mobile2.projeto2.entity.Word;
import com.mobile2.projeto2.entity.data.SyllableData;
import com.mobile2.projeto2.entity.data.WordData;
import com.mobile2.projeto2.repository.GeneralRepository;
import com.mobile2.projeto2.repository.room.ProjectDatabase;
import com.mobile2.projeto2.util.SyllableList;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by cesar on 5/9/2018.
 */

public class Project2Application extends Application {

    private static ProjectDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(this, ProjectDatabase.class, "database")
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        Completable.fromAction(() -> {
                            SyllableData syllables [] = new SyllableData[SyllableList.getSyl().length];
                            for (int i = 0; i < SyllableList.getSyl().length; i++) {
                                syllables[i] = new SyllableData(SyllableList.getSyl()[i]);
                            }
                            database.getDao().insert(syllables);
                        })
                                .subscribeOn(Schedulers.io())
                                .subscribe(() -> Log.d("ROOM", "DB created, prepopulate complete."),
                                        Throwable::printStackTrace);
                    }
                }).build();
    }

    public synchronized static ProjectDatabase getDatabase() {
        return database;
    }
}
