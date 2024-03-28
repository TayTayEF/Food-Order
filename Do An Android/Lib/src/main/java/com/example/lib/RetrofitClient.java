package com.example.lib;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;

    // Phương thức tạo và cung cấp đối tượng Retrofit với URL được chỉ định.
    public static Retrofit getRetrofit(String url) {
        // Kiểm tra nếu đối tượng Retrofit chưa được khởi tạo.
        if(retrofit == null) {
            // Sử dụng Retrofit.Builder để xây dựng đối tượng Retrofit.
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)  // Đặt URL cơ sở cho Retrofit.
                    .addConverterFactory(GsonConverterFactory.create())  // Sử dụng GsonConverterFactory để chuyển đổi dữ liệu JSON thành đối tượng Java.
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())  // Sử dụng RxJava3CallAdapterFactory cho quá trình gọi Retrofit.
                    .build();  // Xây dựng đối tượng Retrofit.
        }

        return retrofit;
    }
}
