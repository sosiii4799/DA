package com.example.da.adapter.comic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.da.R;
import com.example.da.entity.obj.comic.ComicChapter;
import com.example.da.retrofit2.OtherUltil;

import java.util.ArrayList;
import java.util.List;

public class ChapterAdapter extends ArrayAdapter<ComicChapter> {
    private Context ctx;
    private ArrayList<ComicChapter> arr;
    public ChapterAdapter(@NonNull Context context, int resource, @NonNull List<ComicChapter> objects) {
        super(context, resource, objects);
        this.ctx = context;
        this.arr = new ArrayList<>(objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_chapter, null);
        }

            ComicChapter chapterC = this.arr.get(position);

            TextView txtSoChapter = (TextView) convertView.findViewById(R.id.txtSoChapterComic);
            TextView txtNgayViet = (TextView) convertView.findViewById(R.id.txtNgayVietComic);

            txtSoChapter.setText(chapterC.getChapterComic());
            txtNgayViet.setText(OtherUltil.convertDateFromMysql(chapterC.getDayCreated()) + "");

        return convertView;
    }
}
