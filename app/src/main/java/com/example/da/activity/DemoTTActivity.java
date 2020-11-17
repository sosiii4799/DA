package com.example.da.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.da.R;
import com.example.da.entity.Manager.thoiTietManager;
import com.example.da.util.Constant;
import com.example.da.util.Server;

import org.json.JSONException;

public class DemoTTActivity extends AppCompatActivity {

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Server server = new Server();
            final String code = server.sendRequest(1, "http://127.0.0.1:5000/api/data", new String[][]{});
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        thoiTietManager thoiTietManager = new thoiTietManager(code);
                        Toast.makeText(DemoTTActivity.this, thoiTietManager.lstTT.size() + "n", Toast.LENGTH_LONG).show();
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
        setContentView(R.layout.activity_demo_t_t);

//        Thread th = new Thread(runnable);
//        th.start();
    }
}
