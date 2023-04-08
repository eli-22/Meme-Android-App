package com.elise.elisefinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.elise.elisefinal.manageJSON.Util;
import com.elise.elisefinal.model.Meme;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewViewHolder> {

    Context context;
    ArrayList<Meme> memeArrayList;
    private OnItemClickListener onClickListener;
    private OnItemLongClickListener onLongClickListener;

    // This is how we know where to "connect" the adapter and present the recycler view.
    public RecyclerViewAdapter(Context context, ArrayList<Meme> memeArrayList,
                               OnItemClickListener onClickListener, OnItemLongClickListener onLongClickListener) {
        this.context = context;
        this.memeArrayList = memeArrayList;
        this.onClickListener = onClickListener;
        this.onLongClickListener = onLongClickListener;
    }

    // 1. This method will be called to build a ViewHolder using cell layout
    @Override
    public RecyclerViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Called automatically when instantiating the recycler view, we don't have to call it.
        // Creates a View and passes it to RecyclerViewViewHolder.
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.custom_cell,
                        parent,
                        false);
        return new RecyclerViewViewHolder(itemView);
    }

    // 2. Inner class representing the custom cell.
    public class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
        // A ViewHolder describes an item view and metadata about its place in the Recycler View.
        // ViewHolder belongs to the adapter.
        ImageView imageViewMemeImage;
        TextView textViewMemeName;

        // Constructor:
        public RecyclerViewViewHolder(View itemView) {
            // itemView is a cell view. Comes from Layout inflater.
            super(itemView);
            imageViewMemeImage     = itemView.findViewById(R.id.meme_image);
            textViewMemeName    = itemView.findViewById(R.id.meme_name);
        }
        // Cell still hasn't been populated with any values.
    }

    // 3. How many items will be in this RecyclerView List
    @Override
    public int getItemCount() {
        return memeArrayList.size();
    }

    /*
    4. This method will be called over and over, when RecyclerView wants to build a cell
        for specific position from a ViewHolder object and populate its items (like textViews).
        It is like the cellForRowAt function in iOS.
        Pass the appropriate Model item to onBindViewHolder method to fill up ViewHolder or
        cell layout elements with Model object data.
     */

    @Override
    public void onBindViewHolder(RecyclerViewViewHolder viewHolder, int position) {

        Meme meme = memeArrayList.get(position);
        // position corresponds to index in array list
        // Current meme = meme object at [position].

        viewHolder.textViewMemeName.setText(meme.getName());
        Util.loadImage(viewHolder.imageViewMemeImage, meme.getUrl());

        viewHolder.imageViewMemeImage.setOnClickListener(view -> {
            onClickListener.onItemClick(meme);
        });

        viewHolder.imageViewMemeImage.setOnLongClickListener(view -> {
            onLongClickListener.onLongClick(meme);
            return true;
        });
    }

    public interface OnItemClickListener {
        void onItemClick(Meme meme);
    }

    public interface OnItemLongClickListener {
        void onLongClick(Meme meme);
    }
}
