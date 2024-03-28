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
import com.example.appfood.activity.ChiTietDanhMucActivity;
import com.example.lib.InterfaceResponsitory.ItemClickOptions;
import com.example.lib.common.Url;
import com.example.lib.model.DanhMuc;

import java.util.List;

public class DanhMucAdapter extends RecyclerView.Adapter<DanhMucAdapter.GetViewDanhMuc> {
    Context context; // Context hiện tại
    List<DanhMuc.Result> list; // Danh sách các danh mục món ăn

    public DanhMucAdapter(Context context, List<DanhMuc.Result> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public GetViewDanhMuc onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Tạo view cho từng item danh mục
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_danh_muc, parent, false);
        return new GetViewDanhMuc(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetViewDanhMuc holder, int position) {
        // Đặt dữ liệu vào trong view của từng item
        DanhMuc.Result danhmucResult = list.get(position);
        holder.tendanhmuc.setText(danhmucResult.getTendanhmuc());

        // Sử dụng Glide để hiển thị hình ảnh danh mục
        Glide.with(context).load(Url.linkDanhMuc + danhmucResult.getHinhdanhmuc())
                .placeholder(R.drawable.img_default)
                .error(R.drawable.img_error)
                .into(holder.hinhdanhmuc);

        // Thiết lập click listener cho mỗi item
        holder.setItemClickOptions(new ItemClickOptions() {
            @Override
            public void onClickOptions(View view, int pos, int value) {
                // Chuyển đến ChiTietDanhMucActivity khi click vào item
                Intent intent = new Intent(context, ChiTietDanhMucActivity.class);
                intent.putExtra("chitietdanhmuc", danhmucResult);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        // Trả về số lượng item trong danh sách danh mục
        return list.size();
    }

    public class GetViewDanhMuc extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tendanhmuc; // TextView hiển thị tên danh mục
        ImageView hinhdanhmuc; // ImageView hiển thị hình ảnh danh mục
        private ItemClickOptions itemClickOptions; // Interface cho sự kiện click

        public GetViewDanhMuc(@NonNull View itemView) {
            super(itemView);
            // Lấy ID của các view trong layout
            tendanhmuc = itemView.findViewById(R.id.tendanhmuc);
            hinhdanhmuc = itemView.findViewById(R.id.hinhdanhmuc);
            itemView.setOnClickListener(this); // Thiết lập listener cho việc nhấn vào item
        }

        public void setItemClickOptions(ItemClickOptions itemClickOptions) {
            this.itemClickOptions = itemClickOptions;
        }

        @Override
        public void onClick(View view) {
            itemClickOptions.onClickOptions(view, getAdapterPosition(), 0);
        }
    }
}