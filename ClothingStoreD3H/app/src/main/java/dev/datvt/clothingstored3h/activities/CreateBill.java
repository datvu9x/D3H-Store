package dev.datvt.clothingstored3h.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;

import dev.datvt.clothingstored3h.R;
import dev.datvt.clothingstored3h.utils.ConstantHelper;

/**
 * Created by DatVIT on 10/27/2016.
 */

public class CreateBill extends RootActivity implements View.OnClickListener {

    private ImageView btnBack, btnAddProduct;

    private Spinner spLoaiKH, spGioiTinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_ticket);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBlack));
        }

        spLoaiKH = (Spinner) findViewById(R.id.spLoaiKH);
        spGioiTinh = (Spinner) findViewById(R.id.spGioiTinh);

        ArrayList<String> arrLoaiKH = new ArrayList<>();
        arrLoaiKH.add("Bán buôn");
        arrLoaiKH.add("Bán lẻ");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrLoaiKH);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spLoaiKH.setAdapter(arrayAdapter);

        ArrayList<String> arrGioiTinh = new ArrayList<>();
        arrGioiTinh.add("Nam");
        arrGioiTinh.add("Nữ");
        arrGioiTinh.add("Khác");
        ArrayAdapter<String> arrayAdapterGT = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrGioiTinh);
        arrayAdapterGT.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spGioiTinh.setAdapter(arrayAdapterGT);

        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnAddProduct = (ImageView) findViewById(R.id.btnAddProduct);
        btnBack.setOnClickListener(this);
        btnAddProduct.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnBack) {
            finish();
        }

        if (v == btnAddProduct) {
            Intent intent = new Intent(CreateBill.this, ListProduct.class);
            startActivityForResult(intent, ConstantHelper.REQUEST_LIST_PRODUCT);
        }
    }
}
