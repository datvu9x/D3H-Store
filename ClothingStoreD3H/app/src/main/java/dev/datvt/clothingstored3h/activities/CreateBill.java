package dev.datvt.clothingstored3h.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dev.datvt.clothingstored3h.R;
import dev.datvt.clothingstored3h.adapters.MyProductAdapter;
import dev.datvt.clothingstored3h.models.Bill;
import dev.datvt.clothingstored3h.models.Customer;
import dev.datvt.clothingstored3h.models.Product;
import dev.datvt.clothingstored3h.models.StoreBill;
import dev.datvt.clothingstored3h.models.StoreProduct;
import dev.datvt.clothingstored3h.utils.ConstantHelper;
import dev.datvt.clothingstored3h.utils.DatabaseHandler;

/**
 * Created by DatVIT on 10/27/2016.
 */

public class CreateBill extends RootActivity implements View.OnClickListener {

    private ImageView btnBack;

    private Spinner spLoaiKH, spGioiTinh;
    private ImageView btnAddProduct;
    private TextView tvTongTienHang, tvTongTien, tvTienThua, btnCreateBill, btnResetText, tvChietKhau, tvGiamGia;
    private EditText etKH, etDienThoai, etDiaChi, etEmail, etKhuyenMai, etPhieuGiamGia, etTienATM, etTienMat;
    private ListView listView;
    private ArrayList<Product> arrayList;
    private MyProductAdapter productArrayAdapter;

    private DatabaseHandler databaseHandler;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private double tienNo = 0, tienHang = 0, tienPhaiTra = 0, tienThua = 0, tienMatKH = 0, tienATMKH = 0, tienChietKhau = 0, tienGiamGia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_bill);

        databaseHandler = new DatabaseHandler(this);

        spLoaiKH = (Spinner) findViewById(R.id.spLoaiKH);
        spGioiTinh = (Spinner) findViewById(R.id.spGioiTinh);
        btnAddProduct = (ImageView) findViewById(R.id.btnAddProduct);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        tvTongTien = (TextView) findViewById(R.id.tvTongTien);
        tvTongTienHang = (TextView) findViewById(R.id.tvTongTienHang);
        tvTienThua = (TextView) findViewById(R.id.tvTienThua);
        btnCreateBill = (TextView) findViewById(R.id.btnCreateBill);
        btnResetText = (TextView) findViewById(R.id.btnResetText);
        tvChietKhau = (TextView) findViewById(R.id.tvChietKhau);
        tvGiamGia = (TextView) findViewById(R.id.tvGiamGia);

        etKH = (EditText) findViewById(R.id.etKhachHang);
        etDiaChi = (EditText) findViewById(R.id.etDiaChi);
        etDienThoai = (EditText) findViewById(R.id.etDienThoai);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etKhuyenMai = (EditText) findViewById(R.id.etKhuyenMai);
        etPhieuGiamGia = (EditText) findViewById(R.id.etPhieuGiamGia);
        etTienATM = (EditText) findViewById(R.id.etTienATM);
        etTienMat = (EditText) findViewById(R.id.etTienMat);

        listView = (ListView) findViewById(R.id.listProductInBill);
        arrayList = new ArrayList<>();
        productArrayAdapter = new MyProductAdapter(CreateBill.this, arrayList);
        listView.setAdapter(productArrayAdapter);

        ArrayList<String> arrLoaiKH = new ArrayList<>();
        arrLoaiKH.add("Khách buôn");
        arrLoaiKH.add("Khách lẻ");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CreateBill.this, android.R.layout.simple_spinner_item, arrLoaiKH) {

            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);

                ((TextView) v).setGravity(Gravity.RIGHT);

                return v;

            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                View v = super.getDropDownView(position, convertView, parent);

                ((TextView) v).setGravity(Gravity.RIGHT);

                return v;

            }
        };
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spLoaiKH.setAdapter(arrayAdapter);

        ArrayList<String> arrGioiTinh = new ArrayList<>();
        arrGioiTinh.add("Nam");
        arrGioiTinh.add("Nữ");
        arrGioiTinh.add("Khác");
        ArrayAdapter<String> arrayAdapterGT = new ArrayAdapter<String>(CreateBill.this, android.R.layout.simple_spinner_item, arrGioiTinh) {

            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);

                ((TextView) v).setGravity(Gravity.RIGHT);

                return v;

            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                View v = super.getDropDownView(position, convertView, parent);

                ((TextView) v).setGravity(Gravity.RIGHT);

                return v;

            }
        };
        arrayAdapterGT.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spGioiTinh.setAdapter(arrayAdapterGT);

        btnAddProduct.setOnClickListener(this);
        btnCreateBill.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnResetText.setOnClickListener(this);

        tvTongTienHang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (tvTongTienHang.getText() != null && !tvTongTienHang.getText().toString().isEmpty()) {
                    tienHang = Double.parseDouble(tvTongTienHang.getText().toString());
                }
            }
        });

        etPhieuGiamGia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etPhieuGiamGia.getText() != null && !etPhieuGiamGia.getText().toString().isEmpty()) {
                    double chietKhau = Double.parseDouble(tvChietKhau.getText().toString().trim());
                    double giamGia = Double.parseDouble(tvGiamGia.getText().toString().trim());
                    int khuyenMai = Integer.parseInt(etKhuyenMai.getText().toString().trim());
                    int phieu = Integer.parseInt(etPhieuGiamGia.getText().toString().trim());
                    tienPhaiTra = tienHang - tienHang * ((khuyenMai + phieu) * 1.0 / 100) - giamGia - chietKhau;
                    tvTongTien.setText(tienPhaiTra + "");
                }
            }
        });

        etTienMat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etTienMat.getText() != null && !etTienMat.getText().toString().trim().isEmpty()) {
                    tienMatKH = Double.parseDouble(etTienMat.getText().toString().trim());
                    if (tienMatKH + tienATMKH >= tienPhaiTra) {
                        tienThua = tienATMKH + tienMatKH - tienPhaiTra;
                    } else {
                        tienThua = 0;
                    }
                    tvTienThua.setText(tienThua + "");
                }
            }
        });

        etTienATM.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etTienATM.getText() != null && !etTienATM.getText().toString().trim().isEmpty()) {
                    tienATMKH = Double.parseDouble(etTienATM.getText().toString().trim());
                    if (tienMatKH + tienATMKH >= tienPhaiTra) {
                        tienThua = tienATMKH + tienMatKH - tienPhaiTra;
                    } else {
                        tienThua = 0;
                    }
                    tvTienThua.setText(tienThua + "");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == btnBack) {
            Intent intent = getIntent();
            setResult(ConstantHelper.FRAGMENT_CREATE_BILL_RESULT, intent);
            finish();
        } else if (v == btnAddProduct) {
            Intent intent = new Intent(CreateBill.this, ListProduct.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("list", arrayList);
            intent.putExtras(bundle);
            startActivityForResult(intent, ConstantHelper.REQUEST_LIST_PRODUCT);
        } else if (v == btnCreateBill) {
            createBill();
        } else if (v == btnResetText) {
            resetText();
        }
    }

    private boolean isCheckNull(String s) {
        if (s.isEmpty()) {
            return true;
        }
        return false;
    }


    private void createBill() {
        String khachHang = etKH.getText().toString().trim();
        String dienThoai = etDienThoai.getText().toString().trim();
        String diaChi = etDiaChi.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String khuyenMai = etKhuyenMai.getText().toString().trim();
        String phieuGiamGia = etPhieuGiamGia.getText().toString().trim();
        String tienMat = etTienMat.getText().toString().trim();
        String tienATM = etTienATM.getText().toString().trim();

        if (!isCheckNull(khachHang) && !isCheckNull(dienThoai) && !isCheckNull(diaChi)
                && !isCheckNull(email) && !isCheckNull(khuyenMai)
                && !isCheckNull(phieuGiamGia) && !isCheckNull(tienMat) && !isCheckNull(tienATM) && arrayList.size() > 0) {
            Customer customer = databaseHandler.getCustomer(khachHang, spGioiTinh.getSelectedItem().toString().trim(),
                    diaChi, dienThoai, email);

            if (customer == null) {
                customer = new Customer();
                customer.setName(khachHang);
                customer.setPhone(dienThoai);
                customer.setAddress(diaChi);
                customer.setEmail(email);
                customer.setGender(spGioiTinh.getSelectedItem().toString());
                long id = databaseHandler.addCustomer(customer);
                customer.setId((int) id);
            }
            Log.d("CUSTOMER_ID", customer.getId() + "");

            Bill bill = new Bill();
            bill.setKhuyenMai(Integer.parseInt(khuyenMai));
            bill.setLoaiKH(spLoaiKH.getSelectedItem().toString().trim());
            bill.setMaKH(customer.getId());
            bill.setMaNV(MainActivity.id);
            bill.setNgayLap(simpleDateFormat.format(new Date()));
            Log.d("CUSTOMER_DATE", bill.getNgayLap());
            bill.setPhieuGiamGia(Integer.parseInt(phieuGiamGia));
            bill.setTienATM(Double.parseDouble(tienATM));
            bill.setTienMat(Double.parseDouble(tienMat));
            if (tienATMKH + tienMatKH < tienPhaiTra) {
                tienNo = tienPhaiTra - tienATMKH - tienMatKH;
            } else {
                tienNo = 0;
            }
            bill.setTienNo(tienNo);

            long idB = databaseHandler.addBill(bill);

            if (idB > -1) {
                StoreBill storeBill = new StoreBill();
                storeBill.setMaHD((int) idB);
                storeBill.setLoaGiaoDich("Offline");
                if (tienMatKH + tienATMKH <= 0) {
                    storeBill.setTrangThai("Chưa thanh toán");
                } else if (tienMatKH + tienATMKH > 0 && tienNo > 0) {
                    storeBill.setTrangThai("Còn nợ");
                } else if (tienMatKH + tienATMKH > 0 && tienNo == 0) {
                    storeBill.setTrangThai("Đã thanh toán");
                }
                if (databaseHandler.kiemTraDonHang(idB + "")) {
                    databaseHandler.capNhapDonHang(storeBill);
                } else {
                    databaseHandler.themDonHang(storeBill);
                }

                long isCheck = 0;
                for (int i = 0; i < arrayList.size(); i++) {
                    Product product = arrayList.get(i);
                    StoreProduct storeProduct = new StoreProduct(product.getMaHang(), (int) idB,
                            product.getDonGiaBan(), product.getSoLuongBan());
                    isCheck = databaseHandler.addKeHang(storeProduct);
                }

                if (isCheck != -1) {
                    Toast.makeText(this, "Tạo hóa đơn thành công", Toast.LENGTH_SHORT).show();
                    resetText();
                }
            } else {
                Toast.makeText(this, "Tạo hóa đơn thất bại", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Bạn nhập chưa đủ thông tin", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetText() {
        tienNo = 0;
        tienHang = 0;
        tienPhaiTra = 0;
        tienThua = 0;
        tienMatKH = 0;
        tienATMKH = 0;
        tienChietKhau = 0;
        etKH.setText("");
        etDiaChi.setText("");
        etDienThoai.setText("");
        etDiaChi.setText("");
        etEmail.setText("");
        tvChietKhau.setText("");
        etKhuyenMai.setText("");
        etPhieuGiamGia.setText("");
        etTienATM.setText("");
        etTienMat.setText("");
        tvTienThua.setText("");
        tvTongTienHang.setText("");
        tvTongTien.setText("");
        spGioiTinh.setSelection(0);
        spLoaiKH.setSelection(0);
        arrayList.clear();
        productArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ConstantHelper.REQUEST_LIST_PRODUCT && data != null) {
            if (resultCode == ConstantHelper.RESULT_LIST_PRODUCT) {
                arrayList = data.getExtras().getParcelableArrayList("list_product");
                Log.e("SIZE", "ĐÃ UPDATE + " + arrayList.size());
                productArrayAdapter = new MyProductAdapter(CreateBill.this, arrayList);
                listView.setAdapter(productArrayAdapter);
                tienChietKhau = 0;
                tienGiamGia = 0;
                if (arrayList.size() > 0) {
                    double tien = 0;
                    for (int i = 0; i < arrayList.size(); i++) {
                        tien += (arrayList.get(i).getDonGiaBan() * arrayList.get(i).getSoLuongBan());
                        tienChietKhau += (arrayList.get(i).getDonGiaBan() * arrayList.get(i).getChietKhau() / 100);
                        tienGiamGia += (arrayList.get(i).getDonGiaBan() * arrayList.get(i).getGiamGia() / 100);
                    }

                    tvTongTienHang.setText(tien + "");
                    tvChietKhau.setText(tienChietKhau + "");
                    tvGiamGia.setText(tienGiamGia + "");
                }
            }
        }
    }
}
