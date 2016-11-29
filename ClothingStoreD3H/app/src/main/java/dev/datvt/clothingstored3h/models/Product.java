package dev.datvt.clothingstored3h.models;

/**
 * Created by DatVIT on 10/15/2016.
 */

public class Product {

    private String maHang;
    private String tenHang;
    private String img;

    private Properties thuocTinh;

    private double donGiaNhap;
    private double donGiaBan;
    private int soLuongNhap;
    private int soLuongBan;
    private int chietKhau;
    private int giamGia;

    private boolean sale = false;

    public Product(String ten, int soLuongNhap) {
        this.tenHang = ten;
        this.soLuongNhap = soLuongNhap;
    }

    public Product(String maHang, String tenHang, double donGia, int giamGia, int chietKhau, int soLuong, Properties thuocTinh) {
        this.maHang = maHang;
        this.tenHang = tenHang;
        this.donGiaNhap = donGia;
        this.giamGia = giamGia;
        this.chietKhau = chietKhau;
        this.soLuongNhap = soLuong;
        this.thuocTinh = thuocTinh;
    }

    public Product() {

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

    public boolean isSale() {
        return sale;
    }

    public void setSale(boolean sale) {
        this.sale = sale;
    }

    public String getMaHang() {
        return maHang;
    }

    public void setMaHang(String maHang) {
        this.maHang = maHang;
    }

    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }

    public Properties getThuocTinh() {
        return thuocTinh;
    }

    public void setThuocTinh(Properties thuocTinh) {
        this.thuocTinh = thuocTinh;
    }

    public double getDonGiaNhap() {
        return donGiaNhap;
    }

    public void setDonGiaNhap(double donGiaNhap) {
        this.donGiaNhap = donGiaNhap;
    }

    public int getSoLuongNhap() {
        return soLuongNhap;
    }

    public void setSoLuongNhap(int soLuongNhap) {
        this.soLuongNhap = soLuongNhap;
    }

    public int getChietKhau() {
        return chietKhau;
    }

    public void setChietKhau(int chietKhau) {
        this.chietKhau = chietKhau;
    }

    public int getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(int giamGia) {
        this.giamGia = giamGia;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
