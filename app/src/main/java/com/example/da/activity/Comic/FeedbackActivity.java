package com.example.da.activity.Comic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.da.R;
import com.example.da.entity.obj.comic.Account;
import com.example.da.entity.obj.comic.PhanHoi;
import com.example.da.retrofit2.APIService;
import com.example.da.retrofit2.DataService;
import com.example.da.util.GlobalApplication;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackActivity extends AppCompatActivity {

    ImageView sao1, sao2, sao3, sao4, sao5;
    EditText PhanHoi;
    Button GuiPhanHoi;
    GlobalApplication globalApplication;
    Account account;
    ArrayList<PhanHoi> arrPH;
    String Cmt="";
    int DanhGia = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        globalApplication = (GlobalApplication) getApplication();
        account = globalApplication.account;
        AnhXa();
        getData();
        SetUpSao();
        GuiPhanHoi();
    }

    private void GuiPhanHoi() {
        GuiPhanHoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cmt = PhanHoi.getText().toString();
                DataService dataService = APIService.getService();
                if (Cmt.length() > 0){
                    Call<String> call = dataService.DanhGia(account.getIdAccount(), Cmt, String.valueOf(DanhGia));
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });

                    startActivity(new Intent(FeedbackActivity.this, ComicActivity.class));
                }else if (Cmt.length() == 0){
                    Call<String> call = dataService.DanhGiaSao(account.getIdAccount(), String.valueOf(DanhGia));
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                    startActivity(new Intent(FeedbackActivity.this, ComicActivity.class));
                }
            }
        });

    }

    private void SetUpSao() {
        sao1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sao1.setImageResource(R.drawable.saovang);
                sao2.setImageResource(R.drawable.saoden);
                sao3.setImageResource(R.drawable.saoden);
                sao4.setImageResource(R.drawable.saoden);
                sao5.setImageResource(R.drawable.saoden);

                DanhGia = 1;
            }
        });
        sao2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sao1.setImageResource(R.drawable.saovang);
                sao2.setImageResource(R.drawable.saovang);
                sao3.setImageResource(R.drawable.saoden);
                sao4.setImageResource(R.drawable.saoden);
                sao5.setImageResource(R.drawable.saoden);

                DanhGia = 2;
            }
        });
        sao3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sao1.setImageResource(R.drawable.saovang);
                sao2.setImageResource(R.drawable.saovang);
                sao3.setImageResource(R.drawable.saovang);
                sao4.setImageResource(R.drawable.saoden);
                sao5.setImageResource(R.drawable.saoden);

                DanhGia = 3;
            }
        });
        sao4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sao1.setImageResource(R.drawable.saovang);
                sao2.setImageResource(R.drawable.saovang);
                sao3.setImageResource(R.drawable.saovang);
                sao4.setImageResource(R.drawable.saovang);
                sao5.setImageResource(R.drawable.saoden);

                DanhGia = 4;
            }
        });
        sao5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sao1.setImageResource(R.drawable.saovang);
                sao2.setImageResource(R.drawable.saovang);
                sao3.setImageResource(R.drawable.saovang);
                sao4.setImageResource(R.drawable.saovang);
                sao5.setImageResource(R.drawable.saovang);

                DanhGia = 5;
            }
        });
    }

    //getData
    private void getData(){
        DataService dataService = APIService.getService();
        Call<List<PhanHoi>> call = dataService.DanhGiaDD(String.valueOf(account.getIdAccount()));
        call.enqueue(new Callback<List<PhanHoi>>() {
            @Override
            public void onResponse(Call<List<PhanHoi>> call, Response<List<PhanHoi>> response) {
                arrPH = (ArrayList<PhanHoi>) response.body();
                int a = Integer.valueOf(arrPH.get(0).getDanhGia());
                switch (a){
                    case 1:
                        sao1.setImageResource(R.drawable.saovang);
                        DanhGia = 1;
                        break;
                    case 2:
                        sao1.setImageResource(R.drawable.saovang);
                        sao2.setImageResource(R.drawable.saovang);
                        DanhGia = 2;
                        break;
                    case 3:
                        sao1.setImageResource(R.drawable.saovang);
                        sao2.setImageResource(R.drawable.saovang);
                        sao3.setImageResource(R.drawable.saovang);
                        DanhGia = 3;
                        break;
                    case 4:
                        sao1.setImageResource(R.drawable.saovang);
                        sao2.setImageResource(R.drawable.saovang);
                        sao3.setImageResource(R.drawable.saovang);
                        sao4.setImageResource(R.drawable.saovang);
                        DanhGia = 4;
                        break;
                    case 5:
                        sao1.setImageResource(R.drawable.saovang);
                        sao2.setImageResource(R.drawable.saovang);
                        sao3.setImageResource(R.drawable.saovang);
                        sao4.setImageResource(R.drawable.saovang);
                        sao5.setImageResource(R.drawable.saovang);
                        DanhGia = 5;
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<com.example.da.entity.obj.comic.PhanHoi>> call, Throwable t) {

            }
        });
    }

    private void AnhXa() {
        sao1 = findViewById(R.id.sao1);
        sao2 = findViewById(R.id.sao2);
        sao3 = findViewById(R.id.sao3);
        sao4 = findViewById(R.id.sao4);
        sao5 = findViewById(R.id.sao5);
        PhanHoi = findViewById(R.id.PhanHoi);
        GuiPhanHoi = findViewById(R.id.GuiPhanHoi);
    }

    public void backPhanHoi(View view) {
        finish();
    }
}
