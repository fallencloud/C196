package com.android.c196.Note.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.c196.Note.Model.Note;
import com.android.c196.R;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
    private List<Note> notes = new ArrayList<>();
    private Context context;

    public NoteAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.note_recycler_view_row, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        if(notes != null) {
            Note note = notes.get(position);
            holder.noteTitleText.setText(note.getNoteTitle());
            holder.noteBodyText.setText(note.getNoteBody());
        }

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes (List<Note> notes) {
        this.notes = notes;
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        TextView noteTitleText;
        ImageView shareNoteIcon;
        ImageView editNoteIcon;
        ImageView deleteNoteIcon;
        TextView noteBodyText;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            noteTitleText = itemView.findViewById(R.id.noteTitleText);
            shareNoteIcon = itemView.findViewById(R.id.shareNoteIcon);
            editNoteIcon = itemView.findViewById(R.id.editNoteIcon);
            deleteNoteIcon = itemView.findViewById(R.id.deleteNoteIcon);
            noteBodyText = itemView.findViewById(R.id.noteBodyText);
        }
    }
}
