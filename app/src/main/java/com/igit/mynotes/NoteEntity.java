package com.igit.mynotes;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "NotesTable")
public class NoteEntity
{

    @PrimaryKey(autoGenerate = true)
    private int id;

private String title;

private String content;
private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

 /*   public  NoteEntity(){


}*/
@Ignore
    public NoteEntity(int id, String title, String content,     String date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public NoteEntity(String title, String content, String date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }
}
