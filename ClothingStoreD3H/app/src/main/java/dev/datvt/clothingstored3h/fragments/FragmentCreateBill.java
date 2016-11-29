package dev.datvt.clothingstored3h.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.BaseInputConnection;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dev.datvt.clothingstored3h.R;
import dev.datvt.clothingstored3h.activities.ListProduct;
import dev.datvt.clothingstored3h.models.Bill;
import dev.datvt.clothingstored3h.models.Customer;
import dev.datvt.clothingstored3h.models.Product;
import dev.datvt.clothingstored3h.utils.ConstantHelper;
import dev.datvt.clothingstored3h.utils.DatabaseHandler;


/**
 * Created by DatVIT on 10/16/2016.
 */

public class FragmentCreateBill extends Fragment implements View.OnClickListener {

    private View viewFragment;

    private Spinner spLoaiKH, spGioiTinh;
    private ImageView btnAddProduct;
    private TextView tvTongTienHang, tvTongTien, tvTienThua, btnCreateBill, btnResetText;
    private EditText etKH, etDienThoai, etDiaChi, etEmail, etVAT, etKhuyenMai, etPhieuGiamGia, etTienATM, etTienMat;
    private ListView listView;
    private ArrayList<Product> arrayList;
    private ArrayAdapter<Product> arrayAdapter;

    private DatabaseHandler databaseHandler;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private double tienNo = 0, tienHang = 0, tienPhaiTra = 0, tienThua = 0, tienMatKH = 0, tienATMKH = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewFragment = inflater.inflate(R.layout.fragment_bill, container, false);

        databaseHandler = new DatabaseHandler(getActivity());

        spLoaiKH = (Spinner) viewFragment.findViewById(R.id.spLoaiKH);
        spGioiTinh = (Spinner) viewFragment.findViewById(R.id.spGioiTinh);
        btnAddProduct = (ImageView) viewFragment.findViewById(R.id.btnAddProduct);

        tvTongTien = (TextView) viewFragment.findViewById(R.id.tvTongTien);
        tvTongTienHang = (TextView) viewFragment.findViewById(R.id.tvTongTienHang);
        tvTienThua = (TextView) viewFragment.findViewById(R.id.tvTienThua);
        btnCreateBill = (TextView) viewFragment.findViewById(R.id.btnCreateBill);
        btnResetText = (TextView) viewFragment.findViewById(R.id.btnResetText);

        etKH = (EditText) viewFragment.findViewById(R.id.etKhachHang);
        etDiaChi = (EditText) viewFragment.findViewById(R.id.etDiaChi);
        etDienThoai = (EditText) viewFragment.findViewById(R.id.etDienThoai);
        etEmail = (EditText) viewFragment.findViewById(R.id.etEmail);
        etVAT = (EditText) viewFragment.findViewById(R.id.etVAT);
        etKhuyenMai = (EditText) viewFragment.findViewById(R.id.etKhuyenMai);
        etPhieuGiamGia = (EditText) viewFragment.findViewById(R.id.etPhieuGiamGia);
        etTienATM = (EditText) viewFragment.findViewById(R.id.etTienATM);
        etTienMat = (EditText) viewFragment.findViewById(R.id.etTienMat);

        listView = (ListView) viewFragment.findViewById(R.id.listProductInBill);
        arrayList  = new ArrayList<>();
        arrayAdapter  = new ArrayAdapter<Product>(getActivity(), android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

        ArrayList<String> arrLoaiKH = new ArrayList<>();
        arrLoaiKH.add("Bán buôn");
        arrLoaiKH.add("Bán lẻ");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arrLoaiKH);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spLoaiKH.setAdapter(arrayAdapter);

        ArrayList<String> arrGioiTinh = new ArrayList<>();
        arrGioiTinh.add("Nam");
        arrGioiTinh.add("Nữ");
        arrGioiTinh.add("Khác");
        ArrayAdapter<String> arrayAdapterGT = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arrGioiTinh);
        arrayAdapterGT.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spGioiTinh.setAdapter(arrayAdapterGT);

        btnAddProduct.setOnClickListener(this);
        btnCreateBill.setOnClickListener(this);
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
                if (s != null && !s.toString().isEmpty()) {
                    tienHang = Double.parseDouble(s.toString());
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
                if (s != null && !s.toString().isEmpty()) {
                    double vat = Double.parseDouble(etVAT.getText().toString().trim());
                    double khuyenMai = Double.parseDouble(etKhuyenMai.getText().toString().trim());
                    double giamGia = Double.parseDouble(s.toString().trim());
                    tienPhaiTra = tienHang + tienHang * (vat + khuyenMai + giamGia) / 100;
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
                if (s != null && !s.toString().trim().isEmpty()) {
                    tienMatKH = Double.parseDouble(s.toString().trim());
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
                if (s != null && !s.toString().trim().isEmpty()) {
                    tienATMKH = Double.parseDouble(s.toString().trim());
                    if (tienMatKH + tienATMKH >= tienPhaiTra) {
                        tienThua = tienATMKH + tienMatKH - tienPhaiTra;
                    } else {
                        tienThua = 0;
                    }
                    tvTienThua.setText(tienThua + "");
                }
            }
        });

        return viewFragment;
    }

    @Override
    public void onClick(View v) {
        if (v == btnAddProduct) {
            Intent intent = new Intent(getActivity(), ListProduct.class);
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
        String vat = etVAT.getText().toString().trim();
        String khuyenMai = etKhuyenMai.getText().toString().trim();
        String phieuGiamGia = etPhieuGiamGia.getText().toString().trim();
        String tienMat = etTienMat.getText().toString().trim();
        String tienATM = etTienATM.getText().toString().trim();

        if (!isCheckNull(khachHang) && !isCheckNull(dienThoai) && !isCheckNull(diaChi)
                && !isCheckNull(email) && !isCheckNull(vat) && !isCheckNull(khuyenMai)
                && !isCheckNull(phieuGiamGia) && !isCheckNull(tienMat) && !isCheckNull(tienATM) && arrayList.size() > 0) {
            Customer customer = new Customer();

            if (databaseHandler.getCustomer(khachHang, spGioiTinh.getSelectedItem().toString().trim(),
                    diaChi, dienThoai, email) == null) {
                customer.setName(khachHang);
                customer.setPhone(dienThoai);
                customer.setAddress(diaChi);
                customer.setEmail(email);
                customer.setGender(spGioiTinh.getSelectedItem().toString());
                databaseHandler.addCustomer(customer);
            }
            customer = databaseHandler.getCustomer(khachHang, spGioiTinh.getSelectedItem().toString().trim(),
                    diaChi, dienThoai, email);
            Log.d("CUSTOMER_ID", customer.getId());

            Bill bill = new Bill();
            bill.setKhuyenMai(khuyenMai);
            bill.setLoaiKH(spLoaiKH.getSelectedItem().toString().trim());
            bill.setMaKH(customer.getId());
            bill.setMaNV("NV01");
            bill.setNgayLap(simpleDateFormat.format(new Date()));
            Log.d("CUSTOMER_DATE", bill.getNgayLap());
            bill.setPhieuGiamGia(phieuGiamGia);
            bill.setVat(vat);
            bill.setTienATM(tienATM);
            bill.setTienMat(tienMat);
            if (tienATMKH + tienMatKH < tienPhaiTra) {
                tienNo = tienPhaiTra - tienATMKH - tienMatKH;
            } else {
                tienNo = 0;
            }
            bill.setSoNo(tienNo + "");

            databaseHandler.addBill(bill);

            String id_bill = databaseHandler.getBillID(customer.getId(),"NV01", spLoaiKH.getSelectedItem().toString().trim(),
                    bill.getNgayLap());

            if (id_bill != null) {

            }
        }
    }

    private void resetText() {
        etKH.setText("");
        etDiaChi.setText("");
        etDienThoai.setText("");
        etDiaChi.setText("");
        etEmail.setText("");
        etVAT.setText("");
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
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==ConstantHelper.REQUEST_LIST_PRODUCT) {
            if (resultCode == ConstantHelper.RESULT_LIST_PRODUCT){
                
            }
        }
    }
}
