package dev.datvt.clothingstored3h.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DatVIT on 12/8/2016.
 */
public class ProductOnline {

    @SerializedName("MaHang")
    public String MaHang;
    @SerializedName("DonGiaBan")
    public double DonGiaBan;
    @SerializedName("SoLuongBan")
    public int SoLuongBan;
}
