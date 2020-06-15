package com.igit.mynotes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyviewHolder> {
Context context;
RecViewOnclickListener recViewOnclickListener;
List<NoteEntity> notelist=new ArrayList<>();

    public Adapter(Context context,RecViewOnclickListener recViewOnclickListener) {
        this.context = context;
this.recViewOnclickListener=recViewOnclickListener;
    }

    public void setNotelist(List<NoteEntity> notelist) {
        this.notelist = notelist;
    notifyDataSetChanged();
    }
public  NoteEntity remove(int position){

            NoteEntity deletednote=notelist.remove(position);
            notifyItemRemoved(position);
            return deletednote;


    }

    public void addNote(int position,NoteEntity noteEntity){
        notelist.add(position,noteEntity);
        notifyItemInserted(position);
    }
    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.rec_layout,parent,false);


        return new MyviewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
NoteEntity note=notelist.get(position);

        holder.title.setText(note.getTitle());
        holder.content.setText(note.getContent());
        holder.date.setText(note.getDate());



    }

    @Override
    public int getItemCount() {
        return notelist.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
    TextView title,content,date;
        public MyviewHolder(@NonNull View itemView) {
        super(itemView);
    title=itemView.findViewById(R.id.title);
    content=itemView.findViewById(R.id.content);
    date=itemView.findViewById(R.id.date);
itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        recViewOnclickListener.onclick(getAdapterPosition());
    }
});
itemView.setOnLongClickListener(new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View v) {


        return false;
    }
});
        }
}
}
