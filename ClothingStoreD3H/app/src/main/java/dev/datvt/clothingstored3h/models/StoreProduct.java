package dev.datvt.clothingstored3h.models;

/**
 * Created by DatVIT on 10/27/2016.
 */

public class StoreProduct {

    private int maHD;
    private String maHang;
    private double donGiaBan;
    private int soLuongBan;

    public StoreProduct(String maHang, int maHD, double donGia, int soLuong) {
        this.maHang = maHang;
        this.maHD = maHD;
        this.donGiaBan = donGia;
        this.soLuongBan = soLuong;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public String getMaHang() {
        return maHang;
    }

    public void setMaHang(String maHang) {
        this.maHang = maHang;
    }

    public double getDonGiaBan() {
        return donGiaBan;
    }

    public void setDonGiaBan(double donGiaBan) {
        this.donGiaBan = donGiaBan;
    }

    public int getSoLuongBan() {
        return soLuongBan;
    }

    public void setSoLuongBan(int soLuongBan) {
        this.soLuongBan = soLuongBan;
    }
}
