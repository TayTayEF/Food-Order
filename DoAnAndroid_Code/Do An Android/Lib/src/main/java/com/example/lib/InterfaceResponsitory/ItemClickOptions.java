package com.example.lib.InterfaceResponsitory;

import android.view.View;

// Giao diện định nghĩa phương thức onClickOptions để xử lý sự kiện khi một tùy chọn được chọn trong giao diện.
public interface ItemClickOptions {
    // Phương thức này sẽ được triệu hồi khi một tùy chọn được chọn trong giao diện.
    // - view: Đối tượng View mà tùy chọn được chọn.
    // - pos: Vị trí của tùy chọn trong danh sách hoặc giao diện.
    // - value: Giá trị tham số tùy chọn (có thể là một số nguyên hoặc giá trị tùy chỉnh khác).
    void onClickOptions(View view, int pos, int value);
}
