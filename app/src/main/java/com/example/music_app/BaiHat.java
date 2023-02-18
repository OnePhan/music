package com.example.music_app;

public class BaiHat {
    private int Anh;
    private String tenBaiHat;
    private String tenTacGia;
    private int linkBai;

    public BaiHat(int anh, String tenBaiHat, String tenTacGia, int linkBai) {
        Anh = anh;
        this.tenBaiHat = tenBaiHat;
        this.tenTacGia = tenTacGia;
        this.linkBai = linkBai;
    }

    public int getAnh() {
        return Anh;
    }

    public void setAnh(int anh) {
        Anh = anh;
    }

    public String getTenBaiHat() {
        return tenBaiHat;
    }

    public void setTenBaiHat(String tenBaiHat) {
        this.tenBaiHat = tenBaiHat;
    }

    public String getTenTacGia() {
        return tenTacGia;
    }

    public void setTenTacGia(String tenTacGia) {
        this.tenTacGia = tenTacGia;
    }

    public int getLinkBai() {
        return linkBai;
    }

    public void setLinkBai(int linkBai) {
        this.linkBai = linkBai;
    }
}
