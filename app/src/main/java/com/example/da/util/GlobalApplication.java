package com.example.da.util;

import android.app.Application;

import com.example.da.entity.obj.comic.Account;
import com.example.da.entity.obj.comic.ComicBook;

public class GlobalApplication extends Application {

    public Account account;
    public ComicBook comic;

    public int check = 1;
}
