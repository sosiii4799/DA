package com.example.da.fragment.comic;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.da.R;
import com.example.da.activity.Comic.ComicReadActivity;
import com.example.da.activity.Home.LoginAppActivity;
import com.example.da.adapter.comic.ChapterAdapter;
import com.example.da.entity.obj.comic.Account;
import com.example.da.entity.obj.comic.ComicBook;
import com.example.da.entity.obj.comic.ComicChapter;
import com.example.da.entity.obj.comic.ComicQC;
import com.example.da.retrofit2.APIService;
import com.example.da.retrofit2.DataService;
import com.example.da.util.GlobalApplication;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentChapterComic extends Fragment {

    ComicBook comic;
    ComicQC comicQC;
    Account account = new Account();
    GlobalApplication globalApplication;

    View view;
    ListView listView;
    ArrayList<ComicChapter> arrChapter, arrT;
    ChapterAdapter chapterAdapter;
    ImageView imgLoveComic;

    RelativeLayout layoutReading;
    TextView txtReading;

    public Boolean check;
    int isCheck, likeComic;


    String idAccount, idComic;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_chapter_comic, container, false);
        listView = view.findViewById(R.id.lstChapterComic);
        globalApplication = (GlobalApplication) getActivity().getApplication();

        init();
        checkRead();

        if (globalApplication.account == null){
            imgLoveComic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    XacNhanDN();
                }
            });
        }else {
            account = globalApplication.account;
            CheckLike();
        }

        return view;
    }

    private void checkRead() {

        if (globalApplication.account == null){
            idAccount = "";
            if (comic == null){
                idComic = comicQC.getIdComic();
            }else {
                idComic = comic.getIdComic();
            }
        }else {
            account = globalApplication.account;
            idAccount = String.valueOf(account.getIdAccount());
            if (comic == null){
                idComic = comicQC.getIdComic();
            }else {
                idComic = comic.getIdComic();
            }
        }

        DataService dataService = APIService.getService();
        Call<List<ComicChapter>> call = dataService.CheckReading(idAccount,idComic);
        call.enqueue(new Callback<List<ComicChapter>>() {
            @Override
            public void onResponse(Call<List<ComicChapter>> call, Response<List<ComicChapter>> response) {
                arrT = (ArrayList<ComicChapter>) response.body();
                String a = "Chapter 1";
                if (!a.equals(arrT.get(0).getChapterComic())){
                    txtReading.setText(R.string.DocTiep);
                }
            }

            @Override
            public void onFailure(Call<List<ComicChapter>> call, Throwable t) {

            }
        });

        layoutReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComicChapter comicChapter = arrT.get(0);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Chapter", comicChapter);
                Intent chapter = new Intent(getActivity(), ComicReadActivity.class);
                chapter.putExtra("dataChapter",bundle);
                startActivity(chapter);
            }
        });


    }

    private void XacNhanDN() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.ThongBao);
        builder.setMessage(R.string.BanCanDangNhapDeTheoDoi);
        builder.setPositiveButton(R.string.DangNhap, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity(), LoginAppActivity.class);
                getActivity().startActivity(intent);
            }
        });
        builder.setNegativeButton(R.string.Huy, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void init() {
        imgLoveComic = view.findViewById(R.id.imgLoveComic);
        layoutReading = view.findViewById(R.id.layoutReading);
        txtReading = view.findViewById(R.id.txtReading);

        Intent intent = getActivity().getIntent();
        if (intent.hasExtra("dataComicChapter")){
            comic = (ComicBook) intent.getSerializableExtra("dataComicChapter");
            GetDataComic(comic.getIdComic());

        }else if(intent.hasExtra("bannerComic")){

            comicQC = (ComicQC) intent.getSerializableExtra("bannerComic");
            GetDataComic(comicQC.getIdComic());
        }
    }

    private void GetDataComic(String idComic) {
        DataService dataService = APIService.getService();
        Call<List<ComicChapter>> call = dataService.GetChapterComic(idComic);
        call.enqueue(new Callback<List<ComicChapter>>() {
            @Override
            public void onResponse(Call<List<ComicChapter>> call, Response<List<ComicChapter>> response) {
                arrChapter = (ArrayList<ComicChapter>) response.body();
                chapterAdapter = new ChapterAdapter(getActivity(), R.layout.item_chapter, arrChapter);
                listView.setAdapter(chapterAdapter);


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Bundle bundle = new Bundle();
                        ComicChapter comicChapter = arrChapter.get(position);
                        bundle.putSerializable("Chapter", comicChapter);
                        Intent chapter = new Intent(getActivity(), ComicReadActivity.class);
                        chapter.putExtra("dataChapter",bundle);
                        startActivity(chapter);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<ComicChapter>> call, Throwable t) {

            }
        });
    }

    private void CheckLike() {
        DataService dataService = APIService.getService();
        final Call<String> call = dataService.CheckComicLike(Integer.valueOf(idAccount), Integer.valueOf(idComic));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String code ="";
                code = response.body();
                if (code.equalsIgnoreCase("yes")){
                    check = true;
                    imgLoveComic.setImageResource(R.drawable.iconloved);
                }else {
                    check = false;
                    imgLoveComic.setImageResource(R.drawable.iconunlove);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

        imgLoveComic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check == true){
                    isCheck = 0;
                    likeComic = -1;
                    imgLoveComic.setImageResource(R.drawable.iconunlove);
                }else if (check == false){
                    isCheck = 1;
                    likeComic = 1;
                    imgLoveComic.setImageResource(R.drawable.iconloved);
                }

                doiIconLike();

                DataService dataService = APIService.getService();
                Call<String> call = dataService.UpdateLikeComic(String.valueOf(likeComic), idComic);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String code = "";
                        code = response.body();
                        if (code.equalsIgnoreCase("Success")){
                            DataService dataService1 = APIService.getService();
                            Call<String> call2 = dataService1.GetSLLike(idComic);
                            call2.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    String code = "";
                                    code = response.body();
                                    TextView txtlikeComicChapter = getActivity().findViewById(R.id.txtlikeComicChapter);
                                    txtlikeComicChapter.setText(code);
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });

            }
        });
    }

    private void doiIconLike() {
        DataService dataService1 = APIService.getService();
        Call<String> call1 = dataService1.InsertComicLike(Integer.valueOf(idAccount), Integer.valueOf(idComic), isCheck);
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String code = "";
                code = response.body();
                if (code.equalsIgnoreCase("add")){
                    check = !check;
                }else if (code.equalsIgnoreCase("del")){
                    check = !check;
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
