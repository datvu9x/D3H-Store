package dev.datvt.clothingstored3h.models;

import java.io.Serializable;

/**
 * Created by DatVIT on 10/27/2016.
 */

public class StoreBill implements Serializable {

    private int maPhieu;
    private String trangThai;
    private String loaGiaoDich;
    private int maHD;

    public int getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(int maPhieu) {
        this.maPhieu = maPhieu;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getLoaGiaoDich() {
        return loaGiaoDich;
    }

    public void setLoaGiaoDich(String loaGiaoDich) {
        this.loaGiaoDich = loaGiaoDich;
    }


}
