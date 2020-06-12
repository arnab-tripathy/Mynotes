package com.igit.mynotes;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class DetailActivity extends AppCompatActivity {
EditText mcontent;
EditText mtitle;
Calendar calendar;
Integer id;
ListViewModel listViewModel;
public static final String EXTRA_DELETE="com.igit.mynotes.EXTRA_DELETE";
public static final String EXTRA_TITLE="com.igit.mynotes.EXTRA_TITLE";
    public static final String EXTRA_CONTENT="com.igit.mynotes.EXTRA_CONTENT";
    public static final String EXTRA_DATE="com.igit.mynotes.EXTRA_DATE";
    public static final String EXTRA_ID="com.igit.mynotes.EXTRA_ID";
    boolean edit=false;
boolean delete=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

mtitle=findViewById(R.id.detaiL_act_title);
mcontent=findViewById(R.id.detail_act_content);
listViewModel=new ViewModelProvider(DetailActivity.this,new ViewModelProvider.AndroidViewModelFactory(DetailActivity.this.getApplication()))
        .get(ListViewModel.class);
Intent intent=getIntent();
 id=intent.getIntExtra("id",0);
if(id!=0) {
    listViewModel.getNotebyid(id).observe(DetailActivity.this, new Observer<NoteEntity>() {
        @Override
        public void onChanged(NoteEntity noteEntity) {
           if(noteEntity!=null) {
               mtitle.setText(noteEntity.getTitle());
               mcontent.setText(noteEntity.getContent());


           }
        }
    });
    edit=true; //to check if the activity is used to edit

}


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
returnback();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (edit) {

            getMenuInflater().inflate(R.menu.menu_detail, menu);

            return true;
        }
    return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:
        delete=true;
        returnback();
        return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


//here we return to the previous activity
    private void returnback() {
    String title=mtitle.getText().toString();
    String content=mcontent.getText().toString();
    calendar=Calendar.getInstance();
        String date=calendar.get(Calendar.DATE)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.YEAR);

    if(content.isEmpty()){
        Toast.makeText(DetailActivity.this,"Please enter note",Toast.LENGTH_SHORT).show();
return;
    }
    if(mtitle.getText().toString().trim().isEmpty()){
        title="No title";
    }

        Intent data=new Intent();

    data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_CONTENT,content);
        data.putExtra(EXTRA_DATE,date);
data.putExtra(EXTRA_DELETE,delete);
        data.putExtra(EXTRA_ID,id);
        setResult(RESULT_OK,data);
        finish();
    }
}