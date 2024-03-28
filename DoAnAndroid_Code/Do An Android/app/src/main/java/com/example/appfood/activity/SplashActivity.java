package com.example.appfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appfood.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash); // Thiết lập layout cho Splash Screen

        // Handler để chạy một đoạn code sau một khoảng thời gian
        new Handler().postDelayed(() -> {
            // Khởi chạy MainActivity sau khi thời gian chờ kết thúc
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            // Đóng SplashActivity sau khi chuyển đến MainActivity
            finish();
        }, 3000); // Đặt thời gian chờ là 3 giây
    }
}