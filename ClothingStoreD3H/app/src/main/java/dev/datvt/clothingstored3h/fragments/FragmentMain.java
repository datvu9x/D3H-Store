package dev.datvt.clothingstored3h.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import dev.datvt.clothingstored3h.R;
import dev.datvt.clothingstored3h.activities.DetailUser;
import dev.datvt.clothingstored3h.adapters.ProductAdapter;
import dev.datvt.clothingstored3h.models.Product;
import dev.datvt.clothingstored3h.utils.ConstantHelper;
import dev.datvt.clothingstored3h.utils.DatabaseHandler;

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
    private ArrayList<Product> productArrayList;

    private ImageView imgDel, imgSearch;

    private DatabaseHandler databaseHandler;


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

        infoFrame.setOnClickListener(this);
        imgDel.setOnClickListener(this);
        imgSearch.setOnClickListener(this);

        new GetProduct().execute();


        return view;
    }


    @Override
    public void onClick(View v) {
        if (v== infoFrame) {
            Intent intent = new Intent(getActivity(), DetailUser.class);
            startActivityForResult(intent, ConstantHelper.DETAIL_USER);
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
