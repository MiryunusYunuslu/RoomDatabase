package com.example.architecturecomp.View.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_name")
public class MainData {
    @PrimaryKey(autoGenerate = true)
    private int Id;
    @ColumnInfo(name="text")
    private String text;
    public String getText() {
        return text;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getId() {

        return Id;
    }

    public void setText(String text) {
        this.text = text;

    }
}
