package com.example.da.activity.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Cov19Activity extends AppCompatActivity {


    String cases, casesVN, deaths, deathsVN, recovered, recoveredVN;
    TextView NhiemBenh,TuVong, BinhPhuc, TTDB, VN, TG, XemAll;
    ImageView imgVN, imgTG, imgTestVN, imgTesstTG;
    RelativeLayout layoutVN, layoutTG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cov19);
        new ReadJson().execute("https://code.junookyo.xyz/api/ncov-moh/data.json");
        AnhXa();
        SetUp();
    }

    private void SetUp() {

        NhiemBenh.setText(casesVN);
        TuVong.setText(deathsVN);
        BinhPhuc.setText(recoveredVN);
        layoutVN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //img
                imgVN.setBackgroundResource(R.drawable.bg_vientron);
                VN.setBackgroundResource(R.drawable.bg_tronhong);
                imgTestVN.setBackgroundResource(R.drawable.bg_tronhong);
                imgTG.setBackgroundResource(R.drawable.bg_trongxam);
                TG.setBackgroundResource(R.drawable.bg_trongxam);
                imgTesstTG.setBackgroundResource(R.drawable.bg_trongxam);
                //text
                TTDB.setText(R.string.TTVietNam);
                NhiemBenh.setText(casesVN);
                TuVong.setText(deathsVN);
                BinhPhuc.setText(recoveredVN);
            }
        });

        layoutTG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //img
                imgTG.setBackgroundResource(R.drawable.bg_blue);
                TG.setBackgroundResource(R.drawable.bg_bluenhat);
                imgTesstTG.setBackgroundResource(R.drawable.bg_bluenhat);
                imgVN.setBackgroundResource(R.drawable.bg_trongxam);
                VN.setBackgroundResource(R.drawable.bg_trongxam);
                imgTestVN.setBackgroundResource(R.drawable.bg_trongxam);
                //text
                TTDB.setText(R.string.TTTheGioi);
                NhiemBenh.setText(cases);
                TuVong.setText(deaths);
                BinhPhuc.setText(recovered);

            }
        });

        XemAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://ncovi.vnpt.vn/views/ncovi_detail.html";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        layoutVN = findViewById(R.id.layoutVN);
        layoutTG = findViewById(R.id.layoutTG);
        NhiemBenh = findViewById(R.id.NhiemBenh);
        TuVong = findViewById(R.id.TuVong);
        BinhPhuc = findViewById(R.id.BinhPhuc);
        TTDB = findViewById(R.id.TTDB);
        VN = findViewById(R.id.VN);
        TG = findViewById(R.id.TG);
        imgVN = findViewById(R.id.imgVN);
        imgTG = findViewById(R.id.imgTG);
        imgTestVN = findViewById(R.id.imgtestVN);
        imgTesstTG = findViewById(R.id.imgtestTG);
        XemAll = findViewById(R.id.txtXemAll);
    }

    public void backCov19(View view) {
        finish();
    }

    public void KhaiBaoYTe(View view) {
        String url = "https://tokhaiyte.vn";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    public void HuongDan(View view) {
        String url = "https://ncovi.vnpt.vn/views/huongdan.html";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private class ReadJson extends AsyncTask<String, Void, String> {

        StringBuffer conten = new StringBuffer();

        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null){
                    conten.append(line);
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return conten.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(s);

                JSONObject data = object.getJSONObject("data");

                JSONObject global = data.getJSONObject("global");
                cases = global.getString("cases");
                deaths = global.getString("deaths");
                recovered = global.getString("recovered");

                JSONObject vietnam = data.getJSONObject("vietnam");
                casesVN = vietnam.getString("cases");
                deathsVN = vietnam.getString("deaths");
                recoveredVN = vietnam.getString("recovered");

            } catch (JSONException e) {

            }
        }
    }

}
