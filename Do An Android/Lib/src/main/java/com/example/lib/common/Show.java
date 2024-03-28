package com.example.lib.common;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lib.model.GioHang;

import java.util.List;

public class Show {
    /**
     * Hiển thị thông báo Toast.
     * @param context Context của ứng dụng.
     * @param notify Nội dung thông báo.
     */
    public static void Notify(Context context, String notify) {
        Toast.makeText(context, notify, Toast.LENGTH_SHORT).show();
    }

    /**
     * Danh sách giỏ hàng.
     */
    public static List<GioHang> listGiohang;

    /**
     * Đếm số lượng sản phẩm trong giỏ hàng.
     * @param Options Lựa chọn (1: Giới hạn số lượng hiển thị là 999+; 2: Không giới hạn).
     * @return Số lượng sản phẩm trong giỏ hàng.
     */
    public static int demSoLuongGioHang(int Options) {
        int dem = 0;
        if (Show.listGiohang != null) {
            for (int i = 0; i < Show.listGiohang.size(); i++) {
                dem += Show.listGiohang.get(i).getSoluong();
            }
            switch (Options) {
                case 1:
                    dem = dem < 999 ? dem : (dem > 999 ? 1000 : 999);
                    return dem;
                case 2:
                    return dem;
                default:
                    return 0;
            }
        }
        return 0;
    }

    /**
     * Thay đổi số lượng sản phẩm trong giỏ hàng (hiển thị "999+" nếu có nhiều hơn 999 sản phẩm).
     * @param view TextView để hiển thị số lượng sản phẩm.
     */
    public static void thayDoiSoLuongGioHangNho(TextView view) {
        int check = Show.demSoLuongGioHang(1);
        if (check > 999) {
            view.setText("999+");
        } else if (check <= 0) {
            view.setVisibility(View.GONE);
        } else {
            view.setText(String.valueOf(demSoLuongGioHang(1)));
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Tính tổng tiền của các sản phẩm trong giỏ hàng.
     * @return Tổng tiền.
     */
    public static int tinhTongTien() {
        int tongtien = 0;
        if (listGiohang.size() > 0) {
            for (int i = 0; i < Show.listGiohang.size(); i++) {
                tongtien += Show.listGiohang.get(i).getGia() * Show.listGiohang.get(i).getSoluong();
            }
        }
        return tongtien;
    }
}
