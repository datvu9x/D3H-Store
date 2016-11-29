package dev.datvt.clothingstored3h.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import dev.datvt.clothingstored3h.R;
import dev.datvt.clothingstored3h.activities.MainActivity;
import dev.datvt.clothingstored3h.models.Employee;
import dev.datvt.clothingstored3h.utils.DatabaseHandler;
import dev.datvt.clothingstored3h.utils.ToolsHelper;

/**
 * Created by DatVIT on 10/13/2016.
 */

public class FragmentLogin extends Fragment implements View.OnClickListener {

    private View view;
    private AutoCompleteTextView idEmployee;
    private EditText pass, money;
    private LinearLayout btnLogin, btnSign, btnPass;
    private DatabaseHandler databaseHandler;
    private ArrayList<String> employeeArrayList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHandler = new DatabaseHandler(getActivity());
        employeeArrayList = (ArrayList<String>) databaseHandler.getAllIDEmployees();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_fragment, container, false);

        idEmployee = (AutoCompleteTextView) view.findViewById(R.id.actIdEmployee);
        pass = (EditText) view.findViewById(R.id.etPass);
        money = (EditText) view.findViewById(R.id.etMoney);

        btnLogin = (LinearLayout) view.findViewById(R.id.btnLogin);
        btnPass = (LinearLayout) view.findViewById(R.id.btnPassword);
        btnSign = (LinearLayout) view.findViewById(R.id.btnSign);

        btnLogin.setOnClickListener(this);
        btnPass.setOnClickListener(this);
        btnSign.setOnClickListener(this);

        if (employeeArrayList != null && employeeArrayList.size() > 0) {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, employeeArrayList);
            idEmployee.setAdapter(arrayAdapter);
        }

//        money.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                money.setText(ToolsHelper.intToString(Float.parseFloat(money.getText().toString())));
//            }
//        });

        return view;
    }

    private void loginSystem() {
        String id = idEmployee.getText().toString().trim();
        String pw = pass.getText().toString().trim();
        String moneyRecei = money.getText().toString().trim();

        if (id.isEmpty() || pw.isEmpty() || moneyRecei.isEmpty()) {
            ToolsHelper.toast(getActivity(), "Bạn chưa nhập đầy đủ thông tin");
        } else {
            if (databaseHandler.isCheckLogin(id, pw)) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("money", Float.parseFloat(moneyRecei));
                intent.putExtra("id", id);
                startActivity(intent);
                idEmployee.setText("");
                pass.setText("");
                money.setText("");
//                getActivity().finish();
            } else {
                ToolsHelper.toast(getActivity(), "Mã nhân viên hoặc mật khẩu không đúng");
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
            Toast.makeText(getActivity(), "Thay đổi mật khẩu", Toast.LENGTH_SHORT).show();
        }

        if (v == btnSign) {
            Toast.makeText(getActivity(), "Đăng ký tài khoản", Toast.LENGTH_SHORT).show();
        }
    }
}
