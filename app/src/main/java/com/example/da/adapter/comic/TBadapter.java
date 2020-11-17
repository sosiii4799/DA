package com.example.da.adapter.comic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.da.R;
import com.example.da.entity.obj.comic.PhanHoi;

import java.util.ArrayList;
import java.util.List;

public class TBadapter extends ArrayAdapter<PhanHoi> {

    private Context ctx;
    private ArrayList<PhanHoi> arr;

    public TBadapter(@NonNull Context context, int resource, @NonNull List<PhanHoi> objects) {
        super(context, resource, objects);
        this.ctx = context;
        this.arr = new ArrayList<>(objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_tb, null);
        }

        TextView txtPH = (TextView) convertView.findViewById(R.id.txtThongBao);
        TextView txtThongBaoTrc = (TextView) convertView.findViewById(R.id.txtThongBaoTrc);

        PhanHoi phanHoi = this.arr.get(position);
        if (phanHoi.getCheckDoc().equalsIgnoreCase("1")){
            txtPH.setBackgroundResource(R.color.colorXam);
            txtThongBaoTrc.setBackgroundResource(R.color.colorXam);
            txtPH.setText(phanHoi.getPhanHoi());
            txtThongBaoTrc.setText(phanHoi.getCmt());
        }else {
            txtPH.setText(phanHoi.getPhanHoi());
            txtThongBaoTrc.setText(phanHoi.getCmt());
        }

        return convertView;
    }
}
