package com.example.appfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfood.R;
import com.example.appfood.adapter.ChiTietDanhMucAdapter;
import com.example.lib.InterfaceResponsitory.AppFoodMethods;
import com.example.lib.RetrofitClient;
import com.example.lib.common.NetworkConnection;
import com.example.lib.common.Show;
import com.example.lib.common.Url;
import com.example.lib.model.DanhMuc;
import com.example.lib.model.Mon;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChiTietDanhMucActivity extends AppCompatActivity {
    // Khai báo các view và biến
    Toolbar toolbar_Chitietdanhmuc;
    RecyclerView recycleView_ChiTietDanhMuc;

    // Quản lý các subscriptions của RxJava
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    AppFoodMethods appFoodMethods; // Interface cho Retrofit

    List<Mon.Result> listMonTheoDanhMuc; // Danh sách món ăn theo danh mục
    ChiTietDanhMucAdapter chiTietDanhMucAdapter; // Adapter cho RecyclerView

    TextView thongbao_soluong; // Hiển thị thông báo số lượng
    int madanhmuc; // Mã danh mục

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_danh_muc); // Thiết lập layout

        getViewId(); // Lấy ID của các view từ layout
        actionToolbar(); // Thiết lập hành động cho toolbar
        khoitao(); // Khởi tạo các biến và đối tượng

        // Kiểm tra kết nối mạng
        if(NetworkConnection.isConnected(this)) {
            getChiTietDanhMuc(); // Lấy chi tiết danh mục từ server
            Show.thayDoiSoLuongGioHangNho(thongbao_soluong); // Cập nhật số lượng giỏ hàng
        } else {
            Show.Notify(this, getString(R.string.error_network)); // Hiển thị lỗi kết nối
            finish(); // Đóng activity
        }
    }

    private void khoitao() {
        listMonTheoDanhMuc = new ArrayList<>();
        appFoodMethods = RetrofitClient.getRetrofit(Url.AppFood_Url).create(AppFoodMethods.class);

        // Thiết lập layout manager cho RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recycleView_ChiTietDanhMuc.setLayoutManager(layoutManager);
        recycleView_ChiTietDanhMuc.setHasFixedSize(true);
    }

    private void getChiTietDanhMuc() {
        // Lấy thông tin danh mục từ intent và thực hiện truy vấn server
        DanhMuc.Result danhmucResult = (DanhMuc.Result) getIntent().getSerializableExtra("chitietdanhmuc");
        madanhmuc = danhmucResult.getId();
        compositeDisposable.add(appFoodMethods.POST_MonTheoDanhMuc(madanhmuc)
                .subscribeOn(Schedulers.io()) // Xử lý trên thread khác
                .observeOn(AndroidSchedulers.mainThread()) // Kết quả trả về trên main thread
                .subscribe(
                        mon -> {
                            // Xử lý kết quả trả về
                            if (mon.isSuccess()) {
                                listMonTheoDanhMuc = mon.getResult();
                                chiTietDanhMucAdapter = new ChiTietDanhMucAdapter(this, listMonTheoDanhMuc);
                                recycleView_ChiTietDanhMuc.setAdapter(chiTietDanhMucAdapter);
                                toolbar_Chitietdanhmuc.setTitle(danhmucResult.getTendanhmuc());
                            }
                        },
                        throwable -> {
                            // Xử lý lỗi
                            Show.Notify(this, getString(R.string.error_server));
                        }
                ));
    }

    private void actionToolbar() {
        // Thiết lập toolbar
        setSupportActionBar(toolbar_Chitietdanhmuc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_Chitietdanhmuc.setNavigationOnClickListener(view -> finish());
    }

    private void getViewId() {
        // Gán các view với ID tương ứng trong layout
        toolbar_Chitietdanhmuc = findViewById(R.id.toolbar_Chitietdanhmuc);
        recycleView_ChiTietDanhMuc = findViewById(R.id.recycleView_ChiTietDanhMuc);
        thongbao_soluong = findViewById(R.id.thongbao_soluong);
    }

    // Các phương thức khác để chuyển đến các activity khác như trang chủ, giỏ hàng...

    public void ToHome(View view) {
        Intent trangchu = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(trangchu);
    }

    public void openCart(View view) {
        Intent giohang = new Intent(getApplicationContext(),GioHangActivity.class);
        startActivity(giohang);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Cập nhật số lượng giỏ hàng khi activity bắt đầu
        Show.thayDoiSoLuongGioHangNho(thongbao_soluong);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật số lượng giỏ hàng khi activity tiếp tục
        Show.thayDoiSoLuongGioHangNho(thongbao_soluong);
    }

    @Override
    protected void onDestroy() {
        // Dọn dẹp compositeDisposable khi activity bị hủy
        compositeDisposable.clear();
        super.onDestroy();
    }
}