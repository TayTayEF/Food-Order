package com.example.appfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfood.R;
import com.example.appfood.adapter.DanhMucAdapter;
import com.example.lib.InterfaceResponsitory.AppFoodMethods;
import com.example.lib.RetrofitClient;
import com.example.lib.common.NetworkConnection;
import com.example.lib.common.Show;
import com.example.lib.common.Url;
import com.example.lib.model.DanhMuc;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DanhMucActivity extends AppCompatActivity {
    Toolbar toolbar_Danhmuc; // Toolbar của activity
    RecyclerView recycleView_DanhMuc; // RecyclerView hiển thị danh mục

    CompositeDisposable compositeDisposable = new CompositeDisposable(); // Quản lý các subscriptions của RxJava
    AppFoodMethods appFoodMethods; // Interface cho Retrofit

    List<DanhMuc.Result> listDanhMucResult; // Danh sách các danh mục
    DanhMucAdapter danhMucAdapter; // Adapter cho RecyclerView

    TextView thongbao_soluong; // Thông báo số lượng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_muc); // Thiết lập layout

        getViewId(); // Lấy ID của các view từ layout
        actionToolbar(); // Thiết lập hành động cho toolbar
        khoitao(); // Khởi tạo các biến và đối tượng

        // Kiểm tra kết nối mạng
        if(NetworkConnection.isConnected(this)) {
            GetDanhMuc(); // Lấy thông tin danh mục
            Show.thayDoiSoLuongGioHangNho(thongbao_soluong); // Cập nhật thông báo số lượng giỏ hàng
        } else {
            Show.Notify(this, getString(R.string.error_network)); // Hiển thị thông báo lỗi kết nối
            finish(); // Đóng activity
        }
    }

    private void actionToolbar() {
        // Thiết lập toolbar
        setSupportActionBar(toolbar_Danhmuc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_Danhmuc.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void khoitao() {
        listDanhMucResult = new ArrayList<>();
        appFoodMethods = RetrofitClient.getRetrofit(Url.AppFood_Url).create(AppFoodMethods.class);

        // Thiết lập layout manager cho RecyclerView (2 cột)
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recycleView_DanhMuc.setLayoutManager(layoutManager);
        recycleView_DanhMuc.setHasFixedSize(true);
    }

    private void GetDanhMuc() {
        // Lấy thông tin danh mục từ server
        compositeDisposable.add(appFoodMethods.GET_DanhMuc()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        danhMuc -> {
                            // Xử lý kết quả trả về
                            if(danhMuc.isSuccess()) {
                                listDanhMucResult = danhMuc.getResult();
                                danhMucAdapter = new DanhMucAdapter(this, listDanhMucResult);
                                recycleView_DanhMuc.setAdapter(danhMucAdapter);
                            }
                        },
                        throwable -> {
                            // Xử lý lỗi
                            Show.Notify(this, getString(R.string.error_server));
                        }
                ));
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

    private void getViewId() {
        // Gán các view với ID tương ứng trong layout
        toolbar_Danhmuc = findViewById(R.id.toolbar_Danhmuc);
        recycleView_DanhMuc = findViewById(R.id.recycleView_DanhMuc);
        thongbao_soluong = findViewById(R.id.thongbao_soluong);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Cập nhật thông báo số lượng giỏ hàng khi activity bắt đầu
        Show.thayDoiSoLuongGioHangNho(thongbao_soluong);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật thông báo số lượng giỏ hàng khi activity tiếp tục
        Show.thayDoiSoLuongGioHangNho(thongbao_soluong);
    }

    @Override
    protected void onDestroy() {
        // Dọn dẹp compositeDisposable khi activity bị hủy
        compositeDisposable.clear();
        super.onDestroy();
    }
}