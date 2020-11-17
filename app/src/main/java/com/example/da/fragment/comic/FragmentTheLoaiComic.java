package com.example.da.fragment.comic;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da.R;
import com.example.da.activity.Comic.ComicChapterActivity;
import com.example.da.adapter.comic.ComicAdapter;
import com.example.da.adapter.comic.TheLoaiAdapter;
import com.example.da.entity.obj.comic.ComicBook;
import com.example.da.entity.obj.comic.TheLoaiComic;
import com.example.da.retrofit2.APIService;
import com.example.da.retrofit2.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentTheLoaiComic extends Fragment{

    View view;
    TheLoaiAdapter theLoaiAdapter;
    ArrayList<TheLoaiComic> arrTheLoaiComic;

    ArrayList arrComicTheLoai;
    ListView LstTruyenTheoTheLoai;
    RecyclerView RecTheLoai;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_theloai_comic, container, false);
        AnhXa();
        GetDataTheLoai();

        return view;
    }

    private void GetDataTheLoai() {
        final DataService dataService = APIService.getService();
        Call<List<TheLoaiComic>> call = dataService.GetDataTheLoaiComic();
        call.enqueue(new Callback<List<TheLoaiComic>>() {
            @Override
            public void onResponse(Call<List<TheLoaiComic>> call, Response<List<TheLoaiComic>> response) {
                arrTheLoaiComic = (ArrayList<TheLoaiComic>) response.body();
                theLoaiAdapter = new TheLoaiAdapter(getActivity(), arrTheLoaiComic);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                RecTheLoai.setLayoutManager(linearLayoutManager);
                RecTheLoai.setAdapter(theLoaiAdapter);

                theLoaiAdapter.setItemClickListener(new TheLoaiAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int position) {
                        GetDataComicTheLoai(arrTheLoaiComic.get(position).getGenre());
                    }
                });
            }

            @Override
            public void onFailure(Call<List<TheLoaiComic>> call, Throwable t) {

            }
        });
    }

    private void GetDataComicTheLoai(String genre) {
        DataService dataService = APIService.getService();
        Call<List<ComicBook>> call = dataService.GetComicGenre(genre);
        call.enqueue(new Callback<List<ComicBook>>() {
            @Override
            public void onResponse(Call<List<ComicBook>> call, Response<List<ComicBook>> response) {
                arrComicTheLoai = (ArrayList) response.body();
                ComicAdapter comicAdapter = new ComicAdapter(getActivity(), R.layout.item_truyen, arrComicTheLoai);
                LstTruyenTheoTheLoai.setAdapter(comicAdapter);

                LstTruyenTheoTheLoai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ComicBook comic = (ComicBook) arrComicTheLoai.get(position);
                        Intent chapter = new Intent(getActivity(), ComicChapterActivity.class);
                        chapter.putExtra("dataComicChapter",comic);
                        startActivity(chapter);
                    }
                });

            }

            @Override
            public void onFailure(Call<List<ComicBook>> call, Throwable t) {

            }
        });
    }

    private void AnhXa() {
        LstTruyenTheoTheLoai = view.findViewById(R.id.LstTruyenTheoTheLoai);
        RecTheLoai = view.findViewById(R.id.RecTheLoai);
    }
}
