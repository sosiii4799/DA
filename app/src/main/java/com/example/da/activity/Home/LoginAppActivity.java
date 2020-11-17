package com.example.da.activity.Home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.da.R;
import com.example.da.activity.Comic.ComicActivity;
import com.example.da.entity.obj.comic.Account;
import com.example.da.entity.Manager.UserManager;
import com.example.da.retrofit2.APIService;
import com.example.da.retrofit2.DataService;
import com.example.da.util.Constant;
import com.example.da.util.GlobalApplication;
import com.example.da.util.Server;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginAppActivity extends AppCompatActivity {


    LoginButton login_button;
    CallbackManager  callbackManager;
    String email, lastname, fistname, id;
    Account accountfb;

    ProgressDialog mPrD;
    GlobalApplication globalApplication;
    final Runnable taskLogin = new Runnable() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mPrD.show(); // Bật dialog progress
                }
            });

            String username = getDataForm(R.id.txtUsername);
            String password = getDataForm(R.id.txtPassword);

            String data[][] = {
                    {"username", username},
                    {"password", password}
            };

            Server server = new Server();
            final String result = server.sendRequest(1, Constant.HOSTING_API + Constant.LOGIN, data);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mPrD.dismiss(); // Tắt dialog progress
                    try {
                        UserManager manager = new UserManager(result);
                        // Nếu đăng nhập sai
                        if (manager.lstUser.size() == 0) {
                            Toast.makeText(LoginAppActivity.this, "Tài khoản hoặc mật khẩu không chính xác !", Toast.LENGTH_SHORT).show();
                        } else {
                            // Lấy đối tượng Đăng nhập
                            Account account = manager.lstUser.get(0);
                            if(globalApplication == null){
                                globalApplication = (GlobalApplication) getApplicationContext();
                            }
                            globalApplication.account = account;
                            Intent intent = new Intent(LoginAppActivity.this, ComicActivity.class);
                            startActivity(intent);
                            finish();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(LoginAppActivity.this, "Dữ liệu lỗi", Toast.LENGTH_SHORT).show();
                    }
                    
                    try {
                        luuTK();
                    }catch (Exception e){

                    }
                }
            });

        }
    };

    private void luuTK() {
        if (globalApplication.account != null){
            SharedPreferences prefs = getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
            //luu dataa
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("User", globalApplication.account.getUsername());
            editor.putString("Pass", globalApplication.account.getPassword());
            editor.commit();
        }
    }

    private String getDataForm(int resource) {
        EditText text = (EditText) findViewById(resource);
        return text.getText().toString();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login_app);

        try {
            PackageInfo info = null;
            try {
                info = getPackageManager().getPackageInfo(
                        "com.example.da",                  //Insert your own package name.
                        PackageManager.GET_SIGNATURES);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }

        } catch (NoSuchAlgorithmException e) {
        }

        mPrD = new ProgressDialog(LoginAppActivity.this);
        mPrD.setMessage("Vui lòng đợi trong chốc lát");
        mPrD.setCancelable(false);


        login_button = findViewById(R.id.login_button);
        
        login_button.setReadPermissions(Arrays.asList("public_profile", "email"));
        loginFB();

    }

    private void loginFB() {
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //login thanh cong : xu ly
                getData();//lay thon tin ve
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void getData() {
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                try {
                    id = object.getString("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    lastname = object.getString("last_name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    fistname = object.getString("first_name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                accountfb = new Account();
                DataService dataService = APIService.getService();
                Call<String> call = dataService.AddFB(id, fistname, lastname);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        DataService dataService1 = APIService.getService();
                        Call<List<Account>> call1 = dataService1.GetAccFB(id);
                        call1.enqueue(new Callback<List<Account>>() {
                            @Override
                            public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {
                                ArrayList<Account> accFB = (ArrayList<Account>) response.body();
                                accountfb = accFB.get(0);
                                if (globalApplication == null){
                                    globalApplication = (GlobalApplication) getApplication();
                                }
                                globalApplication.account = accountfb;
                                startActivity(new Intent(LoginAppActivity.this, ComicActivity.class));
                            }

                            @Override
                            public void onFailure(Call<List<Account>> call, Throwable throwable) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable throwable) {

                    }
                });



            }
        });
        Bundle bundle = new Bundle();
        bundle.putString("fields", "id,last_name,first_name");
        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();
    }

    @Override
    protected void onStart() {
        LoginManager.getInstance().logOut();
        super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    public void actionLogin(View view) {
        Thread th = new Thread(taskLogin);
        th.start();
    }

    public void openRegister(View view) {
        Intent intent = new Intent(LoginAppActivity.this, RegisterAppActivity.class);
        startActivity(intent);
    }

    public void backDN(View view) {
        startActivity(new Intent(LoginAppActivity.this, ComicActivity.class));
    }
}
