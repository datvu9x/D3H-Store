package dev.datvt.clothingstored3h.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import dev.datvt.clothingstored3h.R;
import dev.datvt.clothingstored3h.fragments.FragmentLogin;
import dev.datvt.clothingstored3h.fragments.FragmentStart1;
import dev.datvt.clothingstored3h.fragments.FragmentStart2;
import dev.datvt.clothingstored3h.fragments.FragmentStart3;
import dev.datvt.clothingstored3h.models.Employee;
import dev.datvt.clothingstored3h.utils.ConstantHelper;
import dev.datvt.clothingstored3h.utils.DatabaseHandler;
import dev.datvt.clothingstored3h.utils.ToolsHelper;

/**
 * Created by DatVIT on 10/13/2016.
 */

public class LoginAcitivty extends RootActivity implements View.OnClickListener {

    private int cur_fragment = ConstantHelper.FRAGMENT_START_1;
    private RelativeLayout formIntroBack, formIntroNext;
    private ImageView ivNext;
    private TextView tvLogin;
    private boolean isNext = false;
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        if (savedInstanceState == null) {
            cur_fragment = ConstantHelper.FRAGMENT_LOGIN;
            Fragment fragment = new FragmentLogin();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            transaction.replace(R.id.fragStart, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        databaseHandler = new DatabaseHandler(this);
        getId();
        addEmployeeDefault();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (cur_fragment == ConstantHelper.FRAGMENT_LOGIN) {
            formIntroNext.setVisibility(View.INVISIBLE);
            formIntroBack.setVisibility(View.INVISIBLE);
        }
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
        formIntroBack = (RelativeLayout) findViewById(R.id.formIntroBack);
        formIntroNext = (RelativeLayout) findViewById(R.id.formIntroNext);
        ivNext = (ImageView) findViewById(R.id.ivNext);
        tvLogin = (TextView) findViewById(R.id.tvDangNhap);

        formIntroBack.setOnClickListener(this);
        formIntroNext.setOnClickListener(this);

        formIntroNext.setVisibility(View.INVISIBLE);
        formIntroBack.setVisibility(View.INVISIBLE);
    }


//    private void exchangeFragment(int id) {
//        Fragment fragment = null;
//
//        if (id == ConstantHelper.FRAGMENT_START_1) {
//            cur_fragment = ConstantHelper.FRAGMENT_START_1;
//            fragment = new FragmentStart1();
//
//        } else if (id == ConstantHelper.FRAGMENT_START_2) {
//            cur_fragment = ConstantHelper.FRAGMENT_START_2;
//            fragment = new FragmentStart2();
//
//        } else if (id == ConstantHelper.FRAGMENT_START_3) {
//            cur_fragment = ConstantHelper.FRAGMENT_START_3;
//            fragment = new FragmentStart3();
//
//        } else if (id == ConstantHelper.FRAGMENT_LOGIN) {
//            cur_fragment = ConstantHelper.FRAGMENT_LOGIN;
//            fragment = new FragmentLogin();
//            formIntroNext.setVisibility(View.INVISIBLE);
//            formIntroBack.setVisibility(View.INVISIBLE);
//        }
//
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        if (isNext) {
//            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
//        } else {
//            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
//        }
//        transaction.replace(R.id.fragStart, fragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

//        switch (id) {
//            case R.id.formIntroBack:
//                cur_fragment--;
//                isNext = false;
//                if (cur_fragment < ConstantHelper.FRAGMENT_START_2) {
//                    formIntroBack.setVisibility(View.INVISIBLE);
//                }
//
//                if (cur_fragment < ConstantHelper.FRAGMENT_LOGIN) {
//                    ivNext.setVisibility(View.VISIBLE);
//                    tvLogin.setVisibility(View.INVISIBLE);
//                }
//
//                if (cur_fragment >= ConstantHelper.FRAGMENT_START_1) {
//                    exchangeFragment(cur_fragment);
//                } else {
//                    cur_fragment = ConstantHelper.FRAGMENT_START_1;
//                }
//                break;
//            case R.id.formIntroNext:
//                isNext = true;
//                cur_fragment++;
//                if (cur_fragment > ConstantHelper.FRAGMENT_START_1) {
//                    formIntroBack.setVisibility(View.VISIBLE);
//                }
//
//                if (cur_fragment > ConstantHelper.FRAGMENT_START_2) {
//                    ivNext.setVisibility(View.INVISIBLE);
//                    tvLogin.setVisibility(View.VISIBLE);
//                }
//
//                if (cur_fragment <= ConstantHelper.FRAGMENT_LOGIN) {
//                    exchangeFragment(cur_fragment);
//                } else {
//                    cur_fragment = ConstantHelper.FRAGMENT_LOGIN;
//                }
//
//                break;
//        }
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


}
