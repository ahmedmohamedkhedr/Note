package com.example.note;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.NoteViewHolder> {
    private List<DataModel> list;
    private OnNoteListener onNoteListener;


    public Adapter(List<DataModel> list, OnNoteListener onNoteListener) {
        this.onNoteListener = onNoteListener;
        this.list = list;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout, parent, false);
        return new NoteViewHolder(view, onNoteListener);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        DataModel dataModel = list.get(position);
        holder.textView.setText(dataModel.getLabel());
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM 'at' hh:mm a");
        holder.textViewDate.setText(simpleDateFormat.format(date));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView,textViewDate;
        OnNoteListener onNoteListener;

        public NoteViewHolder(View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            textView = itemView.findViewById(R.id.getLabel);
            textViewDate = itemView.findViewById(R.id.date);

            itemView.setOnClickListener(this);
            this.onNoteListener = onNoteListener;
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener {
        void onNoteClick(int position);
    }

}
