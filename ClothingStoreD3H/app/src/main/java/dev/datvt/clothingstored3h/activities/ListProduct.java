package dev.datvt.clothingstored3h.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dev.datvt.clothingstored3h.R;
import dev.datvt.clothingstored3h.adapters.ProductAdapter;
import dev.datvt.clothingstored3h.adapters.ProductSaleAdapter;
import dev.datvt.clothingstored3h.models.Product;
import dev.datvt.clothingstored3h.utils.ConstantHelper;
import dev.datvt.clothingstored3h.utils.DatabaseHandler;

/**
 * Created by DatVIT on 10/27/2016.
 */

public class ListProduct extends RootActivity implements View.OnClickListener {

    private ListView lvProduct;
    private ProductSaleAdapter productSaleAdapter;
    private List<Product> productArrayList;
    private ArrayList<Product> productList;
    private DatabaseHandler databaseHandler;

    private SwipeRefreshLayout ref;
    private TextView btnOk;
    private ImageView btnBack;

    private AutoCompleteTextView etSearch;
    private ImageView imgDel, imgSearch;
    private List<String> arrayListProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_product);

        databaseHandler = new DatabaseHandler(this);

        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        imgDel = (ImageView) findViewById(R.id.imgDelete);
        imgSearch = (ImageView) findViewById(R.id.imgSearch);
        etSearch = (AutoCompleteTextView) findViewById(R.id.etSearchOne);

        ref = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        ref.setColorSchemeColors(getResources().getColor(R.color.colorOrange),
                getResources().getColor(R.color.colorOrange),
                getResources().getColor(R.color.colorOrange));

        lvProduct = (ListView) findViewById(R.id.listProduct);
        btnOk = (TextView) findViewById(R.id.btnOk);

        ref.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetProduct().execute();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            productList = bundle.getParcelableArrayList("list");
            if (productList == null) {
                productList = new ArrayList<>();
            }
        }

        arrayListProduct = new ArrayList<>();
        if (databaseHandler.getAllProducts().size() > 0) {
            for (int i = 0; i < databaseHandler.getAllProducts().size(); i++) {
                arrayListProduct.add(databaseHandler.getAllProducts().get(i).getTenHang());
            }
        }
        ArrayAdapter arrayAdapter1 = new ArrayAdapter(ListProduct.this, android.R.layout.simple_dropdown_item_1line, arrayListProduct);
        etSearch.setAdapter(arrayAdapter1);

        btnOk.setOnClickListener(this);
        imgDel.setOnClickListener(this);
        imgSearch.setOnClickListener(this);

        new GetProduct().execute();

    }

    @Override
    public void onClick(View v) {
        if (v == btnOk) {
            Intent intent = getIntent();
            Bundle bundle = new Bundle();
            for (int i = 0; i < productArrayList.size(); i++) {
                if (productArrayList.get(i).isSale()) {
                    int ban = productArrayList.get(i).getSoLuongBan();
                    int conLai = productArrayList.get(i).getSoLuongConLai();
                    if (conLai >= ban) {
                        productArrayList.get(i).setSoLuongNhap(conLai - ban);
                        productList.add(productArrayList.get(i));
                    }
                }
            }
            bundle.putParcelableArrayList("list_product", productList);
            intent.putExtras(bundle);
            setResult(ConstantHelper.RESULT_LIST_PRODUCT, intent);
            finish();
        }
        if (v == btnBack) {
            finish();
        }

        if (v == imgSearch) {
            if (etSearch.getText() != null && !etSearch.getText().toString().isEmpty()) {
                new GetProductName().execute(etSearch.getText().toString().trim());
            } else {
                Toast.makeText(ListProduct.this, "Bạn chưa nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
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
            productSaleAdapter = new ProductSaleAdapter(ListProduct.this, productArrayList);
            lvProduct.setAdapter(productSaleAdapter);

            if (productArrayList.size() <= 0) {
                Toast.makeText(ListProduct.this, "Không tìm thấy sản phẩm nào", Toast.LENGTH_SHORT).show();
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
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            productSaleAdapter = new ProductSaleAdapter(ListProduct.this, productArrayList);
            lvProduct.setAdapter(productSaleAdapter);
            ref.setRefreshing(false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            productArrayList = databaseHandler.getAllProducts();
            Log.d("SIZE", productArrayList.size() + "");

            return null;
        }
    }
}
