package dev.datvt.clothingstored3h.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dev.datvt.clothingstored3h.R;
import dev.datvt.clothingstored3h.activities.UserDetail;
import dev.datvt.clothingstored3h.activities.MainActivity;
import dev.datvt.clothingstored3h.adapters.ProductAdapter;
import dev.datvt.clothingstored3h.models.Employee;
import dev.datvt.clothingstored3h.models.Product;
import dev.datvt.clothingstored3h.utils.ConstantHelper;
import dev.datvt.clothingstored3h.utils.DatabaseHandler;
import dev.datvt.clothingstored3h.utils.NumberTextWatcherForThousand;
import dev.datvt.clothingstored3h.utils.ToolsHelper;

/**
 * Created by DatVIT on 10/13/2016.
 */

public class FragmentMain extends Fragment implements View.OnClickListener {

    private View view;
    private LinearLayout lnWarning;
    private LinearLayout fragmentProduct, infoFrame;
    private SwipeRefreshLayout ref;

    private ListView lvProduct;
    private ProductAdapter productAdapter;
    private List<Product> productArrayList;
    private List<String> arrayListProduct;


    private TextView nhanVien, tienGiao, tienBan;

    private DatabaseHandler databaseHandler;
    private AutoCompleteTextView etSearch;
    private ImageView imgDel, imgSearch;

    private Employee employee;
    private double tienBanHang = 0;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private AVLoadingIndicatorView avi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);

        databaseHandler = new DatabaseHandler(getActivity());

        lnWarning = (LinearLayout) view.findViewById(R.id.lnWarning);
        fragmentProduct = (LinearLayout) view.findViewById(R.id.fragmentProduct);
        infoFrame = (LinearLayout) view.findViewById(R.id.infoFrame);

        imgDel = (ImageView) view.findViewById(R.id.imgDelete);
        imgSearch = (ImageView) view.findViewById(R.id.imgSearch);
        nhanVien = (TextView) view.findViewById(R.id.nhanVien);
        tienGiao = (TextView) view.findViewById(R.id.tienDuocGiao);
        tienBan = (TextView) view.findViewById(R.id.tienBanHang);
        etSearch = (AutoCompleteTextView) view.findViewById(R.id.etSearch);
        avi = (AVLoadingIndicatorView) view.findViewById(R.id.avi);

        arrayListProduct = new ArrayList<>();
        if (databaseHandler.getAllProducts().size() > 0) {
            for (int i = 0; i < databaseHandler.getAllProducts().size(); i++) {
                arrayListProduct.add(databaseHandler.getAllProducts().get(i).getTenHang());
            }
        }
        ArrayAdapter arrayAdapter1 = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, arrayListProduct);
        etSearch.setAdapter(arrayAdapter1);

        lnWarning.setVisibility(View.INVISIBLE);

        ref = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        ref.setColorSchemeColors(getResources().getColor(R.color.colorOrange),
                getResources().getColor(R.color.colorOrange),
                getResources().getColor(R.color.colorOrange));

        lvProduct = (ListView) view.findViewById(R.id.listProduct);

        ref.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetProduct().execute();
            }
        });

        employee = databaseHandler.getEmployee(MainActivity.id);
        if (employee != null) {
            nhanVien.setText(employee.getName());
            tienGiao.setText(NumberTextWatcherForThousand.getDecimalFormattedString(MainActivity.money + "") + " $");
            tienBanHang = databaseHandler.getMoneySale(simpleDateFormat.format(new Date()), employee.getId());
            tienBan.setText(NumberTextWatcherForThousand.getDecimalFormattedString(tienBanHang + "") + " $");
        }

        infoFrame.setOnClickListener(this);
        imgDel.setOnClickListener(this);
        imgSearch.setOnClickListener(this);

        new GetProduct().execute();

        return view;
    }


    @Override
    public void onClick(View v) {
        if (v == infoFrame) {
            Intent intent = new Intent(getActivity(), UserDetail.class);
            intent.putExtra("id", MainActivity.id);
            startActivityForResult(intent, ConstantHelper.DETAIL_USER);
        }
        if (v == imgSearch) {
            if (etSearch.getText() != null && !etSearch.getText().toString().isEmpty()) {
                new GetProductName().execute(etSearch.getText().toString().trim());
            } else {
                Toast.makeText(getActivity(), "Bạn chưa nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
            }
        }
        if (v == imgDel) {
            etSearch.setText("");
            new GetProduct().execute();
        }
    }

    private class GetProductName extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ref.setRefreshing(true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            productAdapter = new ProductAdapter(getActivity(), productArrayList);
            lvProduct.setAdapter(productAdapter);

            if (productArrayList.size() <= 0) {
                Toast.makeText(getActivity(), "Không tìm thấy sản phẩm nào", Toast.LENGTH_SHORT).show();
            }
            ref.setRefreshing(false);
        }

        @Override
        protected Void doInBackground(String... params) {
            if (params[0] != null && !params[0].isEmpty()) {
                productArrayList = databaseHandler.getProductName(params[0]);
            }
            return null;
        }
    }

    private class GetProduct extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ref.setRefreshing(true);
            avi.smoothToShow();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            productAdapter = new ProductAdapter(getActivity(), productArrayList);
            lvProduct.setAdapter(productAdapter);

            if (productArrayList.size() == 0) {
                fragmentProduct.setVisibility(View.INVISIBLE);
                lnWarning.setVisibility(View.VISIBLE);
            } else {
                fragmentProduct.setVisibility(View.VISIBLE);
                lnWarning.setVisibility(View.INVISIBLE);
            }
            ref.setRefreshing(false);
            avi.smoothToHide();
        }

        @Override
        protected Void doInBackground(Void... params) {
            productArrayList = databaseHandler.getAllProducts();
            Log.d("SIZE", productArrayList.size() + "");

            return null;
        }
    }

}
