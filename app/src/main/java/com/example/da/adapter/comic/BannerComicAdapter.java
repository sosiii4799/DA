package com.example.da.adapter.comic;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.da.R;
import com.example.da.activity.Comic.ComicChapterActivity;
import com.example.da.entity.obj.comic.ComicQC;
import com.example.da.util.Constant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BannerComicAdapter extends PagerAdapter {

    Context mCtx;
    ArrayList<ComicQC> arrBanner;

    public BannerComicAdapter(Context mCtx, ArrayList<ComicQC> arrBanner) {
        this.mCtx = mCtx;
        this.arrBanner = arrBanner;
    }

    @Override
    public int getCount() {
        return arrBanner.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.dong_banner_chinh, null);

        ImageView imgBanner = view.findViewById(R.id.bgBannerChinh);

        Picasso.with(mCtx).load(Constant.HOSTING + arrBanner.get(position).getImgQC()).into(imgBanner);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, ComicChapterActivity.class);
                intent.putExtra("bannerComic", arrBanner.get(position));
                mCtx.startActivity(intent);
            }
        });
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
