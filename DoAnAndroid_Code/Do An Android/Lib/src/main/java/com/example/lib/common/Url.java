package com.example.lib.common;

public class Url {
    // Địa chỉ IP và các đường dẫn tới các tài nguyên của ứng dụng
    private static String ipv4Address = "192.168.1.3";
    public static final String AppFood_Url = "http://" + ipv4Address + "/MiniShop/";
    public static final String postUserInfo = "http://" + ipv4Address + "/MiniShop/thongtinkhachhang.php";
    public static final String postBillDetail = "http://" + ipv4Address + "/MiniShop/chitietdonhang.php";
    public static final String linkSlider = "http://" + ipv4Address + "/MiniShop/slide/";
    public static final String linkDanhMuc = "http://" + ipv4Address + "/MiniShop/danhmuc/";
    public static final String linkMon = "http://" + ipv4Address + "/MiniShop/mon/";
    public static final String getSlider = "http://" + ipv4Address + "/MiniShop/slider.php";
    public static final String thongTinKH = "http://" + ipv4Address + "/MiniShop/thongtinnguoidung.php";
    public static final String postBinhLuan = "http://" + ipv4Address + "/MiniShop/thembinhluan.php";
    public static final String checkSigIn = "http://" + ipv4Address + "/MiniShop/kiemtradangnhap.php";
    public static final String checkSigUp = "http://" + ipv4Address + "/MiniShop/kiemtradangky.php";
    public static final String sigUp = "http://" + ipv4Address + "/MiniShop/dangky.php";
}
