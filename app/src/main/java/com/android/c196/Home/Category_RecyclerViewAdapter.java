package com.android.c196.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.c196.R;

import java.util.ArrayList;

public class Category_RecyclerViewAdapter extends RecyclerView.Adapter<Category_RecyclerViewAdapter.MyViewHolder> {
    private final CatRecyclerViewInterface recyclerViewInterface;

    Context context;
    ArrayList<Category> categories;

    //constructor
    public Category_RecyclerViewAdapter(Context context, ArrayList<Category> categories, CatRecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.categories = categories;
        this.recyclerViewInterface = recyclerViewInterface;

    }

    //inflates layouts
    //styles each row
    @NonNull
    @Override
    public Category_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cat_recycler_view_row, parent, false);
        return new Category_RecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    //assigns the data pass into recyclerview
    //each row filled based on the item's position
    @Override
    public void onBindViewHolder(@NonNull Category_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.catRecyclerCatName.setText(categories.get(position).getCategoryName());
        holder.catRecyclerImage.setImageResource(categories.get(position).getImageUrl());

    }

    //gets the total number of items in the RecyclerView
    @Override
    public int getItemCount() {
        return categories.size();
    }

    //sort of like onCreate
    //grabs everything for the layout and assigns them to variables
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView catRecyclerImage;
        TextView catRecyclerCatName;

        public MyViewHolder(@NonNull View itemView, CatRecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            catRecyclerImage = itemView.findViewById(R.id.cat_recycler_image);
            catRecyclerCatName = itemView.findViewById(R.id.cat_recycler_catName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null) {
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
