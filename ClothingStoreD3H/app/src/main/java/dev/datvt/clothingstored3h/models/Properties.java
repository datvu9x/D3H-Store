package dev.datvt.clothingstored3h.models;

/**
 * Created by DatVIT on 10/27/2016.
 */

public class Properties {

    private String ma;
    private String loai;
    private String mauSac;
    private String doiTuong;
    private String nsx;
    private String mua;
    private int kichThuoc;

    public Properties(String loai, String mauSac, String doiTuong, String nsx, String mua, int kichThuoc) {
        this.loai = loai;
        this.mauSac = mauSac;
        this.doiTuong = doiTuong;
        this.nsx = nsx;
        this.mua = mua;
        this.kichThuoc = kichThuoc;
    }

    public Properties() {}

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getDoiTuong() {
        return doiTuong;
    }

    public void setDoiTuong(String doiTuong) {
        this.doiTuong = doiTuong;
    }

    public String getNsx() {
        return nsx;
    }

    public void setNsx(String nsx) {
        this.nsx = nsx;
    }

    public String getMua() {
        return mua;
    }

    public void setMua(String mua) {
        this.mua = mua;
    }

    public int getKichThuoc() {
        return kichThuoc;
    }

    public void setKichThuoc(int kichThuoc) {
        this.kichThuoc = kichThuoc;
    }
}
