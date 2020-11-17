package com.example.da.adapter.comic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da.R;
import com.example.da.entity.obj.comic.ComicRead;
import com.example.da.util.Constant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReadAdapter extends RecyclerView.Adapter<ReadAdapter.ViewHolder>{

    Context context;
    ArrayList<ComicRead> comicReads;

    public ReadAdapter(Context context, ArrayList<ComicRead> comicReads) {
        this.context = context;
        this.comicReads = comicReads;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_read, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.with(context).load(Constant.HOSTING + comicReads.get(position).getLinkAnhChapter()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return comicReads.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgRead);
        }
    }
}
