package dev.datvt.clothingstored3h.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import dev.datvt.clothingstored3h.R;
import dev.datvt.clothingstored3h.models.Customer;
import dev.datvt.clothingstored3h.utils.DatabaseHandler;

/**
 * Created by DatVIT on 10/16/2016.
 */

public class CustomerDetail extends RootActivity {

    private int id;
    private TextView ma;
    private EditText ten, diaChi, dienThoai, email;
    private Customer customer;
    private LinearLayout btnOK;
    private Spinner spGioiTinh;
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.detail_info_customer);

        databaseHandler = new DatabaseHandler(this);

        ten = (EditText) findViewById(R.id.tvName);
        diaChi = (EditText) findViewById(R.id.tvAddress);
        email = (EditText) findViewById(R.id.tvEmail);
        dienThoai = (EditText) findViewById(R.id.tvPhone);
        ma = (TextView) findViewById(R.id.tvMa);
        btnOK = (LinearLayout) findViewById(R.id.btnOK);
        spGioiTinh = (Spinner) findViewById(R.id.spGioiTinh);

        ArrayList<String> arrGioiTinh = new ArrayList<>();
        arrGioiTinh.add("Nam");
        arrGioiTinh.add("Nữ");
        arrGioiTinh.add("Khác");
        ArrayAdapter<String> arrayAdapterGT = new ArrayAdapter<String>(CustomerDetail.this, android.R.layout.simple_spinner_item, arrGioiTinh);
        arrayAdapterGT.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spGioiTinh.setAdapter(arrayAdapterGT);

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getIntExtra("id", 0);
            customer = databaseHandler.getCusomer(id + "");
            if (customer != null) {
                ten.setText(customer.getName());
                diaChi.setText(customer.getAddress());
                email.setText(customer.getEmail());
                dienThoai.setText(customer.getPhone());
                ma.setText(customer.getId() + "");
                if (customer.getGender().equals("Nam")) {
                    spGioiTinh.setSelection(0);
                } else if (customer.getGender().equals("Nữ")) {
                    spGioiTinh.setSelection(1);
                } else {
                    spGioiTinh.setSelection(2);
                }
            }
        }

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _ten = ten.getText().toString();
                String _diachi = diaChi.getText().toString();
                String _email = email.getText().toString();
                String _dienthoai = dienThoai.getText().toString();
                String _gioitinh = spGioiTinh.getSelectedItem().toString();

                if (!_diachi.isEmpty() && !_email.isEmpty() && !_ten.isEmpty() && !_dienthoai.isEmpty() && !_gioitinh.isEmpty()) {
                    Customer customer = new Customer();
                    customer.setName(_ten);
                    customer.setGender(_gioitinh);
                    customer.setAddress(_diachi);
                    customer.setPhone(_dienthoai);
                    customer.setEmail(_email);
                    customer.setId(id);

                    databaseHandler.capNhatKhachHang(customer);

                    Toast.makeText(CustomerDetail.this, "Sửa thông tin khách hàng thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(CustomerDetail.this, "Bạn chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
