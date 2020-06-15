package com.igit.mynotes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecViewOnclickListener {
private RecyclerView recyclerView;
private ListViewModel listViewModel;
private ViewModelProvider.AndroidViewModelFactory viewModelFactory;
public static final int ADD_NOTE_REQUEST=1;
public static final int EDIT_NOTE_REQUEST=2;
    NoteEntity deletednote=null;
Intent editintent;
Adapter adapter;
FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//viewModelFactory= (ViewModelProvider.AndroidViewModelFactory) new ViewModelProvider.NewInstanceFactory();
        setContentView(R.layout.activity_main);
    recyclerView=findViewById(R.id.recyclerview);
   recyclerView.setLayoutManager(new LinearLayoutManager(this));
listViewModel= new ViewModelProvider(this,new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(ListViewModel.class);
//listViewModel= new ViewModelProvider(MainActivity.this).get(ListViewModel.class);
        adapter=new Adapter(MainActivity.this,this);
        recyclerView.setAdapter(adapter);
listViewModel.getNotelist().observe(this, new Observer<List<NoteEntity>>() {
    @Override
    public void onChanged(List<NoteEntity> noteEntities) {
adapter.setNotelist(noteEntities);

    }
});
    floatingActionButton=findViewById(R.id.floatingActionButton);
    floatingActionButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(MainActivity.this,DetailActivity.class);
            startActivityForResult(intent,ADD_NOTE_REQUEST);
        }
    });

ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleCallback);
itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
final int position= viewHolder.getAdapterPosition();

            switch (direction){
                    case ItemTouchHelper.LEFT:
                 //       final LiveData<List<NoteEntity>> notelist=listViewModel.getNotelist();
                   //  deletednote  =notelist.getValue().get(viewHolder.getAdapterPosition());
 deletednote=adapter.remove(position);

//adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                        final  Snackbar snackbar=
                          Snackbar.make(recyclerView,"Note deleted",Snackbar.LENGTH_LONG).setAction("Undo", new
                                View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
adapter.addNote(position,deletednote);

                                    }
                                });
snackbar.addCallback(new Snackbar.Callback(){
    @Override
    public void onDismissed(Snackbar transientBottomBar, int event) {
        super.onDismissed(transientBottomBar, event);
    if(event==DISMISS_EVENT_TIMEOUT){
        listViewModel.delete(deletednote);
    }

    }
});
snackbar.show();
                        break;


                }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

       getMenuInflater().inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(adapter.getItemCount()!=0){
            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("DELETE ALL NOTES");
        builder.setMessage("Do you want to delete all notes?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listViewModel.deleteAll();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
            return  true;
        }
        else {
            Toast.makeText(MainActivity.this,"Insert notes first",Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ADD_NOTE_REQUEST&&resultCode==RESULT_OK){


            assert data != null;
            String title=data.getStringExtra(DetailActivity.EXTRA_TITLE);
            String content=data.getStringExtra(DetailActivity.EXTRA_CONTENT);
            String date=data.getStringExtra(DetailActivity.EXTRA_DATE);
            NoteEntity noteEntity=new NoteEntity(title,content,date);
            listViewModel.insert(noteEntity);


        }else if(requestCode==EDIT_NOTE_REQUEST && resultCode==RESULT_OK){

            assert data != null;
            boolean delete=data.getBooleanExtra(DetailActivity.EXTRA_DELETE,false);

            String title=data.getStringExtra(DetailActivity.EXTRA_TITLE);
            String content=data.getStringExtra(DetailActivity.EXTRA_CONTENT);


            String date=data.getStringExtra(DetailActivity.EXTRA_DATE);
            Integer id=data.getIntExtra(DetailActivity.EXTRA_ID,0);
            NoteEntity noteEntity=new NoteEntity(id,title,content,date);
            if(delete){
listViewModel.delete(noteEntity);
            }

            listViewModel.update(noteEntity);

Log.i("tag","updated");
        }
        else {
            Toast.makeText(MainActivity.this,"not saved",Toast.LENGTH_SHORT).show();
        }
        }


    @Override
    public void onclick( int position) {
        Log.i("tag","clicked");
       LiveData<List<NoteEntity>> notelist=listViewModel.getNotelist();
       int id= notelist.getValue().get(position).getId();

        editintent=new Intent(MainActivity.this,DetailActivity.class);
        editintent.putExtra("id",id);
    startActivityForResult(editintent,EDIT_NOTE_REQUEST);
    }


}