package com.mobile2.projeto2.repository.room;

import android.arch.persistence.room.RoomDatabase;

import com.mobile2.projeto2.entity.data.SyllableData;
import com.mobile2.projeto2.entity.data.WordData;
import com.mobile2.projeto2.entity.data.roomjoins.SyllablesFromWord;

/**
 * Created by cesar on 5/9/2018.
 */

@android.arch.persistence.room.Database(
        entities = {
                SyllableData.class,
                WordData.class,
                SyllablesFromWord.class
        }, version = 1)
public abstract class ProjectDatabase extends RoomDatabase {
    public abstract DatabaseDao getDao();
}
