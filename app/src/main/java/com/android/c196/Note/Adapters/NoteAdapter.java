package com.android.c196.Note.Adapters;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.c196.Note.Controllers.AddNote;
import com.android.c196.Note.Model.Note;
import com.android.c196.Note.Model.NoteViewModel;
import com.android.c196.R;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
    private List<Note> notes = new ArrayList<>();
    private Context context;
    private Application application;
    private NoteViewModel noteViewModel;

    public NoteAdapter(Context context, Application application) {
        this.context = context;
        this.application = application;
        noteViewModel = new NoteViewModel(application);
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

            holder.editNoteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long courseId = note.getNoteCourseId();
                    Intent intent = new Intent(context, AddNote.class);
                    intent.putExtra("courseId", courseId);
                    intent.putExtra("noteId", note.getNoteId());
                    context.startActivity(intent);
                }
            });

            holder.deleteNoteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    noteViewModel.deleteNote(note);

                }
            });

            holder.shareNoteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, note.getNoteTitle());
                    shareIntent.putExtra(Intent.EXTRA_TEXT, note.getNoteBody());

                    context.startActivity(Intent.createChooser(shareIntent, "Share note via"));
                }
            });

        }



    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes (List<Note> notes) {

        this.notes = notes;
        notifyDataSetChanged();
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
