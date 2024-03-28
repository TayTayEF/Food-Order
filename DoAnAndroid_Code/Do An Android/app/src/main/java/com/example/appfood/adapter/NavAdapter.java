package com.example.appfood.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appfood.R;
import com.example.lib.NavForm;

public class NavAdapter extends ArrayAdapter<NavForm> {

    Activity context;
    int resource;

    public NavAdapter(@NonNull Context context, int resource) {
        super(context, resource);

        this.context = (Activity) context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Sử dụng LayoutInflater để tạo customView từ layout resource
        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        View customView = layoutInflater.inflate(this.resource,null);

        // Lấy các thành phần trong customView
        ImageView hinhminhhoa = customView.findViewById(R.id.hinhminhhoa);
        TextView noidung = customView.findViewById(R.id.noidung);

        // Lấy dữ liệu từ danh sách NavForm tại vị trí hiện tại
        NavForm nav = getItem(position);

        // Đặt hình minh họa và nội dung tương ứng
        hinhminhhoa.setImageResource(nav.getHinhminhhoa());
        noidung.setText(nav.getNoidung());

        return customView;
    }
}
