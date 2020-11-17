package com.example.da.activity.Comic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

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

public class SearchActivity extends AppCompatActivity {

    SearchView searchView;
    ListView lst;

    ArrayList<ComicBook> arr;
    ComicAdapter adapter;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        AnhXa();
        getData();

        checkSearch();
    }

    private void checkSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() == 0){
                    layout.setVisibility(View.VISIBLE);
                    lst.setVisibility(View.GONE);
                }else {
                    layout.setVisibility(View.GONE);
                    lst.setVisibility(View.VISIBLE);
                    adapter.Filter(newText.trim());
                }
                return false;
            }
        });
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<ComicBook>> call = dataService.GetDataComicBook();
        call.enqueue(new Callback<List<ComicBook>>() {
            @Override
            public void onResponse(Call<List<ComicBook>> call, Response<List<ComicBook>> response) {
                arr = (ArrayList<ComicBook>) response.body();

                adapter = new ComicAdapter(SearchActivity.this, R.layout.item_truyen, arr);
                lst.setAdapter(adapter);

                lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ComicBook comic = arr.get(position);
                        Intent chapter = new Intent(SearchActivity.this, ComicChapterActivity.class);
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
        searchView = findViewById(R.id.searchView);
        lst = findViewById(R.id.lstSeach);
        layout = findViewById(R.id.layoutNoTK);
    }

    public void back(View view) {
        finish();
    }
}
