package dev.datvt.clothingstored3h.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by DatVIT on 10/15/2016.
 */

public class Product implements Parcelable {

    private String maHang;
    private String tenHang;

    private Properties thuocTinh;

    private double donGiaNhap;
    private double donGiaBan;
    private int soLuongNhap;
    private int soLuongBan;
    private int soLuongConLai;
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

    public int getSoLuongConLai() {
        return soLuongConLai;
    }

    public void setSoLuongConLai(int soLuongConLai) {
        this.soLuongConLai = soLuongConLai;
    }

    protected Product(Parcel in) {
        maHang = in.readString();
        tenHang = in.readString();
        donGiaNhap = in.readDouble();
        donGiaBan = in.readDouble();
        soLuongNhap = in.readInt();
        soLuongBan = in.readInt();
        chietKhau = in.readInt();
        giamGia = in.readInt();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(maHang);
        dest.writeString(tenHang);
        dest.writeDouble(donGiaNhap);
        dest.writeDouble(donGiaBan);
        dest.writeInt(soLuongNhap);
        dest.writeInt(soLuongBan);
        dest.writeInt(chietKhau);
        dest.writeInt(giamGia);
    }
}
