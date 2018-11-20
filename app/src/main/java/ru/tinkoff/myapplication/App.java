package ru.tinkoff.myapplication;

import android.app.Application;
import android.arch.persistence.room.Room;

public class App extends Application {

    private static MyDatabase myDatabase;

    public static MyDatabase getMyDatabase() {
        return myDatabase;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        myDatabase = Room.databaseBuilder(this, MyDatabase.class, "my_database").build();
    }
}