package com.example.lib.model;

import java.io.Serializable;
import java.util.List;

public class BinhLuan implements Serializable {
    public static class Result implements Serializable {
        private int id;
        private int mamon;
        private String sdt;
        private String noidung;

        public Result(int id, int mamon, String sdt, String noidung) {
            this.id = id;
            this.mamon = mamon;
            this.sdt = sdt;
            this.noidung = noidung;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMamon() {
            return mamon;
        }

        public void setMamon(int mamon) {
            this.mamon = mamon;
        }

        public String getSdt() {
            return sdt;
        }

        public void setSdt(String sdt) {
            this.sdt = sdt;
        }

        public String getNoidung() {
            return noidung;
        }

        public void setNoidung(String noidung) {
            this.noidung = noidung;
        }
    }
    boolean success;
    String message;
    List<BinhLuan.Result> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }
}
