package com.example.appfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfood.R;
import com.example.appfood.adapter.GioHangAdapter;
import com.example.lib.common.NetworkConnection;
import com.example.lib.common.Show;
import com.example.lib.model.EventBus.ActionEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {
    Toolbar toolbar_Giohang; // Toolbar của activity
    RecyclerView recycleView_Giohang; // RecyclerView hiển thị giỏ hàng
    TextView textview_tongtien, btn_tieptucmua, message_order; // Hiển thị tổng tiền, nút tiếp tục mua và thông báo
    Button btn_thanhtoan; // Nút thanh toán
    FrameLayout frame_giohangtrong; // FrameLayout khi giỏ hàng trống

    GioHangAdapter gioHangAdapter; // Adapter cho RecyclerView
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###"); // Định dạng số tiền

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang); // Thiết lập layout

        getViewId(); // Lấy ID của các view từ layout
        actionToolbar(); // Thiết lập hành động cho toolbar
        khoitao(); // Khởi tạo các biến và đối tượng

        // Kiểm tra kết nối mạng
        if(NetworkConnection.isConnected(this)) {
            getGiohang(); // Lấy thông tin giỏ hàng
            tinhTongTienGioHang(); // Tính tổng tiền giỏ hàng
            demToolBarGioHang(); // Đếm số lượng mặt hàng trong giỏ hàng
        }else{
            Show.Notify(this, getString(R.string.error_network)); // Hiển thị thông báo lỗi kết nối
            finish(); // Đóng activity
        }
    }

    private void tinhTongTienGioHang() {
        // Tính tổng tiền của giỏ hàng
        textview_tongtien.setText(decimalFormat.format(Show.tinhTongTien())+" đ");
    }

    private void khoitao() {
        // Khởi tạo RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recycleView_Giohang.setHasFixedSize(true);
        recycleView_Giohang.setLayoutManager(layoutManager);
    }

    private void getGiohang() {
        // Lấy thông tin giỏ hàng và hiển thị
        if(Show.listGiohang.size() == 0) {
            frame_giohangtrong.setVisibility(View.VISIBLE);
        }else{
            frame_giohangtrong.setVisibility(View.INVISIBLE);
            gioHangAdapter = new GioHangAdapter(getApplicationContext(), Show.listGiohang);
            recycleView_Giohang.setAdapter(gioHangAdapter);
        }
    }

    public void demToolBarGioHang() {
        // Cập nhật title của toolbar dựa trên số lượng mặt hàng
        int show = Show.demSoLuongGioHang(2);
        if(show > 0) {
            toolbar_Giohang.setTitle(getString(R.string.cart)+" (" + show +")");
            btn_tieptucmua.setText(getString(R.string.continue_buy));
        } else {
            toolbar_Giohang.setTitle(getString(R.string.cart));
            btn_tieptucmua.setText(getString(R.string.buy));
        }

    }

    private void actionToolbar() {
        // Thiết lập toolbar
        setSupportActionBar(toolbar_Giohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_Giohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getViewId() {
        // Gán các view với ID tương ứng trong layout
        toolbar_Giohang = findViewById(R.id.toolbarGiohang);
        recycleView_Giohang = findViewById(R.id.recycleView_Giohang);
        textview_tongtien = findViewById(R.id.textview_tongtien);
        btn_thanhtoan = findViewById(R.id.btn_thanhtoan);
        frame_giohangtrong = findViewById(R.id.frame_giohangtrong);
        btn_tieptucmua = findViewById(R.id.btn_tieptucmua);
        message_order = findViewById(R.id.message_order);
    }
    // Các phương thức khác để chuyển đến các activity khác như trang chủ, giỏ hàng...
    public void ToHome(View view) {
        Intent trangchu = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(trangchu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Đăng ký EventBus
        EventBus.getDefault().register(this);
        // Cập nhật thông tin giỏ hàng và toolbar
        getGiohang();
        demToolBarGioHang();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật thông tin giỏ hàng, toolbar và tổng tiền
        getGiohang();
        demToolBarGioHang();
        tinhTongTienGioHang();
    }

    @Override
    protected void onStop() {
        // Hủy đăng ký EventBus
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    // Xử lý sự kiện nhận từ EventBus
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void getEvent(ActionEvent event) {
        if(event != null ) {
            // Cập nhật thông tin giỏ hàng, tổng tiền và toolbar
            getGiohang();
            tinhTongTienGioHang();
            demToolBarGioHang();
        }
    }

    public void thanhtoan(View view) {
        // Xử lý sự kiện cho nút thanh toán
      if(Show.listGiohang.size() <= 0) {
          message_order.setVisibility(View.VISIBLE);
          message_order.setText(getString(R.string.empty_cart));
      }else {
          Intent thanhtoan = new Intent(getApplicationContext(), ThongTinKhachHangActivity.class);
          startActivity(thanhtoan);
          message_order.setVisibility(View.GONE);
          message_order.setText(" ");
      }
    }

    public void tieptucmua(View view) {
        // Xử lý sự kiện cho nút tiếp tục mua
        Intent danhmuc = new Intent(getApplicationContext(),DanhMucActivity.class);
        startActivity(danhmuc);
    }
}