package com.example.da.activity.Comic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.da.R;
import com.example.da.adapter.comic.ComicAdapter;
import com.example.da.entity.obj.comic.ComicBook;
import com.example.da.retrofit2.APIService;
import com.example.da.retrofit2.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankActivity extends AppCompatActivity {

    ListView lstTop;
    ArrayList<ComicBook> books;
    ComicAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        AnhXa();
        getData();
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<ComicBook>> call = dataService.GetDataRank();
        call.enqueue(new Callback<List<ComicBook>>() {
            @Override
            public void onResponse(Call<List<ComicBook>> call, Response<List<ComicBook>> response) {
                books = (ArrayList<ComicBook>) response.body();
                adapter = new ComicAdapter(RankActivity.this, R.layout.item_truyen, books);
                lstTop.setAdapter(adapter);

                lstTop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ComicBook comic = books.get(position);
                        Intent chapter = new Intent(RankActivity.this, ComicChapterActivity.class);
                        chapter.putExtra("dataComicChapter", comic);
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
        lstTop = findViewById(R.id.lstTop);
    }

    public void backRank(View view) {
        finish();
    }
}
