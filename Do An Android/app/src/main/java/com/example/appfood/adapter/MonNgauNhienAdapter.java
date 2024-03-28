package com.example.appfood.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appfood.R;
import com.example.appfood.activity.ChiTietMonActivity;
import com.example.lib.InterfaceResponsitory.ItemClickOptions;
import com.example.lib.common.Url;
import com.example.lib.model.Mon;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MonNgauNhienAdapter extends RecyclerView.Adapter<MonNgauNhienAdapter.GetViewMonNgauNhien> {
    Context context;
    List<Mon.Result> list;
    ArrayList<Mon> arrayMon;

    public MonNgauNhienAdapter(Context context, List<Mon.Result> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public GetViewMonNgauNhien onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_mon_ngau_nhien,parent,false);
        GetViewMonNgauNhien getViewMonNgauNhien = new GetViewMonNgauNhien(view);
        return getViewMonNgauNhien;
    }

    @Override
    public void onBindViewHolder(@NonNull GetViewMonNgauNhien holder, int position) {
        // Lấy dữ liệu món ăn tại vị trí hiện tại
        Mon.Result monResult = list.get(position);

        // Định dạng giá thành tiền
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

        // Đặt giá, tên món và mô tả
        holder.gia.setText(decimalFormat.format(Double.parseDouble(monResult.getGia()))+" đ");
        holder.tenmon.setText(monResult.getTenmon());
        holder.mota.setText(monResult.getMota());

        // Tải hình ảnh món ăn sử dụng Glide
        Glide.with(context).load(Url.linkMon + monResult.getHinhmon())
                .placeholder(R.drawable.img_default)
                .error(R.drawable.img_error)
                .into(holder.hinhmon);

        // Đặt các tùy chọn khi người dùng click vào món ăn
        holder.setItemClickOptions(new ItemClickOptions() {
            @Override
            public void onClickOptions(View view, int pos, int value) {
                // Mở ChiTietMonActivity khi người dùng click vào món ăn
                Intent intent = new Intent(context, ChiTietMonActivity.class);
                intent.putExtra("chitietmon",monResult);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class GetViewMonNgauNhien extends  RecyclerView.ViewHolder implements View.OnClickListener {
        TextView gia,tenmon,mota;
        ImageView hinhmon;
        private ItemClickOptions itemClickOptions;
        public GetViewMonNgauNhien(@NonNull View itemView) {
            super(itemView);
            gia = itemView.findViewById(R.id.gia);
            tenmon = itemView.findViewById(R.id.tenmon);
            mota = itemView.findViewById(R.id.mota);
            hinhmon = itemView.findViewById(R.id.hinhmon);
            itemView.setOnClickListener(this);
        }

        public void setItemClickOptions(ItemClickOptions itemClickOptions) {
            this.itemClickOptions = itemClickOptions;
        }

        @Override
        public void onClick(View view) {
            // Gửi sự kiện khi người dùng click vào món ăn
            itemClickOptions.onClickOptions(view, getAbsoluteAdapterPosition(), 0);
        }
    }
}
