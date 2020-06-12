package com.igit.mynotes;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.lang.ref.PhantomReference;
import java.util.List;

public class NoteRepository  {

    private NotesTabeleDao notesTabeleDao;
    private LiveData<List<NoteEntity>> notelist;
    private LiveData<NoteEntity> noteEntity;



    public NoteRepository(Application application){
NotesDatabase notesDatabase=NotesDatabase.getInstance(application);
notesTabeleDao=notesDatabase.Notedao();
notelist=notesTabeleDao.getNotelist();

}
public void insert(NoteEntity note){
new InsertAsyncTask(notesTabeleDao).execute(note);
}
public void update(NoteEntity note){
        new UpdateNoteAsyncTask(notesTabeleDao).execute(note);
}
    public void delete(NoteEntity note){
new DeleteAsyncTask(notesTabeleDao).execute(note);
    }

    public void deleteAll(){
new DeleteAllAsyncTask(notesTabeleDao).execute();
    }
    public LiveData<NoteEntity> getNotebyid(Integer id){
        return notesTabeleDao.getNotebyid(id);
    }
public LiveData<List<NoteEntity>> getNotelist(){
return notelist;
}
private static class InsertAsyncTask extends AsyncTask<NoteEntity,Void,Void >{
private NotesTabeleDao notesTabeleDao;
private InsertAsyncTask(NotesTabeleDao notesTabeleDao){
    this.notesTabeleDao=notesTabeleDao;
}

@Override
    protected Void doInBackground(NoteEntity... noteEntities) {

    notesTabeleDao.insertNote(noteEntities[0]);
    return null;
    }
}
    private static class DeleteAsyncTask extends AsyncTask<NoteEntity,Void,Void >{
        private NotesTabeleDao notesTabeleDao;
        private DeleteAsyncTask(NotesTabeleDao notesTabeleDao){
            this.notesTabeleDao=notesTabeleDao;
        }

        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {

            notesTabeleDao.deletenote(noteEntities[0]);
            return null;
        }
    }
    private static class DeleteAllAsyncTask extends AsyncTask<Void,Void,Void >{
        private NotesTabeleDao notesTabeleDao;
        private DeleteAllAsyncTask(NotesTabeleDao notesTabeleDao){
            this.notesTabeleDao=notesTabeleDao;
        }

        @Override
        protected Void doInBackground(Void...voids) {

            notesTabeleDao.DeleteAllNotes();
            return null;
        }
    }
private  static  class UpdateNoteAsyncTask extends AsyncTask<NoteEntity,Void,Void>{
    private NotesTabeleDao notesTabeleDao;
    private UpdateNoteAsyncTask(NotesTabeleDao notesTabeleDao){
        this.notesTabeleDao=notesTabeleDao;
    }
        @Override
    protected Void doInBackground(NoteEntity... noteEntities) {

        notesTabeleDao.updateNote(noteEntities[0]);

        return null;
    }
}
}
