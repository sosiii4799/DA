package com.example.da.entity.Manager;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class ResultEntity {
    int id;
    String message;

    public ResultEntity(String data) throws JSONException {
        try {
            JSONObject rootJSON = new JSONObject(data);
            this.id = rootJSON.getInt("id");
            this.message = rootJSON.getString("message");
        } catch (Exception ex){
            Log.e(getClass().getName(), "Lỗi: DỮ LIỆU JSON");
        }
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
