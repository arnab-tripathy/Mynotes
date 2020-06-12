package com.igit.mynotes;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities={NoteEntity.class},version = 1)
public  abstract class NotesDatabase extends RoomDatabase {

    private static NotesDatabase instance;

public abstract NotesTabeleDao Notedao();



    public static synchronized NotesDatabase getInstance(Context context){
if (instance==null){
instance=Room.databaseBuilder(context.getApplicationContext(),
        NotesDatabase.class,
        "notesdatabase").build();

}
   return  instance; }

}
