package com.igit.mynotes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NotesTabeleDao {
@Insert
    void insertNote(NoteEntity noteEntity);
@Update
void updateNote(NoteEntity noteEntity);
@Delete
    void deletenote(NoteEntity noteEntity);

@Query("SELECT * FROM NotesTable WHERE id=:id")
    LiveData<NoteEntity> getNotebyid(Integer id);

@Query("SELECT * FROM NotesTable ORDER BY date DESC ")
    LiveData<List<NoteEntity>> getNotelist();
@Query("DELETE FROM NotesTable")
void DeleteAllNotes();


}
