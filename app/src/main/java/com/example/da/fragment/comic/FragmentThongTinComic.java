package com.example.da.fragment.comic;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.da.R;
import com.example.da.entity.obj.comic.ComicBook;
import com.example.da.entity.obj.comic.ComicQC;

public class FragmentThongTinComic extends Fragment {

    View view;
    ComicBook comic;
    ComicQC comicQC;
    TextView txtThongtinComic;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_thongtin_comic, container, false);

        txtThongtinComic = view.findViewById(R.id.txtThongtinComic);
        //nhận data intent

        Intent intent = getActivity().getIntent();
        if (intent.hasExtra("dataComicChapter")){

            comic = (ComicBook) intent.getSerializableExtra("dataComicChapter");
            txtThongtinComic.setText(comic.getPlot());

        }else if(intent.hasExtra("bannerComic")){

            comicQC = (ComicQC) intent.getSerializableExtra("bannerComic");
            txtThongtinComic.setText(comicQC.getPlot());
        }

        return view;
    }
}
