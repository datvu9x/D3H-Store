package dev.datvt.clothingstored3h.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import dev.datvt.clothingstored3h.R;
import dev.datvt.clothingstored3h.fragments.FragmentAddProduct;
import dev.datvt.clothingstored3h.fragments.FragmentTransferShift;
import dev.datvt.clothingstored3h.fragments.FragmentCreateBill;
import dev.datvt.clothingstored3h.fragments.FragmentMain;
import dev.datvt.clothingstored3h.fragments.FragmentSummary;
import dev.datvt.clothingstored3h.fragments.FragmentSearchProduct;
import dev.datvt.clothingstored3h.utils.ConstantHelper;

public class MainActivity extends RootActivity implements View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Toolbar toolbar;

    private int cur_fragment = ConstantHelper.FRAGMENT_MAIN;
    public static String  id;
    public static double  money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Intent intent = getIntent();
        if (intent != null) {
            money = intent.getDoubleExtra("money", 0);
            id = intent.getStringExtra("id");
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.nav_open_drawer, R.string.nav_close_drawer);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if (savedInstanceState == null) {
            cur_fragment = ConstantHelper.FRAGMENT_MAIN;
            Fragment fragment = new FragmentMain();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            transaction.replace(R.id.frameContainer, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    private void exchangeFragment(int id) {
        Fragment fragment = null;

        if (id == ConstantHelper.FRAGMENT_MAIN) {
            cur_fragment = ConstantHelper.FRAGMENT_MAIN;
            fragment = new FragmentMain();

        } else if (id == ConstantHelper.FRAGMENT_CREATE_BILL) {
            cur_fragment = ConstantHelper.FRAGMENT_CREATE_BILL;
            fragment = new FragmentCreateBill();

        } else if (id == ConstantHelper.FRAGMENT_TRANSFER_SHIFT) {
            cur_fragment = ConstantHelper.FRAGMENT_TRANSFER_SHIFT;
            fragment = new FragmentTransferShift();

        } else if (id == ConstantHelper.FRAGMENT_ADD_PRODUCT) {
            cur_fragment = ConstantHelper.FRAGMENT_ADD_PRODUCT;
            fragment = new FragmentAddProduct();

        } else if (id == ConstantHelper.FRAGMENT_SEARCH_PRODUCT) {
            cur_fragment = ConstantHelper.FRAGMENT_SEARCH_PRODUCT;
            fragment = new FragmentSearchProduct();

        } else if (id == ConstantHelper.FRAGMENT_REPORT) {
            cur_fragment = ConstantHelper.FRAGMENT_REPORT;
            fragment = new FragmentSummary();

        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.frameContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {

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
        alertDialogBuilder.setMessage("Bạn có muốn đăng xuất không?");

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

    private void shareApp() {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String shareBody = "https://play.google.com/store/apps/details?id=" + getPackageName();
            intent.putExtra(Intent.EXTRA_SUBJECT, "Share app");
            intent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(intent, "Share"));
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void feedBackApp() {
        Intent Email = new Intent(Intent.ACTION_SEND);
        Email.setType("text/email");
        Email.putExtra(Intent.EXTRA_EMAIL, new String[]{"thedat246@gmail.com"});
        Email.putExtra(Intent.EXTRA_SUBJECT, "Feedback D3H Store");
        Email.putExtra(Intent.EXTRA_TEXT, "Dear ...," + "");
        startActivity(Intent.createChooser(Email, "Send Feedback:"));
    }

    private void openGooglePlay() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
                toolbar.setTitle("D3H Store");
                cur_fragment = ConstantHelper.FRAGMENT_MAIN;
                exchangeFragment(cur_fragment);
                break;
            case R.id.nav_lap_hoa_don:
                toolbar.setTitle("Lập hóa đơn");
                cur_fragment = ConstantHelper.FRAGMENT_CREATE_BILL;
                exchangeFragment(cur_fragment);
                break;
            case R.id.nav_don_hang:
                Intent intent4 = new Intent(this, ManagementBill.class);
                startActivityForResult(intent4, ConstantHelper.PRODUCT_TICKET);
                break;
            case R.id.nav_nhap_hang:
                toolbar.setTitle("Thêm hàng hóa");
                cur_fragment = ConstantHelper.FRAGMENT_ADD_PRODUCT;
                exchangeFragment(cur_fragment);
                break;
            case R.id.nav_tra_cuu:
                toolbar.setTitle("Tra cứu");
                cur_fragment = ConstantHelper.FRAGMENT_SEARCH_PRODUCT;
                exchangeFragment(cur_fragment);
                break;
            case R.id.nav_chuyen_ca:
                toolbar.setTitle("Bàn giao ca");
                cur_fragment = ConstantHelper.FRAGMENT_TRANSFER_SHIFT;
                exchangeFragment(cur_fragment);
                break;
            case R.id.nav_tong_ket:
                toolbar.setTitle("Tổng kết cuối ngày");
                cur_fragment = ConstantHelper.FRAGMENT_REPORT;
                exchangeFragment(cur_fragment);
                break;

            case R.id.nav_customer:
                toolbar.setTitle("Quản lý khách hàng");
                Intent intent5 = new Intent(this, ManagementCustomer.class);
                startActivityForResult(intent5, ConstantHelper.REQUEST_CUSTOMER);
                break;
            case R.id.nav_share:
                shareApp();
                break;
            case R.id.nav_feedback:
                feedBackApp();
                break;
            case R.id.nav_rate:
                openGooglePlay();
                break;
            case R.id.nav_logout:
                exit();
                break;
        }
        item.setChecked(true);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
