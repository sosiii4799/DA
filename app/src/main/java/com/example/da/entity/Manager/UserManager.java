package com.example.da.entity.Manager;

import com.example.da.entity.obj.comic.Account;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserManager {

    public ArrayList<Account> lstUser = new ArrayList<>();
    public ArrayList<Account> lstLove = new ArrayList<>();
    public UserManager(String data) throws JSONException {
        JSONArray rootJSON = new JSONArray(data);
        for (int i = 0; i < rootJSON.length(); i++) {
            JSONObject user = rootJSON.getJSONObject(i);
            String id = user.getString("idAccount");
            String username = user.getString("username");
            String password = user.getString("password");
            String lastName = user.getString("lastName");
            String name = user.getString("name");
            String phone = user.getString("phone");
            String address = user.getString("address");
            int gender = Integer.parseInt(user.getString("gender"));
            String email = user.getString("email");
            String DateOfBirth = user.getString("DateOfBirth");
            String ava = user.getString("avatar");

            Account entity = new Account(id, username, password, lastName, name, phone, address, gender, email, DateOfBirth, ava);
            Account Love = new Account(id, name, gender, DateOfBirth, ava);
            lstUser.add(entity);
            lstLove.add(Love);
        }
    }
}
