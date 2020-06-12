package com.igit.mynotes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ListViewModel extends AndroidViewModel {

private LiveData<List<NoteEntity>> notelist;
private  NoteRepository noteRepository;
private  NoteEntity noteEntity;
    public ListViewModel(@NonNull Application application) {
        super(application);

        noteRepository=new NoteRepository(application);
    notelist=noteRepository.getNotelist();

    }
    public void update(NoteEntity note){
        noteRepository.update(note);
    }
public  void insert(NoteEntity note){
        noteRepository.insert(note);
}
public  void delete(NoteEntity note){
        noteRepository.delete(note);
}

public  void deleteAll(){
        noteRepository.deleteAll();
}

public  LiveData<List<NoteEntity>> getNotelist(){
        return notelist;
}
public LiveData<NoteEntity> getNotebyid(int id){

        return noteRepository.getNotebyid(id);
}
}
