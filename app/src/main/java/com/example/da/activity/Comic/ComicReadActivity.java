package com.example.da.activity.Comic;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da.R;
import com.example.da.adapter.comic.ReadAdapter;
import com.example.da.entity.obj.comic.Account;
import com.example.da.entity.obj.comic.ComicChapter;
import com.example.da.entity.obj.comic.ComicRead;
import com.example.da.retrofit2.APIService;
import com.example.da.retrofit2.DataService;
import com.example.da.util.GlobalApplication;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComicReadActivity extends AppCompatActivity {

    ComicChapter comicChapter;
    Account account;
    Toolbar ToolbarComicRead;
    RecyclerView lstReadComic;
    ArrayList<ComicRead> reads;

    GlobalApplication globalApplication;
    ReadAdapter readAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_read);
        globalApplication = (GlobalApplication) getApplication();

        AnhXa();
        checkUpdateRead();
        GetDataRead(comicChapter.getIdChapter());
    }

    private void checkUpdateRead() {

        String idAccount, idChapter;
        if (globalApplication.account == null){
            idAccount = "";
            idChapter = comicChapter.getIdChapter();
        }else {
            account = globalApplication.account;
            idAccount = String.valueOf(account.getIdAccount());
            idChapter = comicChapter.getIdChapter();
        }
        DataService dataService = APIService.getService();
        Call<String> call = dataService.UpdateReading(idAccount, idChapter);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    private void GetDataRead(String idChapter) {
        DataService dataService = APIService.getService();
        Call<List<ComicRead>> call = dataService.GetReadComic(idChapter);
        call.enqueue(new Callback<List<ComicRead>>() {
            @Override
            public void onResponse(Call<List<ComicRead>> call, Response<List<ComicRead>> response) {
                reads = (ArrayList<ComicRead>) response.body();
                ComicReadActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        readAdapter = new ReadAdapter(ComicReadActivity.this, reads);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(ComicReadActivity.this);
                        layoutManager.setOrientation(RecyclerView.VERTICAL);
                        lstReadComic.setLayoutManager(layoutManager);
                        lstReadComic.setAdapter(readAdapter);
                    }
                });

            }

            @Override
            public void onFailure(Call<List<ComicRead>> call, Throwable t) {

            }
        });
    }

    private void AnhXa() {
        Bundle bundle = getIntent().getBundleExtra("dataChapter");
        comicChapter = (ComicChapter) bundle.getSerializable("Chapter");

        lstReadComic = findViewById(R.id.lstReadComic);
        ToolbarComicRead = findViewById(R.id.ToolbarComicRead);

        setSupportActionBar(ToolbarComicRead);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(comicChapter.getChapterComic());
        ToolbarComicRead.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
