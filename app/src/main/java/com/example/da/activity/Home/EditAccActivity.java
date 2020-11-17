package com.example.da.activity.Home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.da.R;
import com.example.da.activity.Comic.ComicActivity;
import com.example.da.entity.obj.comic.Account;
import com.example.da.retrofit2.APIService;
import com.example.da.retrofit2.DataService;
import com.example.da.retrofit2.OtherUltil;
import com.example.da.util.GlobalApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAccActivity extends AppCompatActivity {

    ImageView EditImgV;

    EditText editHo, editName, editPhone, editAddress, editEmail;
    TextView editDateOfBirth;
    RadioGroup radiogroupGioitinh;
    RadioButton checkNu, checkNam;
    Button buttonEdit;
    GlobalApplication globalApplication;
    Account account;
    Account accountnew;

    String txtlastname, txtnamefull, txtphone, txtaddress, txtemail, txtdateofbirth;

    String realpath = "";String code = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_acc);
        AnhXa();
        try {
            setdataintilize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        listener();
    }

    private void setdataintilize() throws Exception{


        editHo.setText(account.getLastName() + "");
        editName.setText(account.getName() + "");
        editAddress.setText(account.getAddress() + "");
        editDateOfBirth.setText(OtherUltil.convertDateFromMysql(account.getDateOfBirth()) + "");
        editEmail.setText(account.getEmail() + "");
        editName.setText(account.getName() + "");

        editPhone.setText(account.getPhone() + "");
        switch (account.getGender()) {
            case 0:
                checkNam.setChecked(true);
                break;
            case 1:
                checkNu.setChecked(true);
                break;

        }
    }

    private void AnhXa() {
        EditImgV = findViewById(R.id.EditImgV);

        editHo = findViewById(R.id.editHo);
        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        editAddress = findViewById(R.id.editAddress);
        editEmail = findViewById(R.id.editEmail);
        editDateOfBirth = findViewById(R.id.editDateOfBirth);

        radiogroupGioitinh = (RadioGroup) findViewById(R.id.radiogroupGioitinhEdit);
        checkNam = (RadioButton) findViewById(R.id.checkNamEdit);
        checkNu = (RadioButton) findViewById(R.id.checkNuEdit);
        buttonEdit = (Button) findViewById(R.id.signup_buttonEdit);
        globalApplication = (GlobalApplication) getApplicationContext();
        account = globalApplication.account;
    }

    private void listener() {

        EditImgV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 113);
            }
        });

        editDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditAccActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        editDateOfBirth.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR) - 18, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();

            }
        });
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditSendRequest();
                try {
                    upAnh();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void upAnh() throws Exception{
        File file = new File(realpath);
        String file_path = file.getAbsolutePath();
        String[] mangTenFile = file_path.split("\\.");

        file_path = mangTenFile[0] + System.currentTimeMillis() + "." + mangTenFile[1];

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("upload_file", file_path, requestBody);
        DataService dataService = APIService.getService();
        Call<String> call = dataService.UpdateImg(body);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null){
                    code = response.body();
                    DataService dataService1 = APIService.getService();
                    Call<String> call1 = dataService1.AddImg(account.getIdAccount(), "img/Avatar/"+ code, account.getAvatar());
                    call1.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String a = response.body();
                            if (a.equalsIgnoreCase("oce")){
                                account.setAvatar("img/Avatar/"+ code);
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable throwable) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
            }
        });
    }

    private void EditSendRequest() {
        if (getdataFill()) {
            Log.d("AAAA", "EditSendRequest: "+ accountnew.getLastName() +" "+ accountnew.getName()+" " + accountnew.getPhone()+" " + accountnew.getAddress()+" "+ accountnew.getEmail()+" " + accountnew.getDateOfBirth()+" "+ accountnew.getGender()+" "+ accountnew.getIdAccount()+" ");
            DataService dataService = APIService.getService();
            Call<String> call = dataService.EditAccount(accountnew.getLastName(),
                                                        accountnew.getName(),
                                                        accountnew.getPhone(),
                                                        accountnew.getAddress(),
                                                        accountnew.getEmail(),
                                                        accountnew.getDateOfBirth(),
                                                        accountnew.getGender(),
                                                        accountnew.getIdAccount());

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response != null) {
                        if (response.body().equalsIgnoreCase("1xx1")) {
                            Toasty.success(EditAccActivity.this, "Chỉnh sửa tài khoản thành công!").show();

                            Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(3000);
                                        globalApplication.account = accountnew;
                                        Intent intent = new Intent(EditAccActivity.this, ComicActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            thread.start();


                        } else {
                            Toasty.error(EditAccActivity.this, "Chỉnh sửa tài khoản thất bại!\n Vui lòng thử lại").show();
                        }
                    }

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        } else {
            Toasty.error(EditAccActivity.this, "Vui lòng điền đầy đủ thông tin còn thiếu").show();
        }

    }

    private boolean getdataFill() {

        txtaddress = editAddress.getText().toString().trim() + "";
        txtdateofbirth = editDateOfBirth.getText().toString().trim() + "";

        String dateOfBirthDB = OtherUltil.convertDatetoMysql(txtdateofbirth);
        txtdateofbirth = dateOfBirthDB;

        txtemail = editEmail.getText().toString().trim();
        txtnamefull = editName.getText().toString().trim();
        txtlastname = editHo.getText().toString().trim();

        txtphone = editPhone.getText().toString().trim();
        int gioitinh = 0;
        if (R.id.checkNuEdit == getRadioChecked(R.id.radiogroupGioitinhEdit)) {
            gioitinh = 1;
        }
        if (txtlastname.length() > 0 &&txtnamefull.length() > 0 && txtaddress.length() > 0 && txtemail.length() > 0 && txtphone.length() > 0) {
            accountnew = account;
            accountnew.setDateOfBirth(txtdateofbirth);
            accountnew.setName(txtnamefull);
            accountnew.setLastName(txtlastname);
            accountnew.setPhone(txtphone);
            accountnew.setGender(gioitinh);
            accountnew.setEmail(txtemail);
            accountnew.setAddress(txtaddress);
            return true;

        } else {
            return false;
        }
    }

    private int getRadioChecked(int resource) {
        return ((RadioGroup) findViewById(resource)).getCheckedRadioButtonId();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 113 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            realpath = getReadPathFromUri(uri);
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                EditImgV.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void backEditAcc(View view) {
        finish();
    }

    //đường link thực tế của ảnh
    public String getReadPathFromUri(Uri contenUri){
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(contenUri, proj, null, null, null);
        if (cursor.moveToFirst()){
            int column_index = cursor.getColumnIndex(MediaStore.MediaColumns.DATA);
            path =cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }
}
