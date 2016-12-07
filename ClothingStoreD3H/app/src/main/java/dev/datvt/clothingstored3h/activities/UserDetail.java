package dev.datvt.clothingstored3h.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import dev.datvt.clothingstored3h.R;
import dev.datvt.clothingstored3h.models.Employee;
import dev.datvt.clothingstored3h.utils.DatabaseHandler;

/**
 * Created by DatVIT on 10/16/2016.
 */

public class UserDetail extends RootActivity implements View.OnClickListener {

    private String id;
    private TextView ten, ngaySinh, diaChi, dienThoai, email, ma;
    private Employee employee;
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.detail_info_user);

        databaseHandler = new DatabaseHandler(this);

        ten = (TextView) findViewById(R.id.tvName);
        ngaySinh = (TextView) findViewById(R.id.tvDate);
        diaChi = (TextView) findViewById(R.id.tvAddress);
        email = (TextView) findViewById(R.id.tvEmail);
        dienThoai = (TextView) findViewById(R.id.tvPhone);
        ma = (TextView) findViewById(R.id.tvMa);

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
            employee = databaseHandler.getEmployee(id);
            if (employee != null) {
                ten.setText(employee.getName());
                ngaySinh.setText(employee.getDateOfBirth());
                diaChi.setText(employee.getAddress());
                email.setText(employee.getEmail());
                dienThoai.setText(employee.getPhone());
                ma.setText(employee.getId());
            }
        }

    }

    @Override
    public void onClick(View v) {

    }

}
