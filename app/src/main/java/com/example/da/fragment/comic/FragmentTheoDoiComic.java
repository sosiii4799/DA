package com.example.da.fragment.comic;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.da.R;
import com.example.da.activity.Comic.ComicChapterActivity;
import com.example.da.activity.Comic.SearchActivity;
import com.example.da.activity.Home.LoginAppActivity;
import com.example.da.adapter.comic.ComicAdapter;
import com.example.da.entity.obj.comic.Account;
import com.example.da.entity.obj.comic.ComicBook;
import com.example.da.retrofit2.APIService;
import com.example.da.retrofit2.DataService;
import com.example.da.util.GlobalApplication;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentTheoDoiComic extends Fragment {

    View view;
    GlobalApplication globalApplication;
    Account account;

    ArrayList<ComicBook> arrComicLike = new ArrayList<>();
    ListView lstComicLike;

    LinearLayout LayoutNoComic, LayoutComic;
    RelativeLayout layoutDNTheoDoi;
    Button btnOpenDangNhapTheoDoi, btnSearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_theodoi_comic, container, false);
        globalApplication = (GlobalApplication) getActivity().getApplication();
        init();
        if (globalApplication.account != null){
            account = globalApplication.account;
            getComicTheoDoi(account.getIdAccount());
        }else {
            btnOpenDangNhapTheoDoi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), LoginAppActivity.class);
                    getActivity().startActivity(intent);
                }
            });
        }


        return view;
    }

    private void getComicTheoDoi(String idAccount) {
        DataService dataService = APIService.getService();
        Call<List<ComicBook>> call = dataService.GetComicLike(idAccount);
        call.enqueue(new Callback<List<ComicBook>>() {
            @Override
            public void onResponse(Call<List<ComicBook>> call, Response<List<ComicBook>> response) {
                if (response.body().size() != 0){
                    arrComicLike = (ArrayList<ComicBook>) response.body();
                    ComicAdapter comicAdapter = new ComicAdapter(getActivity(), R.layout.item_truyen, arrComicLike);
                    lstComicLike.setAdapter(comicAdapter);
                    layoutDNTheoDoi.setVisibility(View.GONE);
                    LayoutNoComic.setVisibility(View.GONE);
                    LayoutComic.setVisibility(View.VISIBLE);

                    lstComicLike.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            ComicBook comic = arrComicLike.get(position);
                            Intent chapter = new Intent(getActivity(), ComicChapterActivity.class);
                            chapter.putExtra("dataComicChapter", comic);
                            startActivity(chapter);
                        }
                    });
                }else {
                    layoutDNTheoDoi.setVisibility(View.GONE);
                    LayoutNoComic.setVisibility(View.VISIBLE);
                    LayoutComic.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<ComicBook>> call, Throwable t) {

            }
        });
    }

    private void init() {
        layoutDNTheoDoi = view.findViewById(R.id.layoutDNTheoDoi);
        LayoutNoComic = view.findViewById(R.id.LayoutNoComic);
        LayoutComic = view.findViewById(R.id.LayoutComic);
        btnOpenDangNhapTheoDoi = view.findViewById(R.id.btnOpenDangNhapTheoDoi);
        lstComicLike = view.findViewById(R.id.lstComicLike);
        btnSearch = view.findViewById(R.id.btnSearch);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });
    }
}
