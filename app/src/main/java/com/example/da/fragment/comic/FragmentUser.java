package com.example.da.fragment.comic;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.da.R;
import com.example.da.activity.Comic.ComicActivity;
import com.example.da.activity.Comic.FeedbackActivity;
import com.example.da.activity.Home.EditAccActivity;
import com.example.da.activity.Home.EditPass;
import com.example.da.activity.Home.LoginAppActivity;
import com.example.da.activity.Home.MainActivity;
import com.example.da.entity.obj.comic.Account;
import com.example.da.retrofit2.OtherUltil;
import com.example.da.util.Constant;
import com.example.da.util.GlobalApplication;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class FragmentUser extends Fragment {

    ImageView viewAvatar;
    TextView tiengViet,tiengAh, textView_Name, textviewName_Profile, textviewAccount_, textviewPhone_Profile, textviewGender_Profile, textviewEmail_Profile,textviewDoB_Profile, textviewDiaCi_Profile;
    Account account;
    GlobalApplication globalApplication;
    ImageView imgShow, showNN;
    ProfilePictureView imgAvtFB;

    LinearLayout layoutUse, layoutHoSo, layoutEdit_Acc, layoutEditPasss_Acc, ngonNgu,layoutPhanHoi;
    RelativeLayout layoutDN,layoutTT, layoutEditLague;
    Button btnDN, btnbtnLogOut;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        globalApplication = (GlobalApplication) getActivity().getApplicationContext();
        if (globalApplication.account != null) {

            layoutUse = view.findViewById(R.id.layoutUse);
            layoutUse.setVisibility(View.VISIBLE);
            layoutDN = view.findViewById(R.id.layoutDN);
            layoutDN.setVisibility(View.GONE);

            account = globalApplication.account;
            init(view);
            try {
                setProfile(view);
            } catch (Exception e) {
                e.printStackTrace();
            }
            SetUp();

        }else {
            XacNhanDN();
            btnDN = view.findViewById(R.id.btnOpenDangNhap);
            btnDN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), LoginAppActivity.class);
                    getActivity().startActivity(intent);
                }
            });
        }

        return view;
    }

    private void XacNhanDN() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.XacNhanDN);
        builder.setMessage(R.string.BanCanDNDeTraiNghiemApp);
        builder.setPositiveButton(R.string.DangNhap, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity(), LoginAppActivity.class);
                getActivity().startActivity(intent);
            }
        });
        builder.setNegativeButton(R.string.Huy, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void init(View view) {
        viewAvatar = (ImageView) view.findViewById(R.id.viewAvatar);
        textView_Name = (TextView) view.findViewById(R.id.textView_Name);
        tiengViet = (TextView) view.findViewById(R.id.tiengViet);
        tiengAh = (TextView) view.findViewById(R.id.English);
        textviewName_Profile = (TextView) view.findViewById(R.id.textviewName_Profile);
        textviewAccount_ = (TextView) view.findViewById(R.id.textviewAccount_);
        textviewPhone_Profile = (TextView) view.findViewById(R.id.textviewPhone_Profile);
        textviewGender_Profile = (TextView) view.findViewById(R.id.textviewGender_Profile);
        textviewEmail_Profile = (TextView) view.findViewById(R.id.textviewEmail_Profile);
        textviewDoB_Profile = (TextView) view.findViewById(R.id.textviewDoB_Profile);
        textviewDiaCi_Profile = (TextView) view.findViewById(R.id.textviewDiaCi_Profile);
        imgShow = view.findViewById(R.id.imgShow);
        imgAvtFB = view.findViewById(R.id.imgAvtFB);
        layoutHoSo = view.findViewById(R.id.layoutHoSo);
        layoutTT = view.findViewById(R.id.layoutTT);
        layoutEdit_Acc = view.findViewById(R.id.layoutEdit_Acc);
        layoutEditPasss_Acc = view.findViewById(R.id.layoutEditPasss_Acc);
        layoutEditLague = view.findViewById(R.id.layoutEditLague);
        btnbtnLogOut = view.findViewById(R.id.btnLogOut);
        layoutPhanHoi = view.findViewById(R.id.layoutPhanHoi);

        showNN = view.findViewById(R.id.showNN);
        ngonNgu = view.findViewById(R.id.ngonNgu);
    }

    private void setProfile(View view) throws Exception{
        try {
            if (account.getAvatar().length() == 0){
                viewAvatar.setVisibility(View.GONE);
                imgAvtFB.setVisibility(View.VISIBLE);
                try {
                    imgAvtFB.setProfileId(account.getUsername());
                }catch (Exception e){

                }
            }else {

                Picasso.with(getActivity()).load(Constant.HOSTING + account.getAvatar()).into(viewAvatar);
            }
        }catch (Exception e){

        }
        textView_Name.setText(account.getName());
        textviewName_Profile.setText(account.getLastName() + " " + account.getName());
        textviewAccount_.setText(account.getUsername());
        textviewPhone_Profile.setText(account.getPhone());
        if(account.getGender()==0){
            textviewGender_Profile.setText(R.string.Nam);
        }else textviewGender_Profile.setText(R.string.Nu);
        textviewEmail_Profile.setText(account.getEmail());
        textviewDoB_Profile.setText(OtherUltil.convertDateFromMysql(account.getDateOfBirth()) + "");
        textviewDiaCi_Profile.setText(account.getAddress());
    }

    private void SetUp() {
        //Logout
        btnbtnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs = getActivity().getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.remove("User");
                editor.remove("Pass");
                editor.commit();

                Intent intent = new Intent(getContext(), ComicActivity.class);
                startActivityForResult(intent, 911);
                onDestroy();
                globalApplication.account = null;
                globalApplication.check = globalApplication.check - 1;
                LoginManager.getInstance().logOut();
                getActivity().finish();
            }
        });
        //layout hồ sơ cá nhân
        layoutTT.setOnClickListener(new View.OnClickListener() {
            int dem = 1;
            @Override
            public void onClick(View v) {
                if (dem % 2 == 1){
                    imgShow.setImageResource(R.drawable.backdown);
                    layoutHoSo.setVisibility(View.VISIBLE);
                    dem += 1;
                }else if (dem %2 == 0){
                    imgShow.setImageResource(R.drawable.backup);
                    layoutHoSo.setVisibility(View.GONE);
                    dem += 1;
                }
            }
        });

        //open sửa thông tin
        layoutEdit_Acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditAccActivity.class));
            }
        });

        //open sửa pass
        layoutEditPasss_Acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditPass.class));
            }
        });

        //open edit ngôn ngữ
        layoutEditLague.setOnClickListener(new View.OnClickListener() {
            int dem = 1;
            @Override
            public void onClick(View v) {
                if (dem % 2 == 1){
                    showNN.setImageResource(R.drawable.backdown);
                    ngonNgu.setVisibility(View.VISIBLE);
                    dem += 1;
                }else if (dem %2 == 0){
                    showNN.setImageResource(R.drawable.backup);
                    ngonNgu.setVisibility(View.GONE);
                    dem += 1;
                }
            }
        });

        //đổi ngôn ngữ
        tiengViet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ganNgonNgu("vi");
            }
        });

        tiengAh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ganNgonNgu("en");
            }
        });

        //phản hồi
        layoutPhanHoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FeedbackActivity.class));
            }
        });
    }

    public void ganNgonNgu(String language){
        Locale locale = new Locale(language);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getActivity().getBaseContext().getResources().updateConfiguration(configuration, getActivity().getBaseContext().getResources().getDisplayMetrics());

        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}
