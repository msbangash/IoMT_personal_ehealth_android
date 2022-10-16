package com.example.personalehealth.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities={History.class},version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract HistoryDao historyDao();
}