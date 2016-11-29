package dev.datvt.clothingstored3h.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

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
    private ArrayList<Product> productArrayList;
    private DatabaseHandler databaseHandler;

    private SwipeRefreshLayout ref;
    private TextView btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_product);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBlack));
        }

        databaseHandler = new DatabaseHandler(this);

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

        new GetProduct().execute();

    }

    @Override
    public void onClick(View v) {
        if (v == btnOk) {
            Intent intent = getIntent();
            Bundle bundle = new Bundle();
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < productArrayList.size(); i++) {
                if (productArrayList.get(i).isSale()) {
                    arrayList.add(productArrayList.get(i));
                }
            }
            bundle.putSerializable("product_list", arrayList);
            intent.putExtras(bundle);
            setResult(ConstantHelper.RESULT_LIST_PRODUCT, intent);
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
//            productArrayList = new ArrayList<>();
//            productArrayList.add(new Product("Áo khoác đôi Singapo", 10));
//            productArrayList.add(new Product("Áo khoác đôi Hàn Quốc",  20));
//            productArrayList.add(new Product("Áo khoác đôi Nỉ đẹp",  7));
//            productArrayList.add(new Product("Áo khoác đôi vải mềm", 60));
//            productArrayList.add(new Product("Quần thể thao mùa hè",  45));
//            productArrayList.add(new Product("Mũ sinh viên", 2));
//            productArrayList.add(new Product("Áo thun sinh viên",9));
//            productArrayList.add(new Product("Áo vest sang trọng",  14));
//            productArrayList.add(new Product("Quần jeans chất liệu Hàn Quốc",  38));
//            productArrayList.add(new Product("Quần shorts đẹp mới nhất", 20));
//            productArrayList.add(new Product("Quần kaki Singapore",  90));
//            productArrayList.add(new Product("Áo đẹp",  160));
//            productArrayList.add(new Product("Váy nữ hàng VN", 204));
//            productArrayList.add(new Product("Đầm công sở", 100));
//            productArrayList.add(new Product("Bộ đồ bơi Hàn Quốc",  15));
//            productArrayList.add(new Product("Áo lót nữ",  10));
//            productArrayList.add(new Product("Áo sơ mi chất lượng cao", 10));
            productArrayList = (ArrayList<Product>) databaseHandler.getAllProducts();
            Log.d("SIZE", productArrayList.size() + "");

            return null;
        }
    }
}
