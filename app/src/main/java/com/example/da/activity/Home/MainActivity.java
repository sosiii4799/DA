package com.example.da.activity.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.da.R;
import com.example.da.activity.Comic.ComicActivity;
import com.example.da.activity.DemoTTActivity;
import com.example.da.entity.Manager.UserManager;
import com.example.da.entity.obj.comic.Account;
import com.example.da.util.Constant;
import com.example.da.util.GlobalApplication;
import com.example.da.util.Server;
import com.facebook.FacebookSdk;

import org.json.JSONException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {


    GlobalApplication globalApplication;
    String TK,MK;
    final Runnable taskLogin = new Runnable() {
        @Override
        public void run() {

            String data[][] = {
                    {"username", TK},
                    {"password", MK}
            };

            Server server = new Server();
            final String result = server.sendRequest(1, Constant.HOSTING_API + Constant.LOGIN, data);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        UserManager manager = new UserManager(result);
                        // Nếu đăng nhập sai
                        if (manager.lstUser.size() == 0) {
                        } else {
                            // Lấy đối tượng Đăng nhập
                            Account account = manager.lstUser.get(0);
                            if(globalApplication == null){
                                globalApplication = (GlobalApplication) getApplicationContext();
                            }
                            globalApplication.account = account;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        TK = prefs.getString("User", "");
        MK = prefs.getString("Pass", "");

        Thread th = new Thread(taskLogin);
        th.start();



        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    Intent intent = new Intent(getBaseContext(), ComicActivity.class);
                    startActivity(intent);
                    finish();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
