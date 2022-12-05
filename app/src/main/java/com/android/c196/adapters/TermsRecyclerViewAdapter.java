package com.android.c196.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.c196.R;
import com.android.c196.UI.TermCourses;
import com.android.c196.model.Term;
import com.android.c196.util.Repository;
import com.android.c196.util.Utils;

import java.util.List;

public class TermsRecyclerViewAdapter extends RecyclerView.Adapter<TermsRecyclerViewAdapter.TermViewHolder> {

    private List<Term> terms;
    private Context context;
    private LayoutInflater inflater;
    private Repository repo;

    //constructor
    public TermsRecyclerViewAdapter(Context context, Repository repo) {

        inflater = LayoutInflater.from(context);
        this.context = context;
        this.repo = repo;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
        notifyDataSetChanged();
    }

    //inflate layouts and applies styles to rows
    @NonNull
    @Override
    public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.term_recycler_view_row, parent, false);
        return new TermViewHolder(itemView);
    }

    //tie data to recyclerView
    @Override
    public void onBindViewHolder(@NonNull TermViewHolder holder, int position) {
        if (terms != null) {
            Term term = terms.get(position);
            holder.termTitleText.setText(term.getTermTitle());
            holder.termStartText.setText(Utils.formatDate(term.getTermStartDate()));
            holder.termEndText.setText(Utils.formatDate(term.getTermEndDate()));

            holder.termHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //toggle display view
                    if (holder.termDetailsDisplay.getVisibility() == View.GONE) {
                        holder.termDetailsDisplay.setVisibility(View.VISIBLE);
                    } else {
                        holder.termDetailsDisplay.setVisibility(View.GONE);
                    }
                }
            });

            holder.showCoursesIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TermCourses.class);
                    intent.putExtra("termId", term.getTermId());
                    context.startActivity(intent);
                }
            });

            holder.deleteTermIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(repo.getTermCourses(term.getTermId()).size() != 0) {
                        Toast toast = Toast.makeText(context, "You cannot delete a term with courses.", Toast.LENGTH_LONG);
                        toast.show();
                    } else {
                        repo.deleteTerm(term);
                        terms = repo.getAllTerms();
                        notifyItemRemoved(holder.getAdapterPosition());
                    }
                }
            });

            holder.isEmpty.setVisibility(View.GONE);
            holder.termHeader.setVisibility(View.VISIBLE);
        } else {
            holder.isEmpty.setVisibility(View.VISIBLE);
            holder.termHeader.setVisibility(View.GONE);

        }
    }

    @Override
    public int getItemCount() {
        return terms.size();
    }

    class TermViewHolder extends RecyclerView.ViewHolder {
        private TextView termTitleText;
        private ImageView editTermIcon;
        private ImageView deleteTermIcon;
        private ImageView showCoursesIcon;
        private LinearLayout termDetailsDisplay;
        private TextView termStartText;
        private TextView termEndText;
        private ConstraintLayout termHeader;
        private TextView isEmpty;

        public TermViewHolder(@NonNull View itemView) {
            super(itemView);

            termHeader = itemView.findViewById(R.id.termHeader);
            termTitleText = itemView.findViewById(R.id.termTitleText);
            showCoursesIcon = itemView.findViewById(R.id.showCoursesIcon);
            editTermIcon = itemView.findViewById(R.id.editTermIcon);
            deleteTermIcon = itemView.findViewById(R.id.deleteTermIcon);
            termDetailsDisplay = itemView.findViewById(R.id.termDetailsDisplay);
            termStartText = itemView.findViewById(R.id.termStartText);
            termEndText = itemView.findViewById(R.id.termEndText);
            termDetailsDisplay.setVisibility(View.GONE);
            isEmpty = itemView.findViewById(R.id.isEmpty);

        }
    }

    public interface HandleClickTerm {
        void removeTerm(int termId);

        void editTerm(int termId);
    }
}
