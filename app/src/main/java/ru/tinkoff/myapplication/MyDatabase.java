package ru.tinkoff.myapplication;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = Node.class, version = 1, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    public abstract NodeDao getNodeDao();
}
