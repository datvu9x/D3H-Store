package dev.datvt.clothingstored3h.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import dev.datvt.clothingstored3h.models.Bill;
import dev.datvt.clothingstored3h.models.Customer;
import dev.datvt.clothingstored3h.models.Employee;
import dev.datvt.clothingstored3h.models.Product;
import dev.datvt.clothingstored3h.models.Properties;
import dev.datvt.clothingstored3h.models.StoreProduct;

/**
 * Created by DatVIT on 10/19/2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "D3HStore.db";
    private static final String TABLE_HANG_HOA = "HangHoa";
    private static final String TABLE_HOA_DON = "HoaDon";
    private static final String TABLE_KHACH_HANG = "KhachHang";
    private static final String TABLE_NHAN_VIEN = "NhanVien";
    private static final String TABLE_KE_HANG = "KeHang";
    private static final String TABLE_DON_HANG = "DonHang";
    private static final String TABLE_THUOC_TINH_HANG_HOA = "ThuocTinhHangHoa";

    // Hàng hóa
    private static final String KEY_MA_HANG = "MaHang";
    private static final String KEY_TEN_HANG = "TenHang";
    private static final String KEY_DON_GIA_NHAP = "DonGiaNhap";
    private static final String KEY_SO_LUONG_NHAP = "SoLuongNhap";
    private static final String KEY_CHIET_KHAU = "ChietKhau";
    private static final String KEY_GIAM_GIA = "GiamGia";

    // Hóa đơn
    private static final String KEY_MA_HOA_DON = "MaHD";
    private static final String KEY_LOAI_KH = "LoaiKH";
    private static final String KEY_TIEN_MAT = "TienMat";
    private static final String KEY_TIEN_ATM = "TienATM";
    private static final String KEY_VAT = "VAT";
    private static final String KEY_KHUYEN_MAI = "KhuyenMai";
    private static final String KEY_PHIEU_GIAM_GIA = "PhieuGiamGia";
    private static final String KEY_TIEN_NO = "TienNo";
    private static final String KEY_NGAY_LAP_HD = "NgayLapHD";

    // Khách hàng
    private static final String KEY_MA_KH = "MaKH";
    private static final String KEY_TEN_KH = "TenKH";
    private static final String KEY_GIOI_TINH = "GioiTinh";
    private static final String KEY_DIEN_THOAI = "DienThoai";
    private static final String KEY_DIA_CHI = "DiaChi";
    private static final String KEY_EMAIL = "Email";

    // Nhân viên
    private static final String KEY_MA_NV = "MaNV";
    private static final String KEY_TEN_NV = "TenNV";
    private static final String KEY_PASS_WORD = "Password";
    private static final String KEY_NGAY_SINH = "NgaySinh";

    // Thuộc tính hàng hóa
    private static final String KEY_MA_THUOC_TINH = "MaThuocTinh";
    private static final String KEY_LOAI = "Loai";
    private static final String KEY_KICH_THUOC = "KichThuoc";
    private static final String KEY_MAU_SAC = "MauSac";
    private static final String KEY_DOI_TUONG_DUNG = "DoiTuongDung";
    private static final String KEY_MUA = "Mua";
    private static final String KEY_NSX = "NSX";

    // Đơn hàng
    private static final String KEY_MA_PHIEU = "MaPhieu";
    private static final String KEY_TRANG_THAI = "TrangThai";
    private static final String KEY_LOAI_GIAO_DICH = "LoaiGiaoDich";

    // Kê hàng
    private static final String KEY_DON_GIA_BAN = "DonGiaBan";
    private static final String KEY_SO_LUONG_BAN = "SoLuongBan";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_HANG_HOA = "CREATE TABLE IF NOT EXISTS " + TABLE_HANG_HOA + "("
                + KEY_MA_HANG + " TEXT PRIMARY KEY,"
                + KEY_TEN_HANG + " TEXT,"
                + KEY_DON_GIA_NHAP + " REAL, "
                + KEY_SO_LUONG_NHAP + " INTEGER, "
                + KEY_CHIET_KHAU + " INTEGER, "
                + KEY_GIAM_GIA + " INTEGER, "
                + KEY_MA_THUOC_TINH + " INTEGER,"
                + "FOREIGN KEY (" + KEY_MA_THUOC_TINH
                + ") REFERENCES " + TABLE_THUOC_TINH_HANG_HOA + " (" + KEY_MA_THUOC_TINH + ")"
                + ");";


        String CREATE_TABLE_THUOC_TINH_HANG_HOA = "CREATE TABLE IF NOT EXISTS " + TABLE_THUOC_TINH_HANG_HOA + "("
                + KEY_MA_THUOC_TINH + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_LOAI + " TEXT,"
                + KEY_KICH_THUOC + " INTEGER,"
                + KEY_MAU_SAC + " TEXT,"
                + KEY_DOI_TUONG_DUNG + " TEXT,"
                + KEY_MUA + " TEXT,"
                + KEY_NSX + " TEXT"
                + ");";

        String CREATE_TABLE_HOA_DON = "CREATE TABLE IF NOT EXISTS " + TABLE_HOA_DON + "("
                + KEY_MA_HOA_DON + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_MA_KH + " INTEGER,"
                + KEY_LOAI_KH + " TEXT,"
                + KEY_MA_NV + " TEXT,"
                + KEY_TIEN_MAT + " REAL,"
                + KEY_TIEN_ATM + " REAL,"
                + KEY_VAT + " INTEGER,"
                + KEY_KHUYEN_MAI + " INTEGER,"
                + KEY_PHIEU_GIAM_GIA + " INTEGER,"
                + KEY_TIEN_NO + " REAL,"
                + KEY_NGAY_LAP_HD + " TEXT,"
                + "FOREIGN KEY (" + KEY_MA_KH
                + ") REFERENCES " + TABLE_KHACH_HANG + " (" + KEY_MA_KH + "), "
                + "FOREIGN KEY (" + KEY_MA_NV
                + ") REFERENCES " + TABLE_NHAN_VIEN + " (" + KEY_MA_NV + ")"
                + ");";

        String CREATE_TABLE_KHACH_HANG = "CREATE TABLE IF NOT EXISTS " +
                TABLE_KHACH_HANG + "("
                + KEY_MA_KH + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TEN_KH + " TEXT,"
                + KEY_GIOI_TINH + " TEXT,"
                + KEY_DIA_CHI + " TEXT,"
                + KEY_DIEN_THOAI + " TEXT,"
                + KEY_EMAIL + " TEXT"
                + ");";

        String CREATE_TABLE_NHAN_VIEN = "CREATE TABLE IF NOT EXISTS " + TABLE_NHAN_VIEN + "("
                + KEY_MA_NV + " TEXT PRIMARY KEY,"
                + KEY_PASS_WORD + " TEXT,"
                + KEY_TEN_NV + " TEXT,"
                + KEY_GIOI_TINH + " TEXT,"
                + KEY_NGAY_SINH + " TEXT,"
                + KEY_DIA_CHI + " TEXT,"
                + KEY_DIEN_THOAI + " TEXT,"
                + KEY_EMAIL + " TEXT"
                + ");";

        String CREATE_TABLE_DON_HANG = "CREATE TABLE IF NOT EXISTS " + TABLE_DON_HANG + "("
                + KEY_MA_PHIEU + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TRANG_THAI + " TEXT,"
                + KEY_LOAI_GIAO_DICH + " TEXT,"
                + KEY_MA_HOA_DON + " INTEGER,"
                + "FOREIGN KEY (" + KEY_MA_HOA_DON
                + ") REFERENCES " + TABLE_HOA_DON + " (" + KEY_MA_HOA_DON + ")"
                + ");";

        String CREATE_TABLE_KE_HANG = "CREATE TABLE IF NOT EXISTS " + TABLE_KE_HANG + "("
                + KEY_MA_HANG + " TEXT,"
                + KEY_MA_HOA_DON + " INTEGER,"
                + KEY_DON_GIA_BAN + " REAL,"
                + KEY_SO_LUONG_BAN + " INTEGER,"
                + "PRIMARY KEY(" + KEY_MA_HANG + "," + KEY_MA_HOA_DON + "), "
                + "FOREIGN KEY (" + KEY_MA_HANG
                + ") REFERENCES " + TABLE_HANG_HOA + " (" + KEY_MA_HANG + "),"
                + "FOREIGN KEY (" + KEY_MA_HOA_DON
                + ") REFERENCES " + TABLE_HOA_DON + " (" + KEY_MA_HOA_DON + ")"
                + ");";

        db.execSQL(CREATE_TABLE_HANG_HOA);
        db.execSQL(CREATE_TABLE_THUOC_TINH_HANG_HOA);
        db.execSQL(CREATE_TABLE_HOA_DON);
        db.execSQL(CREATE_TABLE_KHACH_HANG);
        db.execSQL(CREATE_TABLE_NHAN_VIEN);
        db.execSQL(CREATE_TABLE_DON_HANG);
        db.execSQL(CREATE_TABLE_KE_HANG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HANG_HOA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOA_DON);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KHACH_HANG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NHAN_VIEN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KE_HANG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DON_HANG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_THUOC_TINH_HANG_HOA);
        onCreate(db);
    }

    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MA_HANG, product.getMaHang());
        values.put(KEY_TEN_HANG, product.getTenHang());
        values.put(KEY_DON_GIA_NHAP, product.getDonGiaNhap());
        values.put(KEY_SO_LUONG_NHAP, product.getSoLuongNhap());
        values.put(KEY_CHIET_KHAU, product.getChietKhau());
        values.put(KEY_GIAM_GIA, product.getGiamGia());
        values.put(KEY_MA_THUOC_TINH, product.getThuocTinh().getMa());

        db.insert(TABLE_HANG_HOA, null, values);
        db.close();
    }

    public void addThuocTinh(Properties properties) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LOAI, properties.getLoai());
        values.put(KEY_KICH_THUOC, properties.getKichThuoc());
        values.put(KEY_MAU_SAC, properties.getMauSac());
        values.put(KEY_DOI_TUONG_DUNG, properties.getDoiTuong());
        values.put(KEY_MUA, properties.getMua());
        values.put(KEY_NSX, properties.getNsx());

        db.insert(TABLE_THUOC_TINH_HANG_HOA, null, values);
        db.close();
    }

    public void addEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MA_NV, employee.getId());
        values.put(KEY_PASS_WORD, employee.getPass());
        values.put(KEY_TEN_NV, employee.getName());
        values.put(KEY_GIOI_TINH, employee.getGender());
        values.put(KEY_NGAY_SINH, employee.getDateOfBirth());
        values.put(KEY_DIA_CHI, employee.getAddress());
        values.put(KEY_DIEN_THOAI, employee.getPhone());
        values.put(KEY_EMAIL, employee.getEmail());

        db.insert(TABLE_NHAN_VIEN, null, values);
        db.close();
    }

    public void addCustomer(Customer customer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TEN_KH, customer.getName());
        values.put(KEY_GIOI_TINH, customer.getGender());
        values.put(KEY_DIA_CHI, customer.getAddress());
        values.put(KEY_DIEN_THOAI, customer.getPhone());
        values.put(KEY_EMAIL, customer.getEmail());

        db.insert(TABLE_KHACH_HANG, null, values);
        db.close();
    }

    public void addBill(Bill bill) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MA_KH, bill.getMaKH());
        values.put(KEY_LOAI_KH, bill.getLoaiKH());
        values.put(KEY_MA_NV, bill.getMaNV());
        values.put(KEY_TIEN_MAT, bill.getTienMat());
        values.put(KEY_TIEN_ATM, bill.getTienATM());
        values.put(KEY_VAT, bill.getVat());
        values.put(KEY_KHUYEN_MAI, bill.getKhuyenMai());
        values.put(KEY_PHIEU_GIAM_GIA, bill.getPhieuGiamGia());
        values.put(KEY_TIEN_NO, bill.getSoNo());
        values.put(KEY_NGAY_LAP_HD, bill.getNgayLap());

        db.insert(TABLE_HOA_DON, null, values);
        db.close();
    }

    public void addKeHang(StoreProduct storeProduct) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MA_HOA_DON, storeProduct.getMaHD());
        values.put(KEY_MA_HANG, storeProduct.getMaHang());
        values.put(KEY_DON_GIA_BAN, storeProduct.getDonGia());
        values.put(KEY_SO_LUONG_BAN, storeProduct.getSoLuong());

        db.insert(TABLE_KE_HANG, null, values);
        db.close();
    }

    // -----------------------------------------------------
    public int isCheckProperties(String loai, String kichThuoc, String mauSac, String doiTuong, String mua, String nsx) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_THUOC_TINH_HANG_HOA, null,
                KEY_LOAI + "=? AND " + KEY_KICH_THUOC + "=? AND " + KEY_MAU_SAC + "=? AND " +
                        KEY_DOI_TUONG_DUNG + "=? AND " + KEY_MUA + "=? AND " + KEY_NSX + "=?"
                , new String[]{loai, kichThuoc, mauSac, doiTuong, mua, nsx}, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    int ma = cursor.getInt(0);
                    return ma;
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return -1;
    }

    public boolean isCheckProduct(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_HANG_HOA, null, KEY_MA_HANG + "=?", new String[]{id}, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        return false;
    }

    public boolean isCheckEmployee(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NHAN_VIEN, new String[]{KEY_MA_NV,
                KEY_PASS_WORD, KEY_TEN_NV, KEY_GIOI_TINH, KEY_NGAY_SINH, KEY_DIA_CHI, KEY_DIEN_THOAI,
                KEY_EMAIL}, KEY_MA_NV + "=?", new String[]{id}, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        return false;
    }

    public boolean isCheckLogin(String id, String pass) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NHAN_VIEN, new String[]{KEY_MA_NV,
                KEY_PASS_WORD, KEY_TEN_NV, KEY_GIOI_TINH, KEY_NGAY_SINH, KEY_DIA_CHI, KEY_DIEN_THOAI,
                KEY_EMAIL}, KEY_MA_NV + "=? AND " + KEY_PASS_WORD + "=?", new String[]{id, pass}, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        return false;
    }

    // -----------------------------------------------------
    public List<Employee> getAllEmployees() {
        List<Employee> employeeArrayList = new ArrayList<Employee>();
        String selectQuery = "SELECT  * FROM " + TABLE_NHAN_VIEN;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    Employee employee = new Employee(cursor.getString(0), cursor.getString(1),
                            cursor.getString(2), cursor.getString(3), cursor.getString(4),
                            cursor.getString(5), cursor.getString(6), cursor.getString(7));
                    employeeArrayList.add(employee);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        return employeeArrayList;
    }

    public List<String> getAllIDEmployees() {
        List<String> employeeArrayList = new ArrayList<String>();
        String selectQuery = "SELECT " + KEY_MA_NV + " FROM " + TABLE_NHAN_VIEN;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    employeeArrayList.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        return employeeArrayList;
    }

    public Employee getEmployee(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Employee employee = null;

        Cursor cursor = db.query(TABLE_NHAN_VIEN, new String[]{KEY_MA_NV,
                KEY_PASS_WORD, KEY_TEN_NV, KEY_GIOI_TINH, KEY_NGAY_SINH, KEY_DIA_CHI, KEY_DIEN_THOAI,
                KEY_EMAIL}, KEY_MA_NV + "=?", new String[]{id}, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            employee = new Employee(cursor.getString(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3), cursor.getString(4),
                    cursor.getString(5), cursor.getString(6), cursor.getString(7));
            cursor.close();
            return employee;
        }
        return employee;
    }

    public Product getProduct(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Product product = null;
        Cursor cursor = db.query(TABLE_HANG_HOA, null, KEY_MA_HANG + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                product = new Product();
                product.setMaHang(cursor.getString(0));
                product.setTenHang(cursor.getString(1));
                product.setDonGiaNhap(Double.valueOf(cursor.getString(2)));
                product.setSoLuongNhap(Integer.valueOf(cursor.getString(3)));
                product.setChietKhau(Integer.valueOf(cursor.getString(4)));
                product.setGiamGia(Integer.valueOf(cursor.getString(5)));

                Cursor cursor2 = db.query(TABLE_THUOC_TINH_HANG_HOA, null, KEY_MA_THUOC_TINH + "=?",
                        new String[]{String.valueOf(cursor.getString(6))}, null, null, null, null);

                if (cursor2 != null) {
                    cursor2.moveToFirst();
                    Properties properties = null;
                    while (!cursor2.isAfterLast()) {
                        properties = new Properties();
                        properties.setMa(cursor2.getString(0));
                        properties.setLoai(cursor2.getString(1));
                        properties.setKichThuoc(Integer.parseInt(cursor2.getString(2)));
                        properties.setMauSac(cursor2.getString(3));
                        properties.setDoiTuong(cursor2.getString(4));
                        properties.setMua(cursor2.getString(5));
                        properties.setNsx(cursor2.getString(6));
                        product.setThuocTinh(properties);
                        break;
                    }

                    cursor2.close();
                }
            }
            cursor.close();
        }

        return product;
    }

    public Customer getCustomer(String name, String gender, String address, String phone, String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Customer customer = null;
        Cursor cursor = db.query(TABLE_KHACH_HANG, null, KEY_TEN_KH + "=? AND " + KEY_EMAIL + "=? AND "
                +  KEY_GIOI_TINH + "=? AND " +  KEY_DIA_CHI + "=? AND " +  KEY_DIEN_THOAI + "=?",
                new String[]{name, email, gender, address, phone}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                customer = new Customer();
                customer.setId(cursor.getString(0));
                customer.setName(cursor.getString(1));
                customer.setGender(cursor.getString(2));
                customer.setAddress(cursor.getString(3));
                customer.setPhone(cursor.getString(4));
                customer.setEmail(cursor.getString(5));
            }
            cursor.close();
        }

        return customer;
    }

    public String getBillID(String makh, String manv, String loaikh, String ngaylap) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_HOA_DON, null, KEY_MA_KH + "=? AND " + KEY_MA_NV + "=? AND "
                        +  KEY_LOAI_KH + "=? AND " +  KEY_NGAY_LAP_HD + "=?",
                new String[]{makh, manv, loaikh, ngaylap}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
               return cursor.getString(0);
            }
            cursor.close();
        }

        return null;
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<Product>();
        String selectQuery = "SELECT * FROM " + TABLE_HANG_HOA;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Cursor cursor1 = null;

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Product product = new Product();
                product.setMaHang(cursor.getString(0));
                product.setTenHang(cursor.getString(1));
                product.setDonGiaNhap(Double.parseDouble(cursor.getString(2)));
                product.setSoLuongNhap(Integer.parseInt(cursor.getString(3)));
                product.setChietKhau(Integer.parseInt(cursor.getString(4)));
                product.setGiamGia(Integer.parseInt(cursor.getString(5)));

                cursor1 = db.query(TABLE_THUOC_TINH_HANG_HOA, null, KEY_MA_THUOC_TINH + "=?", new String[]{cursor.getString(6)},
                        null, null, null, null);

                if (cursor1 != null && cursor1.getCount() > 0) {
                    cursor1.moveToFirst();
                    Properties properties = null;

                    while (!cursor1.isAfterLast()) {
                        properties = new Properties();
                        properties.setMa(cursor1.getString(0));
                        properties.setLoai(cursor1.getString(1));
                        properties.setKichThuoc(Integer.parseInt(cursor1.getString(2)));
                        properties.setMauSac(cursor1.getString(3));
                        properties.setDoiTuong(cursor1.getString(4));
                        properties.setMua(cursor1.getString(5));
                        properties.setNsx(cursor1.getString(6));
                        product.setThuocTinh(properties);
                        break;
                    }
                    cursor1.close();
                }
                productList.add(product);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return productList;
    }

}
