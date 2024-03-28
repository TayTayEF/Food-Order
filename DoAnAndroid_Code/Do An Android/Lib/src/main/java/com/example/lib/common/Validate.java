package com.example.lib.common;

import android.view.View;
import android.widget.TextView;

public class Validate {
    // Kiểm tra tính hợp lệ của tên khách hàng
    public static boolean isValidName(String value, TextView textView) {
        if (value.trim().length() <= 0) {
            textView.setVisibility(View.VISIBLE);
            textView.setText("Vui lòng nhập tên khách hàng!");
            return false;
        }
        textView.setVisibility(View.GONE);
        textView.setText(" ");
        return true;
    }

    // Kiểm tra tính hợp lệ của địa chỉ nhận hàng
    public static boolean isValidAddress(String value, TextView textView) {
        if (value.trim().length() <= 0) {
            textView.setVisibility(View.VISIBLE);
            textView.setText("Vui lòng nhập địa chỉ nhận hàng!");
            return false;
        }
        textView.setVisibility(View.GONE);
        textView.setText(" ");
        return true;
    }

    // Kiểm tra tính hợp lệ của địa chỉ email và đúng định dạng
    static boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidEmail(String value, TextView textView) {
        if (value.trim().length() <= 0) {
            textView.setVisibility(View.VISIBLE);
            textView.setText("Vui lòng nhập email!");
            return false;
        } else if (!isEmailValid(value.trim())) {
            textView.setVisibility(View.VISIBLE);
            textView.setText("Email chưa đúng định dạng!");
            return false;
        }
        textView.setVisibility(View.GONE);
        textView.setText(" ");
        return true;
    }

    // Kiểm tra tính hợp lệ của số điện thoại và không vượt quá số ký tự tối đa
    public static boolean isValidPhone(String value, int max, TextView textView) {
        if (value.trim().length() <= 0) {
            textView.setVisibility(View.VISIBLE);
            textView.setText("Vui lòng nhập số điện thoại!");
            return false;
        } else if (value.trim().length() > max) {
            textView.setVisibility(View.VISIBLE);
            textView.setText("Số điện thoại không được vượt quá " + max + " ký tự!");
            return false;
        }
        textView.setVisibility(View.GONE);
        textView.setText(" ");
        return true;
    }

    // Kiểm tra tính hợp lệ của giá trị nhập vào
    public static boolean isValidValue(String value, TextView textView) {
        if (value.trim().length() <= 0) {
            textView.setVisibility(View.VISIBLE);
            textView.setText("Vui lòng nhập đầy đủ thông tin!");
            return false;
        }
        textView.setVisibility(View.GONE);
        textView.setText(" ");
        return true;
    }
}
