package com.example.appfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appfood.R;
import com.example.lib.common.Show;

import java.text.DecimalFormat;

public class SuccessCheckoutActivity extends AppCompatActivity {
    // Khai báo các TextView để hiển thị thông tin khách hàng và tổng tiền
    TextView txt_tenkhachhang, txt_address, txt_sodienthoai, txt_ghichu, txt_tongtien;
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###"); // Định dạng cho hiển thị số tiền

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_checkout); // Thiết lập layout

        getViewId(); // Lấy ID của các view từ layout
        getThongTinKhachHang(); // Lấy và hiển thị thông tin khách hàng
    }

    private void getThongTinKhachHang() {
        // Lấy thông tin khách hàng từ ThongTinKhachHangActivity và hiển thị
        txt_tenkhachhang.setText(ThongTinKhachHangActivity.user_name.getText().toString());
        txt_address.setText(ThongTinKhachHangActivity.user_address.getText().toString());
        txt_sodienthoai.setText(ThongTinKhachHangActivity.user_phone.getText().toString());
        txt_ghichu.setText(ThongTinKhachHangActivity.user_note.getText().toString());

        // Tính và hiển thị tổng tiền
        long thanhtien = Show.tinhTongTien();
        txt_tongtien.setText(decimalFormat.format(thanhtien) + " đ");

        // Xóa danh sách giỏ hàng sau khi thanh toán thành công
        Show.listGiohang.clear();
    }

    private void getViewId() {
        // Gán các view với ID tương ứng trong layout
        txt_tenkhachhang = findViewById(R.id.txt_tenkhachhang);
        txt_address = findViewById(R.id.txt_address);
        txt_sodienthoai = findViewById(R.id.txt_sodienthoai);
        txt_ghichu = findViewById(R.id.txt_ghichu);
        txt_tongtien = findViewById(R.id.txt_tongtien);
    }

    public void ToHome(View view) {
        // Chức năng chuyển đến trang chủ khi nhấn nút
        Intent trangchu = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(trangchu);
    }
}