package dev.datvt.clothingstored3h.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import dev.datvt.clothingstored3h.R;
import dev.datvt.clothingstored3h.models.Employee;
import dev.datvt.clothingstored3h.utils.DatabaseHandler;
import dev.datvt.clothingstored3h.utils.NumberTextWatcherForThousand;
import dev.datvt.clothingstored3h.utils.ToolsHelper;

/**
 * Created by DatVIT on 10/13/2016.
 */

public class LoginAcitivty extends RootActivity implements View.OnClickListener {

    private DatabaseHandler databaseHandler;
    private AutoCompleteTextView idEmployee;
    private EditText pass, money;
    private LinearLayout btnLogin, btnSign, btnPass;
    private ArrayList<String> employeeArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_fragment);

        databaseHandler = new DatabaseHandler(this);
        addEmployeeDefault();
        getId();
    }

    private void addEmployeeDefault() {
        ArrayList<Employee> employeeArrayList = new ArrayList<>();
        employeeArrayList.add(new Employee("NV01", "123", "Vũ Thế Đạt", "Nam", "24-06-1995",
                "0981449295", "Kim Sơn - Gia Lâm - Hà Nội", "thedat246@gmail.com"));
        employeeArrayList.add(new Employee("NV02", "123", "Nguyễn Minh Đức", "Nam", "02-10-1995",
                "0164929381", "Bắc Ninh", "thedat246@gmail.com"));
        employeeArrayList.add(new Employee("NV03", "123", "Đào Quang Duy", "Nam", "11-10-1995",
                "0176283233", "Hà Tây", "thedat246@gmail.com"));
        employeeArrayList.add(new Employee("NV04", "123", "Vũ Quang Hải", "Nam", "10-10-1995",
                "0982131231", "Hải Dương", "thedat246@gmail.com"));

        for (int i = 0; i < employeeArrayList.size(); i++) {
            if (!databaseHandler.isCheckEmployee(employeeArrayList.get(i).getId())) {
                databaseHandler.addEmployee(employeeArrayList.get(i));
            }
        }
    }

    private void getId() {
        idEmployee = (AutoCompleteTextView) findViewById(R.id.actIdEmployee);
        pass = (EditText) findViewById(R.id.etPass);
        money = (EditText) findViewById(R.id.etMoney);

        btnLogin = (LinearLayout) findViewById(R.id.btnLogin);
        btnPass = (LinearLayout) findViewById(R.id.btnPassword);
        btnSign = (LinearLayout) findViewById(R.id.btnSign);

        btnLogin.setOnClickListener(this);
        btnPass.setOnClickListener(this);
        btnSign.setOnClickListener(this);

        if (employeeArrayList == null) {
            employeeArrayList = (ArrayList<String>) databaseHandler.getAllIDEmployees();
        }

        if (employeeArrayList != null && employeeArrayList.size() > 0) {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, employeeArrayList);
            idEmployee.setAdapter(arrayAdapter);
        }
        money.addTextChangedListener(new NumberTextWatcherForThousand(money));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Bạn có muốn thoát ứng dụng không?");

        alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    private void loginSystem() {
        String id = idEmployee.getText().toString().trim();
        String pw = pass.getText().toString().trim();
        String moneyRecei = money.getText().toString().trim();

        if (id.isEmpty() || pw.isEmpty() || moneyRecei.isEmpty()) {
            ToolsHelper.toast(LoginAcitivty.this, "Bạn chưa nhập đầy đủ thông tin");
        } else {
            if (databaseHandler.isCheckLogin(id, pw)) {
                Intent intent = new Intent(LoginAcitivty.this, MainActivity.class);
                intent.putExtra("money", Double.parseDouble(NumberTextWatcherForThousand.trimCommaOfString(moneyRecei)));
                intent.putExtra("id", id);
                startActivity(intent);
                idEmployee.setText("");
                pass.setText("");
                money.setText("");
            } else {
                ToolsHelper.toast(LoginAcitivty.this, "Mã nhân viên hoặc mật khẩu không đúng");
                idEmployee.requestFocus();
            }
        }

    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            loginSystem();
        }

        if (v == btnPass) {
            Toast.makeText(LoginAcitivty.this, "Thay đổi mật khẩu", Toast.LENGTH_SHORT).show();
        }

        if (v == btnSign) {
            Toast.makeText(LoginAcitivty.this, "Đăng ký tài khoản", Toast.LENGTH_SHORT).show();
        }
    }
}
