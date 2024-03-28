package com.example.appfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.appfood.R;
import com.example.appfood.adapter.MonNgauNhienAdapter;
import com.example.appfood.adapter.NavAdapter;
import com.example.lib.InterfaceResponsitory.AppFoodMethods;
import com.example.lib.NavForm;
import com.example.lib.RetrofitClient;
import com.example.lib.common.NetworkConnection;
import com.example.lib.common.Show;
import com.example.lib.common.Url;
import com.example.lib.model.Mon;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar_Home; // Toolbar của activity
    ViewFlipper viewFlipper; // ViewFlipper cho hiển thị slider
    RecyclerView recycleView_MonNgauNhien; // RecyclerView cho món ăn ngẫu nhiên
    NavigationView navigationView; // Navigation View cho Drawer
    ListView listView_NavHome; // ListView trong Navigation Drawer
    DrawerLayout drawerLayout; // Drawer Layout
    NavAdapter navAdapter; // Adapter cho ListView Navigation
    TextView thongbao_soluong; // Thông báo số lượng món ăn trong giỏ hàng

    CompositeDisposable compositeDisposable = new CompositeDisposable(); // Quản lý các subscriptions của RxJava
    AppFoodMethods appFoodMethods; // Interface cho Retrofit

    List<Mon.Result> listMonNgauNhienResult; // Danh sách món ăn ngẫu nhiên
    MonNgauNhienAdapter monNgauNhienAdapter; // Adapter cho RecyclerView món ăn ngẫu nhiên
    List<String> slider = new ArrayList<>(); // Danh sách các hình ảnh cho slider
    boolean isLoading = false; // Flag kiểm tra trạng thái loading

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Thiết lập layout

        getViewId(); // Lấy ID của các view từ layout
        actionToolbar(); // Thiết lập hành động cho toolbar
        khoitao(); // Khởi tạo các biến và đối tượng
        setNav(); // Thiết lập Navigation Drawer

        // Kiểm tra kết nối mạng
        if(NetworkConnection.isConnected(this)) {
            GetSlider(); // Lấy thông tin slider
            GetMonNgauNhien(); // Lấy thông tin món ăn ngẫu nhiên
            Show.thayDoiSoLuongGioHangNho(thongbao_soluong); // Cập nhật thông báo số lượng giỏ hàng
            ChuyenTrang(); // Chuyển trang từ menu navigation
        } else {
            Show.Notify(this, getString(R.string.error_network)); // Hiển thị thông báo lỗi kết nối
            finish(); // Đóng activity
        }
    }
    // Các phương thức khác như setNav, ChuyenTrang, GetSlider, GetMonNgauNhien, Slider, ...
    private void setNav() {
        //list tùy chọn nav
        navAdapter = new NavAdapter(MainActivity.this,R.layout.item_list_nav);
        listView_NavHome.setAdapter(navAdapter);
        navAdapter.add(new NavForm(R.drawable.ic_menu_res,getString(R.string.menu)));
        navAdapter.add(new NavForm(R.drawable.ic_account,getString(R.string.account)));
    }

    private void ChuyenTrang() {
        listView_NavHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (i) {
                    case 0:
                        Intent danhmuc = new Intent(getApplicationContext(),DanhMucActivity.class);
                        startActivity(danhmuc);
                        break;
                    case 1:
                        Intent taikhoan = new Intent(getApplicationContext(),TaiKhoanActivity.class);
                        startActivity(taikhoan);
                        break;
                }
            }
        });
    }

    private void GetSlider(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Url.getSlider, null,
                response -> {
                        for(int i=0; i<=response.length(); i++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String link = jsonObject.getString("link");
                                String newLink = Url.linkSlider + link;
                                Log.d("onResponse", newLink);
                                slider.add(newLink);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Slider(slider);
                        },
                error -> {
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void GetMonNgauNhien() {
        compositeDisposable.add(appFoodMethods.GET_MonNgauNhien()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            mon -> {
                if(mon.isSuccess()) {
                    listMonNgauNhienResult = mon.getResult();
                    monNgauNhienAdapter = new MonNgauNhienAdapter(this, listMonNgauNhienResult);
                    recycleView_MonNgauNhien.setAdapter(monNgauNhienAdapter);
                }
            },
            throwable -> {
                Show.Notify(this,"Không thể kết nối với Server!");
            }
        ));
    }

    private void Slider(List<String> slider) {
        for (int i = 0; i< slider.size();i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(slider.get(i)).into(imageView);

            //fix imageView vào ViewFlipper
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_step_1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slider_step_1);
        Animation animation_slide_step_2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slider_step_2);

        viewFlipper.setInAnimation(animation_slide_step_1);
        viewFlipper.setOutAnimation(animation_slide_step_2);
    }

    private void actionToolbar() {
        // Thiết lập toolbar
        setSupportActionBar(toolbar_Home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_Home.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void getViewId() {
        // Gán các view với ID tương ứng trong layout
        toolbar_Home = findViewById(R.id.toolbar_Home);
        viewFlipper = findViewById(R.id.viewFlipper);
        recycleView_MonNgauNhien = findViewById(R.id.recycleView_MonNgauNhien);
        navigationView = findViewById(R.id.navigationView);
        listView_NavHome = findViewById(R.id.listView_NavHome);
        drawerLayout = findViewById(R.id.drawerLayout);
        thongbao_soluong = findViewById(R.id.thongbao_soluong);

    }

    private void khoitao() {
        // Khởi tạo các biến và đối tượng
        listMonNgauNhienResult = new ArrayList<>();
        appFoodMethods = RetrofitClient.getRetrofit(Url.AppFood_Url).create(AppFoodMethods.class);

        //set layout 2 cột
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recycleView_MonNgauNhien.setLayoutManager(layoutManager);
        recycleView_MonNgauNhien.setHasFixedSize(true);

        //giỏ hàng
        if(Show.listGiohang == null) {
            Show.listGiohang = new ArrayList<>();
        }
    }
    // Các phương thức khác để chuyển đến các activity khác như giỏ hàng, tìm kiếm, trang chủ...
    public void openCart(View view) {
        Intent giohang = new Intent(getApplicationContext(),GioHangActivity.class);
        startActivity(giohang);
    }

    public void searchMon(View view) {
        Intent timkiem = new Intent(getApplicationContext(),TimKiemActivity.class);
        startActivity(timkiem);
    }

    public void ToHome(View view) {
        Intent trangchu = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(trangchu);
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