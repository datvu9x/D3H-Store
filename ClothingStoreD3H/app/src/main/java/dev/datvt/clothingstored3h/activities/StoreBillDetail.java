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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dev.datvt.clothingstored3h.R;
import dev.datvt.clothingstored3h.adapters.MyProductAdapter;
import dev.datvt.clothingstored3h.models.Bill;
import dev.datvt.clothingstored3h.models.Customer;
import dev.datvt.clothingstored3h.models.Product;
import dev.datvt.clothingstored3h.models.StoreBill;
import dev.datvt.clothingstored3h.models.StoreProduct;
import dev.datvt.clothingstored3h.utils.ConstantHelper;
import dev.datvt.clothingstored3h.utils.DatabaseHandler;
import dev.datvt.clothingstored3h.utils.NumberTextWatcherForThousand;

/**
 * Created by DatVIT on 12/5/2016.
 */
public class StoreBillDetail extends RootActivity implements View.OnClickListener {

    private StoreBill storeBill;
    private Customer customer;
    private Bill bill;
    private int maKH;
    private int maHD;

    private ImageView btnBack;

    private Spinner spLoaiKH, spGioiTinh, spTrangThai;
    private TextView tvTongTienHang, tvTongTien, tvTienThua, btnOK, tvChietKhau, tvGiamGia, tvMaPhieu, tvMaHD, tvTienNo;
    private EditText etKH, etDienThoai, etDiaChi, etEmail, etKhuyenMai, etPhieuGiamGia, etTienATM, etTienMat;
    private ListView listView;
    private List<Product> arrayList;
    private MyProductAdapter productArrayAdapter;

    private DatabaseHandler databaseHandler;
    private double tienNo = 0, tienHang = 0, tienPhaiTra = 0, tienThua = 0, tienMatKH = 0, tienATMKH = 0, tienChietKhau = 0, tienGiamGia = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_info_bill_offline);

        databaseHandler = new DatabaseHandler(this);

        spLoaiKH = (Spinner) findViewById(R.id.spLoaiKH);
        spGioiTinh = (Spinner) findViewById(R.id.spGioiTinh);
        spTrangThai = (Spinner) findViewById(R.id.spTrangThai);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        tvTongTien = (TextView) findViewById(R.id.tvTongTien);
        tvTongTienHang = (TextView) findViewById(R.id.tvTongTienHang);
        tvTienThua = (TextView) findViewById(R.id.tvTienThua);
        btnOK = (TextView) findViewById(R.id.btnOkDetail);
        tvChietKhau = (TextView) findViewById(R.id.tvChietKhau);
        tvGiamGia = (TextView) findViewById(R.id.tvGiamGia);
        tvMaPhieu = (TextView) findViewById(R.id.tvMaPhieu);
        tvMaHD = (TextView) findViewById(R.id.tvMaHD);
        tvTienNo = (TextView) findViewById(R.id.tvTienNo);

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
        productArrayAdapter = new MyProductAdapter(this, arrayList);
        listView.setAdapter(productArrayAdapter);

        ArrayList<String> arrLoaiKH = new ArrayList<>();
        arrLoaiKH.add("Khách buôn");
        arrLoaiKH.add("Khách lẻ");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrLoaiKH) {

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
        ArrayAdapter<String> arrayAdapterGT = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrGioiTinh) {

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

        ArrayList<String> arrTrangThai = new ArrayList<>();
        arrTrangThai.add("Đã thanh toán");
        arrTrangThai.add("Chưa thanh toán");
        arrTrangThai.add("Còn nợ");
        ArrayAdapter<String> arrayAdapterTT = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrTrangThai) {

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
        arrayAdapterTT.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spTrangThai.setAdapter(arrayAdapterTT);

        btnOK.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        getData();

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
                    if (etTienATM.getText() != null && !etTienATM.getText().toString().trim().isEmpty()) {
                        tienATMKH = Double.parseDouble(NumberTextWatcherForThousand.trimCommaOfString(etTienATM.getText().toString().trim()));
                    } else {
                        tienATMKH = 0;
                    }
                    if (tvTongTien.getText() != null && !tvTongTien.getText().toString().trim().isEmpty()) {
                        tienPhaiTra = Double.parseDouble(NumberTextWatcherForThousand.trimCommaOfString(tvTongTien.getText().toString().trim()));
                    } else {
                        tienPhaiTra = 0;
                    }
                    tienMatKH = Double.parseDouble(NumberTextWatcherForThousand.trimCommaOfString(etTienMat.getText().toString().trim()));
                    Log.e("TIEN", "Tiền mặt: " + tienMatKH);
                    Log.e("TIEN", "Tiền ATM: " + tienATMKH);
                    Log.e("TIEN", "Tiền phải trả: " + tienPhaiTra);
                    if (tienMatKH + tienATMKH >= tienPhaiTra) {
                        tienThua = tienATMKH + tienMatKH - tienPhaiTra;
                        tienNo = 0;
                        spTrangThai.setSelection(0);
                    } else {
                        tienThua = 0;
                        tienNo = tienPhaiTra - (tienMatKH + tienATMKH);
                        spTrangThai.setSelection(2);
                    }
                    tvTienNo.setText(NumberTextWatcherForThousand.getDecimalFormattedString(tienNo + ""));
                    tvTienThua.setText(NumberTextWatcherForThousand.getDecimalFormattedString(tienThua + ""));
                    if (tienMatKH + tienATMKH <= 0) {
                        spTrangThai.setSelection(1);
                    }

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
                    if (etTienMat.getText() != null && !etTienMat.getText().toString().trim().isEmpty()) {
                        tienMatKH = Double.parseDouble(NumberTextWatcherForThousand.trimCommaOfString(etTienMat.getText().toString().trim()));
                    } else {
                        tienMatKH = 0;
                    }
                    if (tvTongTien.getText() != null && !tvTongTien.getText().toString().trim().isEmpty()) {
                        tienPhaiTra = Double.parseDouble(NumberTextWatcherForThousand.trimCommaOfString(tvTongTien.getText().toString().trim()));
                    } else {
                        tienPhaiTra = 0;
                    }
                    tienATMKH = Double.parseDouble(NumberTextWatcherForThousand.trimCommaOfString(etTienATM.getText().toString().trim()));
                    if (tienMatKH + tienATMKH >= tienPhaiTra) {
                        tienThua = tienATMKH + tienMatKH - tienPhaiTra;
                        tienNo = 0;
                        spTrangThai.setSelection(0);
                    } else {
                        tienThua = 0;
                        tienNo = tienPhaiTra - (tienMatKH + tienATMKH);
                        spTrangThai.setSelection(2);
                    }
                    tvTienNo.setText(tienNo + "");
                    tvTienThua.setText(tienThua + "");
                    if (tienMatKH + tienATMKH <= 0) {
                        spTrangThai.setSelection(1);
                    }
                }
            }
        });


        etTienATM.addTextChangedListener(new NumberTextWatcherForThousand(etTienATM));
        etTienMat.addTextChangedListener(new NumberTextWatcherForThousand(etTienMat));
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            storeBill = (StoreBill) bundle.getSerializable("bill_detail");
            if (storeBill != null) {
                tvMaHD.setText(storeBill.getMaHD() + "");
                maHD = storeBill.getMaHD();
                tvMaPhieu.setText(storeBill.getMaPhieu() + "");
                Log.e("MAIN", "TRạng thái: " + storeBill.getTrangThai());
                if (storeBill.getTrangThai().equals("Đã thanh toán")) {
                    spTrangThai.setSelection(0);
                    spTrangThai.setEnabled(false);
                    etTienATM.setEnabled(false);
                    etPhieuGiamGia.setEnabled(false);
                    etTienMat.setEnabled(false);
                    etKhuyenMai.setEnabled(false);
                    etDiaChi.setEnabled(false);
                    etDienThoai.setEnabled(false);
                    etEmail.setEnabled(false);
                    etKH.setEnabled(false);
                    spGioiTinh.setEnabled(false);
                    spLoaiKH.setEnabled(false);
                    tvMaPhieu.setEnabled(false);
                    tvMaHD.setEnabled(false);
                    tvChietKhau.setEnabled(false);
                    tvGiamGia.setEnabled(false);
                } else if (storeBill.getTrangThai().equals("Chưa thanh toán")) {
                    spTrangThai.setEnabled(true);
                    spTrangThai.setSelection(1);
                } else if (storeBill.getTrangThai().equals("Còn nợ")) {
                    spTrangThai.setEnabled(true);
                    spTrangThai.setSelection(2);
                }

                bill = databaseHandler.getBillWithID(storeBill.getMaHD() + "");

                if (bill != null) {
                    customer = databaseHandler.getCustomerWithID(bill.getMaKH() + "");
                    if (customer != null) {
                        maKH = customer.getId();
                        if (bill.getLoaiKH().equals("Khách buôn")) {
                            spLoaiKH.setSelection(0);
                        } else {
                            spLoaiKH.setSelection(1);
                        }
                        etKH.setText(customer.getName());
                        if (customer.getGender().equals("Nam")) {
                            spGioiTinh.setSelection(0);
                        } else if (customer.getGender().equals("Nữ")) {
                            spGioiTinh.setSelection(1);
                        } else {
                            spGioiTinh.setSelection(2);
                        }
                        etDienThoai.setText(customer.getPhone());
                        etDiaChi.setText(customer.getAddress());
                        etEmail.setText(customer.getEmail());

                        arrayList = databaseHandler.getListProductWithBillID(storeBill.getMaHD() + "");

                        if (arrayList != null && arrayList.size() > 0) {
                            productArrayAdapter = new MyProductAdapter(this, arrayList);
                            listView.setAdapter(productArrayAdapter);
                            tienChietKhau = 0;
                            tienGiamGia = 0;
                            double tien = 0;
                            for (int i = 0; i < arrayList.size(); i++) {
                                tien += (arrayList.get(i).getDonGiaBan() * arrayList.get(i).getSoLuongBan());
                                tienChietKhau += (arrayList.get(i).getDonGiaBan() * arrayList.get(i).getChietKhau() / 100);
                                tienGiamGia += (arrayList.get(i).getDonGiaBan() * arrayList.get(i).getGiamGia() / 100);
                            }

                            tvTongTienHang.setText(NumberTextWatcherForThousand.getDecimalFormattedString(tien + ""));
                            tvChietKhau.setText(NumberTextWatcherForThousand.getDecimalFormattedString(tienChietKhau + ""));
                            tvGiamGia.setText(NumberTextWatcherForThousand.getDecimalFormattedString(tienGiamGia + ""));
                        }
                    }

                    tienMatKH = bill.getTienMat();
                    tienATMKH = bill.getTienATM();
                    tienNo = bill.getTienNo();

                    etKhuyenMai.setText(bill.getKhuyenMai() + "");
                    etPhieuGiamGia.setText(bill.getPhieuGiamGia() + "");
                    etTienATM.setText(NumberTextWatcherForThousand.getDecimalFormattedString(bill.getTienATM() +""));
                    etTienMat.setText(NumberTextWatcherForThousand.getDecimalFormattedString(bill.getTienMat() + ""));
                    tvTienNo.setText(NumberTextWatcherForThousand.getDecimalFormattedString(bill.getTienNo() + ""));

                    tienHang = Double.parseDouble(NumberTextWatcherForThousand.trimCommaOfString(tvTongTienHang.getText().toString()));
                    double chietKhau = Double.parseDouble(NumberTextWatcherForThousand.trimCommaOfString(tvChietKhau.getText().toString().trim()));
                    double giamGia = Double.parseDouble(NumberTextWatcherForThousand.trimCommaOfString(tvGiamGia.getText().toString().trim()));
                    int khuyenMai = Integer.parseInt(NumberTextWatcherForThousand.trimCommaOfString(etKhuyenMai.getText().toString().trim()));
                    int phieu = Integer.parseInt(NumberTextWatcherForThousand.trimCommaOfString(etPhieuGiamGia.getText().toString().trim()));
                    tienPhaiTra = tienHang - tienHang * (khuyenMai + phieu) * 1.0 / 100 - chietKhau - giamGia;
                    tvTongTien.setText(NumberTextWatcherForThousand.getDecimalFormattedString(tienPhaiTra + ""));

                    if (tienMatKH + tienATMKH >= tienPhaiTra) {
                        tienThua = tienATMKH + tienMatKH - tienPhaiTra;
                    } else {
                        tienThua = 0;
                    }
                    tvTienThua.setText(NumberTextWatcherForThousand.getDecimalFormattedString(tienThua + ""));

                }
            }
        }
    }


    @Override
    public void onClick(View v) {
        if (v == btnBack) {
            finish();
        } else if (v == btnOK) {
            updateBill();
            Intent intent = getIntent();
            setResult(ConstantHelper.RESULT_DETAIL_BILL, intent);
            finish();
        }
    }

    private boolean isCheckNull(String s) {
        if (s.isEmpty()) {
            return true;
        }
        return false;
    }

    private void updateBill() {
        String khachHang = etKH.getText().toString().trim();
        String dienThoai = etDienThoai.getText().toString().trim();
        String diaChi = etDiaChi.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String khuyenMai = etKhuyenMai.getText().toString().trim();
        String phieuGiamGia = etPhieuGiamGia.getText().toString().trim();
        String tienMat = etTienMat.getText().toString().trim();
        String tienATM = etTienATM.getText().toString().trim();
        String _tienNo = tvTienNo.getText().toString().trim();

        if (!isCheckNull(khachHang) && !isCheckNull(dienThoai) && !isCheckNull(diaChi)
                && !isCheckNull(email) && !isCheckNull(khuyenMai)
                && !isCheckNull(phieuGiamGia) && !isCheckNull(tienMat) && !isCheckNull(tienATM) && arrayList.size() > 0) {

            Customer customer = new Customer();
            customer.setId(maKH);
            customer.setName(khachHang);
            customer.setPhone(dienThoai);
            customer.setAddress(diaChi);
            customer.setEmail(email);
            customer.setGender(spGioiTinh.getSelectedItem().toString());
            databaseHandler.capNhatKhachHang(customer);

            Log.d("CUSTOMER_ID", customer.getId() + "");

            Bill bill = new Bill();
            bill.setMaHD(maHD);
            bill.setKhuyenMai(Integer.parseInt(NumberTextWatcherForThousand.trimCommaOfString(khuyenMai)));
            bill.setLoaiKH(spLoaiKH.getSelectedItem().toString().trim());
            bill.setPhieuGiamGia(Integer.parseInt(NumberTextWatcherForThousand.trimCommaOfString(phieuGiamGia)));
            bill.setTienATM(Double.parseDouble(NumberTextWatcherForThousand.trimCommaOfString(tienATM)));
            bill.setTienMat(Double.parseDouble(NumberTextWatcherForThousand.trimCommaOfString(tienMat)));
            bill.setTienNo(Double.parseDouble(NumberTextWatcherForThousand.trimCommaOfString(_tienNo)));

            databaseHandler.capNhatHoaDon(bill);

            StoreBill storeBill = new StoreBill();
            storeBill.setMaHD(maHD);
            storeBill.setTrangThai(spTrangThai.getSelectedItem().toString());
            databaseHandler.capNhapDonHang(storeBill);

        } else {
            Toast.makeText(this, "Bạn nhập chưa đủ thông tin", Toast.LENGTH_SHORT).show();
        }
    }

}
