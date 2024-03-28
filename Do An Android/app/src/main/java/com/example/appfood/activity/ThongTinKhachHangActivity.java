package com.example.appfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
import com.example.lib.common.NetworkConnection;
import com.example.lib.common.Show;
import com.example.lib.common.Url;
import com.example.lib.common.Validate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ThongTinKhachHangActivity extends AppCompatActivity {
    Toolbar toolbarThanhToan; // Toolbar của activity
    static Button btn_xacnhanthanhtoan; // Nút xác nhận thanh toán
    public static EditText user_name; // Trường nhập tên khách hàng
    static EditText user_address; // Trường nhập địa chỉ
    static EditText user_phone; // Trường nhập số điện thoại
    static EditText user_note; // Trường nhập ghi chú
    TextView textview_tongtien; // Hiển thị tổng tiền
    static TextView message_name; // Thông báo lỗi tên
    static TextView message_address; // Thông báo lỗi địa chỉ
    static TextView message_phone; // Thông báo lỗi số điện thoại

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_khach_hang); // Thiết lập layout

        getViewId(); // Lấy ID của các view từ layout
        actionToolbar(); // Thiết lập hành động cho toolbar

        // Kiểm tra kết nối mạng
        if(NetworkConnection.isConnected(this)) {
            tinhTongTienGioHang(); // Tính tổng tiền giỏ hàng
            xacnhanthanhtoan(); // Xác nhận thanh toán
        } else {
            Show.Notify(this, getString(R.string.error_network)); // Hiển thị thông báo lỗi kết nối
            finish(); // Đóng activity
        }
    }

    public void xacnhanthanhtoan() {
        // Sự kiện cho nút xác nhận thanh toán
        btn_xacnhanthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy thông tin từ các trường nhập và gửi lên server
                final String name = user_name.getText().toString();
                final String address = user_address.getText().toString();
                final String phone = user_phone.getText().toString();
                final String totalPrice = String.valueOf(Show.tinhTongTien());;
                final String note = user_note.getText().toString();

                if(Validate.isValidName(name,message_name)
                        && Validate.isValidPhone(phone,10,message_phone) && Validate.isValidAddress(address, message_address)) {
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.postUserInfo, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            Log.d("id",madonhang);

                            //post chitietdonhang
                            if(Integer.parseInt(madonhang) > 0)
                            {
                                RequestQueue detailQueue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest detailRequest = new StringRequest(Request.Method.POST, Url.postBillDetail, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.equals("1")) {
                                            Show.Notify(getApplicationContext(),getString(R.string.order_send_success));

                                            //trở về home
                                            Intent thanhcong = new Intent(getApplicationContext(),SuccessCheckoutActivity.class);
                                            startActivity(thanhcong);
                                        }else{
                                            Show.Notify(getApplicationContext(),getString(R.string.order_send_error));
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Show.Notify(getApplicationContext(),getString(R.string.order_send_error));
                                    }
                                }){
                                    @Nullable
                                    @Override
                                    //post chitietdonhang
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for(int i=0; i < Show.listGiohang.size();i++) {
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("madonhang",madonhang);
                                                jsonObject.put("mamon",Show.listGiohang.get(i).getMamon());
                                                jsonObject.put("tenmon",Show.listGiohang.get(i).getTenmon());
                                                jsonObject.put("gia",Show.listGiohang.get(i).getGia());
                                                jsonObject.put("soluong",Show.listGiohang.get(i).getSoluong());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        //Gửi dữ liệu lên
                                        HashMap<String,String> hashMap = new HashMap<String,String>();
                                        hashMap.put("json",jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                detailQueue.add(detailRequest);
                            }
                        }
                    }, new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            Show.Notify(getApplicationContext(),getString(R.string.order_send_error));
                        }
                    }){
                        @Nullable
                        @Override
                        //post donhang
                        protected Map<String, String> getParams() throws AuthFailureError
                        {
                            HashMap<String,String> hashMap = new HashMap<String,String>();
                            hashMap.put("tenkhachhang",name);
                            hashMap.put("diachi",address);
                            hashMap.put("sodienthoai",phone);
                            hashMap.put("tongtien",totalPrice);
                            hashMap.put("ghichu",note);

                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else{
                    Validate.isValidName(name,message_name);
                    Validate.isValidAddress(address,message_address);
                    Validate.isValidPhone(phone,10,message_phone);
                }
            }
        });
    }

    private void getViewId() {
        // Gán các view với ID tương ứng trong layout
        toolbarThanhToan = findViewById(R.id.toolbarThanhToan);
        btn_xacnhanthanhtoan = findViewById(R.id.btn_xacnhanthanhtoan);
        user_name = findViewById(R.id.user_name);
        user_address = findViewById(R.id.user_address);
        user_phone = findViewById(R.id.user_phone);
        textview_tongtien= findViewById(R.id.textview_tongtien);
        message_name= findViewById(R.id.message_name);
        message_address= findViewById(R.id.message_address);
        message_phone= findViewById(R.id.message_phone);
        user_note= findViewById(R.id.user_note);
    }

    private void actionToolbar() {
        // Thiết lập toolbar
        setSupportActionBar(toolbarThanhToan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarThanhToan.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void tinhTongTienGioHang() {
        // Tính tổng tiền của giỏ hàng và hiển thị
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        textview_tongtien.setText(decimalFormat.format(Show.tinhTongTien()) + " đ");
    }

    // Các phương thức khác để chuyển đến các activity khác như trang chủ...

    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật tổng tiền khi activity tiếp tục
        tinhTongTienGioHang();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Cập nhật tổng tiền khi activity bắt đầu
        tinhTongTienGioHang();
    }
}