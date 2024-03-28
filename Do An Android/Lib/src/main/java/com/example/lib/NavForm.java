package com.example.lib;

// Lớp dữ liệu NavForm đại diện cho một mục trong thanh điều hướng (navigation bar).
public class NavForm {
    int hinhminhhoa; // Biến lưu trữ ID hình minh họa của mục.
    String noidung;  // Biến lưu trữ nội dung hoặc thông tin của mục.

    // Hàm khởi tạo mặc định.
    public NavForm() {
    }

    // Getter cho biến hinhminhhoa.
    public int getHinhminhhoa() {
        return hinhminhhoa;
    }

    // Setter cho biến hinhminhhoa.
    public void setHinhminhhoa(int hinhminhhoa) {
        this.hinhminhhoa = hinhminhhoa;
    }

    // Getter cho biến noidung.
    public String getNoidung() {
        return noidung;
    }

    // Setter cho biến noidung.
    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    // Hàm khởi tạo với tham số.
    public NavForm(int hinhminhhoa, String noidung) {
        this.hinhminhhoa = hinhminhhoa;
        this.noidung = noidung;
    }
}
