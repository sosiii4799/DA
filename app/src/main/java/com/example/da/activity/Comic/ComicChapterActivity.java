package com.example.da.activity.Comic;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.da.R;
import com.example.da.adapter.comic.ViewPageAdapter;
import com.example.da.entity.obj.comic.ComicBook;
import com.example.da.entity.obj.comic.ComicQC;
import com.example.da.fragment.comic.FragmentChapterComic;
import com.example.da.fragment.comic.FragmentThongTinComic;
import com.example.da.retrofit2.APIService;
import com.example.da.retrofit2.DataService;
import com.example.da.util.Constant;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComicChapterActivity extends AppCompatActivity {

    ComicBook comic;
    ComicQC comicQC;

    ImageView imgAnhTruyenChapter;
    TextView txtTenTruyenChapter, txtTheLoaiChapter, txtlikeComicChapter, txtTacGia, txtViewComicChapter;

    ViewPageAdapter viewPageAdapter;

    TabLayout tabLayoutComicChapter;
    ViewPager viewPageComicChapter;

    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_chapter);
        init();
        setUp();
        UpdateView();
    }

    private void UpdateView() {

        countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                DataService dataService = APIService.getService();
                Call<String> call = dataService.UpdateViewComic("1",comic.getIdComic());
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable throwable) {
                    }
                });
            }
        }.start();
    }

    private void setUp() {
        //nhận data

        Intent intent = getIntent();
        if (intent.hasExtra("dataComicChapter")){

            comic = (ComicBook) intent.getSerializableExtra("dataComicChapter");

            txtTenTruyenChapter.setText(comic.getNameComic());
            txtTheLoaiChapter.setText(comic.getGenre());
            txtlikeComicChapter.setText(comic.getLikeComic());
            txtTacGia.setText(comic.getTacgia());
            txtViewComicChapter.setText(comic.getViewComic());
            Picasso.with(ComicChapterActivity.this).load(Constant.HOSTING + comic.getImgComic()).into(imgAnhTruyenChapter);
        }else if(intent.hasExtra("bannerComic")){

            comicQC = (ComicQC) intent.getSerializableExtra("bannerComic");

            txtTenTruyenChapter.setText(comicQC.getNameComic());
            txtTheLoaiChapter.setText(comicQC.getGenre());
            txtlikeComicChapter.setText(comicQC.getLikeComic());
            txtTacGia.setText(comicQC.getTacgia());
            txtViewComicChapter.setText(comicQC.getViewComic());
            Picasso.with(ComicChapterActivity.this).load(Constant.HOSTING + comicQC.getImgComic()).into(imgAnhTruyenChapter);
        }
    }

    private void init() {
        imgAnhTruyenChapter = findViewById(R.id.imgAnhTruyenChapter);
        txtTenTruyenChapter = findViewById(R.id.txtTenTruyenChapter);
        txtTheLoaiChapter = findViewById(R.id.txtTheLoaiChapter);
        txtlikeComicChapter = findViewById(R.id.txtlikeComicChapter);
        txtViewComicChapter = findViewById(R.id.txtViewComicChapter);
        txtTacGia = findViewById(R.id.txtTacGia);

        tabLayoutComicChapter = findViewById(R.id.tabLayoutComicChapter);
        viewPageComicChapter = findViewById(R.id.viewPageComicChapter);
        //setup
        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPageAdapter.addFragment(new FragmentChapterComic(), "Chapter");
        viewPageAdapter.addFragment(new FragmentThongTinComic(), "");
        viewPageComicChapter.setAdapter(viewPageAdapter);
        tabLayoutComicChapter.setupWithViewPager(viewPageComicChapter);
        tabLayoutComicChapter.getTabAt(1).setText(R.string.TheLoai);

    }

    public void backComicChapter(View view) {
        countDownTimer.cancel();
        Intent intent = new Intent(ComicChapterActivity.this, ComicActivity.class);
        startActivity(intent);
        finish();
    }
}
