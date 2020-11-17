package com.example.da.entity.Manager;

import com.example.da.entity.ThoiTiet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class thoiTietManager {

    public ArrayList<ThoiTiet> lstTT = new ArrayList<>();
    public thoiTietManager(String data) throws JSONException {
        JSONArray rootJSON = new JSONArray(data);
        for (int i = 0; i < rootJSON.length(); i++) {
            JSONObject tt = rootJSON.getJSONObject(i);
            String day = tt.getString("day");
            String maxT = tt.getString("maxT");
            String minT = tt.getString("minT");
            String temp = tt.getString("temp");

            ThoiTiet thoiTiet = new ThoiTiet(day, maxT, minT, temp);
            lstTT.add(thoiTiet);
        }
    }
}
