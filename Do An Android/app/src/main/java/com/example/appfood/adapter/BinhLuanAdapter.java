package com.example.appfood.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfood.R;
import com.example.lib.model.BinhLuan;

import java.util.List;


public class BinhLuanAdapter extends RecyclerView.Adapter<BinhLuanAdapter.GetViewMonNgauNhien> {
    Context context; // Context hiện tại
    List<BinhLuan.Result> list; // Danh sách các đối tượng BinhLuan

    public BinhLuanAdapter(Context context, List<BinhLuan.Result> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public GetViewMonNgauNhien onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Tạo view cho từng item bình luận
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_binhluan, parent, false);
        return new GetViewMonNgauNhien(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetViewMonNgauNhien holder, int position) {
        // Đặt dữ liệu vào trong view của từng item
        BinhLuan.Result monResult = list.get(position);
        String coverPhone = monResult.getSdt().substring(0, 7); // Che dấu một phần số điện thoại
        holder.userphone.setText(coverPhone + "***");
        holder.content.setText(monResult.getNoidung());
    }

    @Override
    public int getItemCount() {
        // Trả về số lượng item trong danh sách
        return list.size();
    }

    public class GetViewMonNgauNhien extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView userphone, content; // TextView hiển thị số điện thoại và nội dung bình luận

        public GetViewMonNgauNhien(@NonNull View itemView) {
            super(itemView);
            // Lấy ID của các view trong layout
            userphone = itemView.findViewById(R.id.userphone);
            content = itemView.findViewById(R.id.content);
            itemView.setOnClickListener(this); // Thiết lập listener cho việc nhấn vào item
        }

        @Override
        public void onClick(View view) {
            // Xử lý sự kiện khi nhấn vào item (hiện tại chưa được sử dụng)
        }
    }
}
