package com.example.da.adapter.comic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.da.R;
import com.example.da.entity.obj.comic.ComicBook;
import com.example.da.util.Constant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ComicAdapter extends ArrayAdapter<ComicBook> implements Filterable {
    private Context ctx;
    private List<ComicBook> arrTT;
    private ArrayList<ComicBook> arrAll;
    public ComicAdapter(@NonNull Context context, int resource, @NonNull List<ComicBook> objects) {
        super(context, resource, objects);
        this.ctx = context;
        this.arrTT = objects;

        this.arrAll = new ArrayList<ComicBook>();
        this.arrAll.addAll(arrTT);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_truyen, null);
        }
        if(arrTT.size() > 0){
            ComicBook comic = this.arrTT.get(position);
            TextView tenTruyen = (TextView) convertView.findViewById(R.id.txtTenTruyen);
            TextView theLoai= (TextView) convertView.findViewById(R.id.txtTheLoai);
            TextView likeComic= (TextView) convertView.findViewById(R.id.txtlikeComic);
            TextView viewComic= (TextView) convertView.findViewById(R.id.txtViewComic);
            ImageView imgAnhTruyen = (ImageView) convertView.findViewById(R.id.imgAnhTruyen);

            tenTruyen.setText(comic.getNameComic());
            theLoai.setText(comic.getGenre());
            likeComic.setText(comic.getLikeComic());
            viewComic.setText(comic.getViewComic());
            Picasso.with(ctx).load(Constant.HOSTING + comic.getImgComic()).into(imgAnhTruyen);
        }
        return convertView;
    }



    public void Filter(String charText){
        charText = charText.toLowerCase().toLowerCase(Locale.getDefault());
        arrTT.clear();

        if (charText.length() == 0){
            arrTT.addAll(arrAll);
        }else {
            for (ComicBook item : arrAll){
                if (item.getNameComic().toLowerCase(Locale.getDefault()).contains(charText)){
                    arrTT.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }


//    @NonNull
//    @Override
//    public Filter getFilter() {
//        return filter;
//    }
//
//    private Filter filter= new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            List<ComicBook> books = new ArrayList<>();
//            if (constraint == null || constraint.length() == 0){
//                books.addAll(arrAll);
//            }else {
//                String fil = constraint.toString().toLowerCase().trim();
//
//                for (ComicBook item: arrAll){
//                    if (item.getNameComic().toLowerCase().contains(fil));
//                    books.add(item);
//                }
//            }
//
//            FilterResults results = new FilterResults();
//            results.values = books;
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            arrTT.clear();
//            arrTT.addAll((List)results.values);
//            notifyDataSetChanged();
//        }
//    };

}
