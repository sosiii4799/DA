package com.example.da.activity.Comic;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.da.R;
import com.example.da.activity.Home.Cov19Activity;
import com.example.da.adapter.comic.ViewPageAdapter;
import com.example.da.fragment.comic.FragmentHomeComic;
import com.example.da.fragment.comic.FragmentTheLoaiComic;
import com.example.da.fragment.comic.FragmentTheoDoiComic;
import com.example.da.fragment.comic.FragmentUser;
import com.example.da.util.GlobalApplication;
import com.google.android.material.tabs.TabLayout;

public class ComicActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPageAdapter viewPageAdapter;
    GlobalApplication globalApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic);
        AnhXa();
        init();


        globalApplication = (GlobalApplication) getApplication();
        if (globalApplication.check == 1){
            Cov19();
            globalApplication.check = globalApplication.check + 1;
        }
    }

    private void Cov19() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ComicActivity.this);
        builder.setTitle(R.string.ThongBao);
        builder.setMessage(R.string.TraCuuTT);
        builder.setPositiveButton(R.string.XacNhan, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ComicActivity.this, Cov19Activity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton(R.string.Huy, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ComicActivity.this);
                builder.setTitle(R.string.ThongBao);
                builder.setMessage(R.string.StayHome);
                builder.setPositiveButton(R.string.XacNhan, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
            }
        });
        builder.show();
    }

    private void init() {
        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPageAdapter.addFragment(new FragmentHomeComic(),"");
        viewPageAdapter.addFragment(new FragmentTheLoaiComic(),"");
        viewPageAdapter.addFragment(new FragmentTheoDoiComic(),"");
        viewPageAdapter.addFragment(new FragmentUser(),"");
        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.house);
        tabLayout.getTabAt(0).setText("Home");
        tabLayout.getTabAt(1).setIcon(R.drawable.list);
        tabLayout.getTabAt(1).setText(R.string.TheLoai);
        tabLayout.getTabAt(2).setIcon(R.drawable.theodoi);
        tabLayout.getTabAt(2).setText(R.string.TheoDoi);
        tabLayout.getTabAt(3).setIcon(R.drawable.account);
        tabLayout.getTabAt(3).setText(R.string.TaiKhoan);
    }

    private void AnhXa() {
        tabLayout = findViewById(R.id.tabLayoutComic);
        viewPager = findViewById(R.id.viewPageComicChinh);
    }
}
