package com.example.da.adapter.comic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da.R;
import com.example.da.entity.obj.comic.TheLoaiComic;

import java.util.ArrayList;

public class TheLoaiAdapter extends RecyclerView.Adapter<TheLoaiAdapter.ViewHolder>{

    Context context;
    ArrayList<TheLoaiComic> arrGetTheLoai;
    private ItemClickListener itemClickListener;

    public TheLoaiAdapter(Context context, ArrayList<TheLoaiComic> arrGetTheLoai) {
        this.context = context;
        this.arrGetTheLoai = arrGetTheLoai;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_theloai_comic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final TheLoaiComic theLoaiComic = arrGetTheLoai.get(position);
        holder.txtTheLoaiComic.setText(theLoaiComic.getGenre());
    }

    @Override
    public int getItemCount() {
        return arrGetTheLoai.size();
    }

    public interface ItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtTheLoaiComic;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            txtTheLoaiComic = itemView.findViewById(R.id.txtTheLoaiComic);


            txtTheLoaiComic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null)
                        itemClickListener.onItemClick(itemView, getLayoutPosition());
                }
            });
        }

    }


}
