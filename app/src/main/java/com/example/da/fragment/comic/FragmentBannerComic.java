package com.example.da.fragment.comic;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.da.R;
import com.example.da.adapter.comic.BannerComicAdapter;
import com.example.da.entity.obj.comic.ComicQC;
import com.example.da.retrofit2.APIService;
import com.example.da.retrofit2.DataService;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentBannerComic extends Fragment {

    View view;
    ViewPager viewPageComic;
    CircleIndicator circleIndicatorComic;

    BannerComicAdapter bannerComicAdapter;

    Handler handler;
    Runnable runnable;
    int item;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_banner_comic, container, false);
        AnhXa();
        GetData();

        return view;
    }

    private void GetData() {
        DataService dataService = APIService.getService();
        Call<List<ComicQC>> call = dataService.GetDataBannerComic();
        call.enqueue(new Callback<List<ComicQC>>() {
            @Override
            public void onResponse(Call<List<ComicQC>> call, Response<List<ComicQC>> response) {
                ArrayList<ComicQC>  arrQC = (ArrayList<ComicQC>) response.body();
                bannerComicAdapter = new BannerComicAdapter(getActivity(), arrQC);
                viewPageComic.setAdapter(bannerComicAdapter);
                circleIndicatorComic.setViewPager(viewPageComic);
                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        item = viewPageComic.getCurrentItem();
                        item++;
                        if (item >= viewPageComic.getAdapter().getCount()){
                            item = 0;
                        }
                        viewPageComic.setCurrentItem(item, true);
                        handler.postDelayed(runnable, 4500);
                    }
                };
                handler.postDelayed(runnable, 4500);
            }

            @Override
            public void onFailure(Call<List<ComicQC>> call, Throwable t) {

            }
        });
    }

    private void AnhXa() {
        viewPageComic = view.findViewById(R.id.viewPageComic);
        circleIndicatorComic = view.findViewById(R.id.circleIndicatorComic);
    }
}
