package com.example.appfood.activity.Account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appfood.R;
import com.example.appfood.activity.MainActivity;
import com.example.appfood.activity.TaiKhoanActivity;
import com.example.lib.common.Url;
import com.example.lib.common.Validate;

import java.util.HashMap;
import java.util.Map;

public class DangNhapActivity extends AppCompatActivity {
    // Khai báo các view và biến
    Toolbar toolbar_DangNhap;
    TextView tvRegister_login, tvForgetPassword_login, message_phone, message_password;
    Button btnLogin_login;
    EditText user_phone_login, user_password_login;
    private static final String FILE_NAME = "myFile"; // Tên file để lưu trữ SharedPreferences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap); // Thiết lập layout cho activity
        getViewId(); // Lấy ID của các view từ layout
        actionToolbar(); // Thiết lập hành động cho toolbar
        event(); // Thiết lập sự kiện cho các view
    }

    private void actionToolbar() {
        // Thiết lập toolbar
        setSupportActionBar(toolbar_DangNhap);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_DangNhap.setNavigationOnClickListener(view -> finish());
        // Thiết lập sự kiện chuyển đến trang Đăng ký và Quên mật khẩu
        tvRegister_login.setOnClickListener(view -> startActivity(new Intent(DangNhapActivity.this, DangKyActivity.class)));
    }

    private void getViewId() {
        // Gán các view với ID tương ứng trong layout
        toolbar_DangNhap = findViewById(R.id.toolbar_DangNhap);
        btnLogin_login = findViewById(R.id.btnLogin_login);
        user_phone_login = findViewById(R.id.user_phone_login);
        user_password_login = findViewById(R.id.user_password_login);
        tvRegister_login = findViewById(R.id.tvRegister_login);
        tvForgetPassword_login = findViewById(R.id.tvForgetPassword_login);
        message_phone = findViewById(R.id.message_phone);
        message_password = findViewById(R.id.message_password);
    }

    private void event() {
        // Xử lý sự kiện khi nút đăng nhập được nhấn
        btnLogin_login.setOnClickListener(view -> {
            String user_phone = user_phone_login.getText().toString();
            String user_password = user_password_login.getText().toString();
            checkLogin(user_phone, user_password); // Kiểm tra thông tin đăng nhập
        });
    }

    private void checkLogin(String user_phone,String user_password) {
        // Kiểm tra tính hợp lệ của số điện thoại và mật khẩu
        if(Validate.isValidValue(user_phone, message_phone) && Validate.isValidValue(user_password, message_password)){
            // Gửi yêu cầu đăng nhập tới server
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.checkSigIn, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Xử lý phản hồi từ server
                    if(response.equals("Success")){
                        rememberPW(user_phone, user_password); // Lưu thông tin đăng nhập nếu cần
                        // Chuyển đến trang tài khoản và hiển thị thông báo
                        startActivity(new Intent(DangNhapActivity.this, TaiKhoanActivity.class));
                        Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(DangNhapActivity.this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Xử lý lỗi phản hồi từ server
                    Toast.makeText(DangNhapActivity.this, "e" + error.toString(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    // Gửi thông tin đăng nhập lên server
                    HashMap<String, String> param = new HashMap<String, String>();
                    param.put("sdt", user_phone);
                    param.put("matkhau", user_password);
                    return param;
                }
            };
            requestQueue.add(stringRequest);
        }else {
            // Kiểm tra lại thông tin nếu không hợp lệ
            Validate.isValidValue(user_phone, message_phone);
            Validate.isValidValue(user_password, message_password);
        }
    }

    private void rememberPW(String phone,String password){
        // Lưu thông tin đăng nhập vào SharedPreferences
        SharedPreferences.Editor editor = getSharedPreferences(FILE_NAME, MODE_PRIVATE).edit();
        editor.putString("user_phone",phone);
        editor.putString("user_password",password);
        editor.apply();
    }

    public void ToHome(View view) {
        // Chuyển đến trang chủ
        Intent trangchu = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(trangchu);
    }
}