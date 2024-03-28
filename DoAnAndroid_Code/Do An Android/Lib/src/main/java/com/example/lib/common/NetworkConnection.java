package com.example.lib.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkConnection {
    /**
     * Kiểm tra kết nối mạng.
     * @param context Context của ứng dụng.
     * @return true nếu có kết nối mạng (WiFi hoặc dữ liệu di động), ngược lại trả về false.
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        // Kiểm tra xem có kết nối WiFi hoặc dữ liệu di động hay không
        if ((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected())) {
            return true;
        } else {
            return false;
        }
    }
}
