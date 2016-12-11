package dev.datvt.clothingstored3h.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by DatVIT on 12/8/2016.
 */
public class BillOnline implements Serializable {

    @SerializedName("TenKH")
    public String TenKH;
    @SerializedName("GioiTinh")
    public String GioiTinh;
    @SerializedName("DiaChi")
    public String DiaChi;
    @SerializedName("DienThoai")
    public String DienThoai;
    @SerializedName("Email")
    public String Email;
    @SerializedName("LoaiKH")
    public String LoaiKH;
    @SerializedName("KhuyenMai")
    public int KhuyenMai;
    @SerializedName("NgayMuaHang")
    public String NgayMuaHang;
    @SerializedName("SanPham")
    public ArrayList<ProductOnline> SanPham;
}
