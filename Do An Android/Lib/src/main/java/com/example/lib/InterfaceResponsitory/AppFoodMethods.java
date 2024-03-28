package com.example.lib.InterfaceResponsitory;

import com.example.lib.model.BinhLuan;
import com.example.lib.model.DanhMuc;
import com.example.lib.model.Mon;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AppFoodMethods {
    // Phương thức GET để lấy danh mục từ máy chủ
    @GET("danhmuc.php")
    Observable<DanhMuc> GET_DanhMuc();

    // Phương thức GET để lấy danh sách món ngẫu nhiên từ máy chủ
    @GET("monngaunhien.php")
    Observable<Mon> GET_MonNgauNhien();

    // Phương thức POST để lấy danh sách món theo danh mục từ máy chủ
    @POST("chitietdanhmuc.php")
    @FormUrlEncoded
    Observable<Mon> POST_MonTheoDanhMuc(
            @Field("madanhmuc") int madanhmuc
    );

    // Phương thức POST để tìm kiếm món từ máy chủ dựa trên tên món
    @POST("timkiemmon.php")
    @FormUrlEncoded
    Observable<Mon> POST_TimKiemMon(
            @Field("tenmon") String tenmon
    );

    // Phương thức POST để đăng bình luận lên máy chủ dựa trên mã món
    @POST("binhluan.php")
    @FormUrlEncoded
    Observable<BinhLuan> POST_BinhLuan(
            @Field("mamon") String mamon
    );
}
