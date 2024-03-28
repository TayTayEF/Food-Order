package com.example.appfood.activity.Account;

import android.content.Intent;
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
import com.example.lib.common.Url;
import com.example.lib.common.Validate;

import java.util.HashMap;
import java.util.Map;

public class DangKyActivity extends AppCompatActivity {

    // Khai báo các view và widget trong giao diện
    Button btnRegister_register;
    Toolbar toolbar_DangKy;
    EditText user_name_register, user_phone_register, user_mail_register, user_password_register, user_repassword_register;
    TextView tvLogin_register, tvForgetPassword_register, message_name, message_phone, message_mail, message_password, message_repassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky); // Thiết lập layout cho activity
        getViewId(); // Lấy ID của các view từ layout
        actionToolbar(); // Thiết lập hành động cho toolbar
        event(); // Thiết lập sự kiện cho các view
    }

    private void event() {
        btnRegister_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy dữ liệu từ các trường nhập
                String user_name = user_name_register.getText().toString();
                String user_phone = user_phone_register.getText().toString();
                String user_mail = user_mail_register.getText().toString();
                String user_password = user_password_register.getText().toString();
                String user_repassword = user_repassword_register.getText().toString();

                // Kiểm tra tính hợp lệ của dữ liệu nhập vào
                if(Validate.isValidValue(user_name, message_name) &&
                        Validate.isValidPhone(user_phone, 10, message_phone) &&
                        Validate.isValidEmail(user_mail, message_mail) &&
                        Validate.isValidValue(user_password, message_password) &&
                        Validate.isValidValue(user_repassword, message_repassword)){
                    if(user_repassword.equals(user_password)){
                        // Gửi yêu cầu đến server nếu dữ liệu hợp lệ
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.checkSigUp, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Xử lý phản hồi từ server
                                if(response.equals("Fail")){
                                    SigUp(user_name, user_phone, user_mail, user_password);
                                }else {
                                    Toast.makeText(DangKyActivity.this, "Số điện thoại đã được đăng ký!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Xử lý lỗi phản hồi từ server
                                Toast.makeText(DangKyActivity.this, "e" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }){
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                // Gửi dữ liệu lên server
                                HashMap<String, String> param = new HashMap<String, String>();
                                param.put("sdt", user_phone);
                                return param;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }else {
                        // Hiển thị thông báo nếu mật khẩu nhập lại không khớp
                        Toast.makeText(DangKyActivity.this, "Mật khẩu không trùng nhau!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    // Kiểm tra lại dữ liệu nhập nếu không hợp lệ
                    Validate.isValidValue(user_name, message_name);
                    Validate.isValidPhone(user_phone,10 ,message_phone);
                    Validate.isValidEmail(user_mail, message_mail);
                    Validate.isValidValue(user_password, message_password);
                    Validate.isValidValue(user_repassword, message_repassword);
                }
            }
        });
    }

    private void SigUp(String user_name, String user_phone, String user_mail, String user_password) {
        // Phương thức để đăng ký tài khoản mới
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.sigUp, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Xử lý phản hồi thành công từ server
                if(response.equals("Done")){
                    Toast.makeText(DangKyActivity.this, "Đăng ký tài khoản thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(getIntent());
                }else {
                    Toast.makeText(DangKyActivity.this, "Đăng ký tài khoản không thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Xử lý lỗi phản hồi từ server
                Toast.makeText(DangKyActivity.this, "e" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Gửi dữ liệu lên server
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("tenkhachhang", user_name);
                param.put("sdt", user_phone);
                param.put("email", user_mail);
                param.put("matkhau", user_password);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void actionToolbar() {
        // Thiết lập toolbar
        setSupportActionBar(toolbar_DangKy);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_DangKy.setNavigationOnClickListener(view -> finish());
        tvLogin_register.setOnClickListener(view -> startActivity(new Intent(DangKyActivity.this, DangNhapActivity.class)));
    }

    private void getViewId() {
        // Gán các view với ID tương ứng trong layout
        toolbar_DangKy = findViewById(R.id.toolbar_DangKy);
        tvLogin_register = findViewById(R.id.tvLogin_register);
        tvForgetPassword_register = findViewById(R.id.tvForgetPassword_register);
        btnRegister_register = findViewById(R.id.btnRegister_register);
        message_name = findViewById(R.id.message_name);
        message_phone = findViewById(R.id.message_phone);
        message_mail = findViewById(R.id.message_mail);
        message_password = findViewById(R.id.message_password);
        message_repassword = findViewById(R.id.message_repassword);
        user_name_register = findViewById(R.id.user_name_register);
        user_phone_register = findViewById(R.id.user_phone_register);
        user_mail_register = findViewById(R.id.user_mail_register);
        user_password_register = findViewById(R.id.user_password_register);
        user_repassword_register = findViewById(R.id.user_repassword_register);
    }
}