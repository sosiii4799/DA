package com.example.da.fragment.comic;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.da.R;
import com.example.da.activity.Comic.ComicChapterActivity;
import com.example.da.activity.Comic.RankActivity;
import com.example.da.activity.Comic.SearchActivity;
import com.example.da.adapter.comic.ComicAdapter;
import com.example.da.adapter.comic.TBadapter;
import com.example.da.entity.obj.comic.Account;
import com.example.da.entity.obj.comic.PhanHoi;
import com.example.da.entity.obj.comic.ComicBook;
import com.example.da.retrofit2.APIService;
import com.example.da.retrofit2.DataService;
import com.example.da.util.GlobalApplication;
import com.example.da.util.checkConnect;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHomeComic extends Fragment {

    View view;
    ListView lstComicBook;
    ImageView TB;

    ComicAdapter comicAdapter;
    ArrayList<ComicBook> arrbooks;
    LinearLayout layoutTK, layoutRank, layoutTB, layoutThongBao;
    GlobalApplication globalApplication;
    Account account ;
    ArrayList<PhanHoi> arrPH,arr = new ArrayList<>();

    ListView lstTB;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_comic, container, false);
        if (checkConnect.haveNetworkConnection(getContext())){

            AnhXa();
            GetData();
            checkTB();
            getTB();

            SetUp();


        }else {

        }
        return view;
    }

    private void SetUp() {
    }

    private void checkTB() {

        globalApplication = (GlobalApplication) getActivity().getApplication();
        if (globalApplication.account != null ){
            account = globalApplication.account;
            DataService dataService = APIService.getService();
            Call<String> call = dataService.GetTBDoc(account.getIdAccount());
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String code;
                    if (response.body() != null){
                        code = response.body();
                        if (code.equalsIgnoreCase("yes")){
                            TB.setImageResource(R.drawable.tb);
                        }else {
                            TB.setImageResource(R.drawable.notb);
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }
    }

    private void getTB() {
        //getdata
        globalApplication = (GlobalApplication) getActivity().getApplication();
        if (globalApplication.account != null ){
            account = globalApplication.account;
            DataService dataService = APIService.getService();
            Call<List<PhanHoi>> call = dataService.DanhGiaDD(String.valueOf(account.getIdAccount()));
            call.enqueue(new Callback<List<PhanHoi>>() {
                @Override
                public void onResponse(Call<List<PhanHoi>> call, Response<List<PhanHoi>> response) {
                    arrPH = (ArrayList<PhanHoi>) response.body();
                    try {
                        for (PhanHoi i: arrPH) {
                            if (!i.getPhanHoi().equalsIgnoreCase("")){
                                arr.add(i);
                            }
                        }
                        if (arr.size() > 0){

                            TBadapter tBadapter = new TBadapter(getActivity(), R.layout.item_tb, arr);
                            lstTB.setAdapter(tBadapter);

                            layoutTB.setOnClickListener(new View.OnClickListener() {
                                int check = 1;
                                @Override
                                public void onClick(View v) {
                                    TB.setImageResource(R.drawable.notb);
                                    if (check % 2 == 1){
                                        check += 1;
                                        layoutThongBao.setVisibility(View.VISIBLE);
                                    }else if (check % 2 == 0){
                                        check += 1;
                                        layoutThongBao.setVisibility(View.GONE);
                                    }
                                }
                            });

                            lstTB.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                                    DataService dataService = APIService.getService();
                                    Call<String> call = dataService.GetTBChuaDoc(account.getIdAccount(), arr.get(position).getIdFeedback());
                                    call.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {

                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {

                                        }
                                    });

                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setTitle(R.string.ThongBao);
                                    builder.setMessage(arr.get(position).getPhanHoi());
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            arr.clear();
                                            getTB();
                                        }
                                    });
                                    builder.show();
                                }
                            });
                        }
                    }catch (Exception e){

                    }

                }

                @Override
                public void onFailure(Call<List<PhanHoi>> call, Throwable t) {

                }
            });
        }
    }

    private void GetData() {
        DataService dataService = APIService.getService();
        Call<List<ComicBook>> call = dataService.GetDataComicBook();
        call.enqueue(new Callback<List<ComicBook>>() {
            @Override
            public void onResponse(Call<List<ComicBook>> call, Response<List<ComicBook>> response) {
                arrbooks = (ArrayList<ComicBook>) response.body();
                comicAdapter = new ComicAdapter(getActivity(), R.layout.item_truyen, arrbooks);
                lstComicBook.setAdapter(comicAdapter);
                lstComicBook.setOnItemClickListener(openConmicChapter);
            }

            @Override
            public void onFailure(Call<List<ComicBook>> call, Throwable t) {

            }
        });
    }

    private void AnhXa() {
        layoutRank = view.findViewById(R.id.layoutRank);
        layoutTK = view.findViewById(R.id.layoutTK);
        layoutTB = view.findViewById(R.id.layoutTB);
        lstComicBook = view.findViewById(R.id.lstComicBook);
        TB = view.findViewById(R.id.TB);
        layoutThongBao = view.findViewById(R.id.layoutThongBao);
        lstTB = view.findViewById(R.id.lstTB);

        layoutRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RankActivity.class));
            }
        });

        layoutTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });
    }

    ListView.OnItemClickListener openConmicChapter = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ComicBook comic = arrbooks.get(position);
            Intent chapter = new Intent(getActivity(), ComicChapterActivity.class);
            chapter.putExtra("dataComicChapter", comic);
            startActivity(chapter);
        }
    };
}
