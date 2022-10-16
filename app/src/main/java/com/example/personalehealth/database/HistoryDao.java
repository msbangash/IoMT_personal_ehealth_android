package com.example.personalehealth.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HistoryDao {

    @Insert
    public void addToHistory(History History);

    @Update
    public void updateHistory(History History);

    @Query("SELECT * FROM MyHistory")
    public List<History>getData();

    @Query("SELECT EXISTS (SELECT 1 FROM myHistory WHERE id=:id)")
    public int isAddToHistory(int id);

    @Query("select COUNT (*) from MyHistory")
    int countHistory();

    @Query("DELETE FROM MyHistory WHERE id=:id ")
    int deleteItem(int id);

    @Query("DELETE FROM MyHistory")
    void delete();

}