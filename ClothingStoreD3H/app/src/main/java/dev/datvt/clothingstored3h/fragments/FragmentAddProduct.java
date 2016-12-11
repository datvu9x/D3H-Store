package dev.datvt.clothingstored3h.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import dev.datvt.clothingstored3h.R;
import dev.datvt.clothingstored3h.models.Product;
import dev.datvt.clothingstored3h.models.Properties;
import dev.datvt.clothingstored3h.utils.DatabaseHandler;
import dev.datvt.clothingstored3h.utils.NumberTextWatcherForThousand;

/**
 * Created by DatVIT on 10/16/2016.
 */

public class FragmentAddProduct extends Fragment implements View.OnClickListener {

    private View viewFragment;
    private Spinner spMua, spDoiTuong;
    private TextView btnAdd, btnRefesh;
    private EditText etMaHang, etTenHang, etLoaiHangHoa, etKichThuoc,
            etMauSac, etDonGia, etNSX, etSoLuong, etGiamGia, etChietKhau;
    private DatabaseHandler databaseHandler;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewFragment = inflater.inflate(R.layout.fragment_add_product, container, false);
        return viewFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spMua = (Spinner) view.findViewById(R.id.spMua);
        spDoiTuong = (Spinner) view.findViewById(R.id.spDoiTuong);

        etMaHang = (EditText) view.findViewById(R.id.etMaHang);
        etTenHang = (EditText) view.findViewById(R.id.etTenHang);
        etLoaiHangHoa = (EditText) view.findViewById(R.id.etLoaiHangHoa);
        etKichThuoc = (EditText) view.findViewById(R.id.etKichThuoc);
        etMauSac = (EditText) view.findViewById(R.id.etMauSac);
        etDonGia = (EditText) view.findViewById(R.id.etDonGia);
        etNSX = (EditText) view.findViewById(R.id.etNSX);
        etSoLuong = (EditText) view.findViewById(R.id.etSoLuong);
        etGiamGia = (EditText) view.findViewById(R.id.etGiamGia);
        etChietKhau = (EditText) view.findViewById(R.id.etChietKhau);

        btnAdd = (TextView) view.findViewById(R.id.btnAdd);
        btnRefesh = (TextView) view.findViewById(R.id.btnRefresh);

        btnAdd.setOnClickListener(this);
        btnRefesh.setOnClickListener(this);

        databaseHandler = new DatabaseHandler(getActivity());

        ArrayList<String> arrMua = new ArrayList<>();
        arrMua.add("Xuân");
        arrMua.add("Hè");
        arrMua.add("Thu");
        arrMua.add("Đông");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arrMua){

            public View getView(int position, View convertView,ViewGroup parent) {

                View v = super.getView(position, convertView, parent);

                ((TextView) v).setGravity(Gravity.RIGHT);

                return v;

            }

            public View getDropDownView(int position, View convertView,ViewGroup parent) {

                View v = super.getDropDownView(position, convertView,parent);

                ((TextView) v).setGravity(Gravity.RIGHT);

                return v;

            }
        };
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spMua.setAdapter(arrayAdapter);

        ArrayList<String> arrDoiTuong = new ArrayList<>();
        arrDoiTuong.add("Nam");
        arrDoiTuong.add("Nữ");
        arrDoiTuong.add("Trẻ em");
        arrDoiTuong.add("Trung niên");
        arrDoiTuong.add("Người già");
        arrDoiTuong.add("Bà bầu");
        arrDoiTuong.add("Khác");
        ArrayAdapter<String> arrayAdapterDoiTuong = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arrDoiTuong){

            public View getView(int position, View convertView,ViewGroup parent) {

                View v = super.getView(position, convertView, parent);

                ((TextView) v).setGravity(Gravity.RIGHT);

                return v;

            }

            public View getDropDownView(int position, View convertView,ViewGroup parent) {

                View v = super.getDropDownView(position, convertView,parent);

                ((TextView) v).setGravity(Gravity.RIGHT);

                return v;

            }
        };
        arrayAdapterDoiTuong.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spDoiTuong.setAdapter(arrayAdapterDoiTuong);

        etDonGia.addTextChangedListener(new NumberTextWatcherForThousand(etDonGia));
        etSoLuong.addTextChangedListener(new NumberTextWatcherForThousand(etSoLuong));
        etGiamGia.addTextChangedListener(new NumberTextWatcherForThousand(etGiamGia));
        etChietKhau.addTextChangedListener(new NumberTextWatcherForThousand(etChietKhau));
    }

    private void addProduct() {
        String maHang = etMaHang.getText().toString().trim();
        String tenHang = etTenHang.getText().toString().trim();
        String loaiHangHoa = etLoaiHangHoa.getText().toString().trim();
        String kichThuoc = etKichThuoc.getText().toString().trim();
        String mauSac = etMauSac.getText().toString().trim();
        String donGia = etDonGia.getText().toString().trim();
        String nsx = etNSX.getText().toString().trim();
        String soLuong = etSoLuong.getText().toString().trim();
        String giamGia = etGiamGia.getText().toString().trim();
        String chietKhau = etChietKhau.getText().toString().trim();

        if (!isCheckNull(maHang) && !isCheckNull(tenHang) && !isCheckNull(loaiHangHoa) && !isCheckNull(kichThuoc)
                && !isCheckNull(mauSac) && !isCheckNull(donGia) && !isCheckNull(nsx) && !isCheckNull(soLuong)
                && !isCheckNull(giamGia) && !isCheckNull(chietKhau)) {
            Properties properties = new Properties(loaiHangHoa, mauSac, spDoiTuong.getSelectedItem().toString(),
                    nsx, spMua.getSelectedItem().toString(), Integer.parseInt(kichThuoc));
            if (databaseHandler.isCheckProperties(loaiHangHoa, Integer.parseInt(kichThuoc), mauSac, spDoiTuong.getSelectedItem().toString(),
                    spMua.getSelectedItem().toString(), nsx) == -1) {
                databaseHandler.addThuocTinh(properties);

            }
            properties.setMa(databaseHandler.isCheckProperties(loaiHangHoa, Integer.parseInt(kichThuoc), mauSac, spDoiTuong.getSelectedItem().toString(),
                    spMua.getSelectedItem().toString(), nsx));
            Log.d("ADD_PRODUCT", "MA_THUOC_TINH: "  + properties.getMa());
            Product product = new Product(maHang, tenHang, Double.parseDouble(NumberTextWatcherForThousand.trimCommaOfString(donGia)),
                    Integer.parseInt(NumberTextWatcherForThousand.trimCommaOfString(giamGia)),
                    Integer.parseInt(NumberTextWatcherForThousand.trimCommaOfString(chietKhau)), Integer.parseInt(NumberTextWatcherForThousand.trimCommaOfString(soLuong)), properties);
            Log.d("ADD_PRODUCT", !databaseHandler.isCheckProduct(maHang) + "");
            if (!databaseHandler.isCheckProduct(maHang)) {
                databaseHandler.addProduct(product);
                Toast.makeText(getActivity(), "Thêm hàng hóa thành công", Toast.LENGTH_SHORT).show();
                refeshText();
            } else {
                Toast.makeText(getActivity(), "Mã hàng hóa đã tồn tại", Toast.LENGTH_SHORT).show();
                etMaHang.setFocusable(true);
            }
        } else {
            Toast.makeText(getActivity(), "Bạn chưa nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isCheckNull(String s) {
        if (s.isEmpty()) {
            return true;
        }
        return false;
    }

    private void refeshText() {
        etMaHang.setText("");
        etTenHang.setText("");
        etLoaiHangHoa.setText("");
        etKichThuoc.setText("");
        etMauSac.setText("");
        etDonGia.setText("");
        etNSX.setText("");
        etSoLuong.setText("");
        etGiamGia.setText("");
        etChietKhau.setText("");
        spMua.setSelection(0);
        spDoiTuong.setSelection(0);
    }

    @Override
    public void onClick(View v) {
        if (v == btnAdd) {
            addProduct();
        }
        if (v == btnRefesh) {
            refeshText();
        }
    }
}
