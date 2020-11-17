package com.example.da.activity.Home;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da.R;
import com.example.da.entity.Manager.ResultEntity;
import com.example.da.retrofit2.APIService;
import com.example.da.retrofit2.DataService;
import com.example.da.util.Constant;
import com.example.da.util.Server;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterAppActivity extends AppCompatActivity {

    RadioGroup radGender;
    String username, password, repassword, lastName, name, phone, address, email, DateofBirth;
    EditText txtusername, txtpassword, txtrepassword, txtlastName,txtname,txtphone,txtaddress,txtemail;
    TextView editDateofBirth;
    Button button;
    int gender = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_app);
        init();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDK();
            }
        });

    }

    private void init() {
        txtusername = findViewById(R.id.dkUsername);
        txtpassword = findViewById(R.id.dkPassword);
        txtrepassword = findViewById(R.id.rePassword);
        txtlastName = findViewById(R.id.dkLastName);
        txtname = findViewById(R.id.dkFullName);
        txtphone = findViewById(R.id.dkPhone);
        txtaddress = findViewById(R.id.dkAddress);
        txtemail= findViewById(R.id.dkEmail);
        editDateofBirth = findViewById(R.id.dkDateofBirth);
        radGender = findViewById(R.id.radGender);
        button = findViewById(R.id.DK);
    }

    private int getRadioChecked(int resource) {
        return ((RadioGroup) findViewById(resource)).getCheckedRadioButtonId();
    }

    private void checkDK() {
        username= txtusername.getText().toString()+"";
        password = txtpassword.getText().toString()+"";
        repassword = txtrepassword.getText().toString()+"";
        lastName = txtlastName.getText().toString()+"";
        name = txtname.getText().toString()+"";
        phone = txtphone.getText().toString()+"";
        address = txtaddress.getText().toString()+"";
        if (R.id.checkNu == getRadioChecked(R.id.radGender)) {
            gender = 1;
        }
        email = txtemail.getText().toString()+"";

        editDateofBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();

                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterAppActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                        editDateofBirth.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR) - 18, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        DateofBirth = editDateofBirth.getText().toString()+"";

        if (username.length() == 0 || password.length() == 0 || name.length() == 0 || repassword.length() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterAppActivity.this);
            builder.setTitle(R.string.ThongBao);
            builder.setMessage(R.string.KhongDcDeTrongTT);
            builder.setPositiveButton(R.string.XacNhan, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (username.length() == 0){
                        txtusername.setBackgroundResource(R.color.colorRed);
                    }else {
                        txtusername.setBackgroundResource(R.color.colorWhite);
                    }
                    if (password.length() == 0){
                        txtpassword.setBackgroundResource(R.color.colorRed);
                    }else {
                        txtpassword.setBackgroundResource(R.color.colorWhite);
                    }
                    if (repassword.length() == 0){
                        txtrepassword.setBackgroundResource(R.color.colorRed);
                    }else {
                        txtrepassword.setBackgroundResource(R.color.colorWhite);
                    }
                    if (name.length() == 0){
                        txtname.setBackgroundResource(R.color.colorRed);
                    }else {
                        txtname.setBackgroundResource(R.color.colorWhite);
                    }
                }
            });
            builder.show();
        } else if (!password.equals(repassword)){
            doiEditText();
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterAppActivity.this);
            builder.setTitle(R.string.ThongBao);
            builder.setMessage(R.string.MKXNKhongChinhXac);
            builder.setPositiveButton(R.string.XacNhan, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    txtrepassword.setText("");
                }
            });
            builder.show();
        }else {
            doiEditText();
            DataService dataService = APIService.getService();
            Call<String> call = dataService.CheckTK(username, password,lastName,name,phone,address,String.valueOf(gender),email,DateofBirth);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.d("AAAAAA", "Conmemay");
                    String code = "";
                    code = response.body();
                    if (code.equalsIgnoreCase("string")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterAppActivity.this);
                        builder.setTitle(R.string.ThongBao);
                        builder.setMessage(R.string.TenTKBiTrung);
                        builder.setPositiveButton(R.string.XacNhan, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        builder.show();
                    }else if (code.equalsIgnoreCase("oke")){
                        startActivity(new Intent(RegisterAppActivity.this, LoginAppActivity.class));
                    }

                    Log.d("AAAAAA", code);
                }

                @Override
                public void onFailure(Call<String> call, Throwable throwable) {

                }
            });
        }
    }

    private void doiEditText() {
        txtusername.setBackgroundResource(R.color.colorWhite);
        txtpassword.setBackgroundResource(R.color.colorWhite);
        txtrepassword.setBackgroundResource(R.color.colorWhite);
        txtname.setBackgroundResource(R.color.colorWhite);
    }
}
