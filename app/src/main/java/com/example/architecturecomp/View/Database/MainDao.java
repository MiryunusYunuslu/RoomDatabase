package com.example.architecturecomp.View.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MainDao {
    @Insert()
    void insert(MainData mainData);
    @Delete
    void delete(MainData mainData);
    @Delete
    void reset(List<MainData> mainDataList);
    @Query("UPDATE table_name SET text=:sText WHERE Id=:sID")
    void update(int sID,String sText);
    @Query("SELECT * FROM table_name ")
   List<MainData> getData();

}
