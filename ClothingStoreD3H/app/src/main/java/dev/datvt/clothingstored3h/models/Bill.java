package dev.datvt.clothingstored3h.models;

/**
 * Created by DatVIT on 10/27/2016.
 */

public class Bill {

    private int maHD;
    private int maKH;
    private String loaiKH;
    private String maNV;
    private double tienMat;
    private double tienATM;
    private int khuyenMai;
    private int phieuGiamGia;
    private String ngayLap;
    private double tienNo;

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getLoaiKH() {
        return loaiKH;
    }

    public void setLoaiKH(String loaiKH) {
        this.loaiKH = loaiKH;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public double getTienMat() {
        return tienMat;
    }

    public void setTienMat(double tienMat) {
        this.tienMat = tienMat;
    }

    public double getTienATM() {
        return tienATM;
    }

    public void setTienATM(double tienATM) {
        this.tienATM = tienATM;
    }

    public int getKhuyenMai() {
        return khuyenMai;
    }

    public void setKhuyenMai(int khuyenMai) {
        this.khuyenMai = khuyenMai;
    }

    public int getPhieuGiamGia() {
        return phieuGiamGia;
    }

    public void setPhieuGiamGia(int phieuGiamGia) {
        this.phieuGiamGia = phieuGiamGia;
    }

    public String getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(String ngayLap) {
        this.ngayLap = ngayLap;
    }

    public double getTienNo() {
        return tienNo;
    }

    public void setTienNo(double tienNo) {
        this.tienNo = tienNo;
    }
}
