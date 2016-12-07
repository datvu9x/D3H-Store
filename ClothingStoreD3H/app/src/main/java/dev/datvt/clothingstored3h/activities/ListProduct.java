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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dev.datvt.clothingstored3h.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_product);

        databaseHandler = new DatabaseHandler(this);

        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        ref = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        ref.setColorSchemeColors(getResources().getColor(R.color.colorOrange),
                getResources().getColor(R.color.colorOrange),
                getResources().getColor(R.color.colorOrange));

        lvProduct = (ListView) findViewById(R.id.listProduct);
        btnOk = (TextView) findViewById(R.id.btnOk);

        btnOk.setOnClickListener(this);

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
