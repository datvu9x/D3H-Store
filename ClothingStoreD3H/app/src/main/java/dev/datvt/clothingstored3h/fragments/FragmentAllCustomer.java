package dev.datvt.clothingstored3h.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dev.datvt.clothingstored3h.R;
import dev.datvt.clothingstored3h.activities.CustomerDetail;
import dev.datvt.clothingstored3h.adapters.CustomerAdapter;
import dev.datvt.clothingstored3h.adapters.ProductAdapter;
import dev.datvt.clothingstored3h.models.Customer;
import dev.datvt.clothingstored3h.utils.ConstantHelper;
import dev.datvt.clothingstored3h.utils.DatabaseHandler;

/**
 * Created by DatVIT on 10/16/2016.
 */

public class FragmentAllCustomer extends Fragment implements View.OnClickListener {

    private View viewFragment;
    private ListView listCustomer;
    private CustomerAdapter customerAdapter;
    private List<Customer> customerArrayList;
    private DatabaseHandler databaseHandler;
    private ImageView btnSearch, btnDel;
    private List<String> arrayListProduct;
    private AutoCompleteTextView etSearch;
    private ArrayAdapter adapterSearch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewFragment = inflater.inflate(R.layout.fragment_customer, container, false);

        databaseHandler = new DatabaseHandler(getActivity());

        listCustomer = (ListView) viewFragment.findViewById(R.id.listCustomer);
        etSearch = (AutoCompleteTextView) viewFragment.findViewById(R.id.etSearch);
        btnDel = (ImageView) viewFragment.findViewById(R.id.imgDelete);
        btnSearch = (ImageView) viewFragment.findViewById(R.id.imgSearch);

        etSearch.setHint("Nhập tên khách hàng VD(Vũ Thế Đat ...)");
        arrayListProduct = new ArrayList<>();
        if (databaseHandler.getListNameCustomer().size() > 0) {
            arrayListProduct = databaseHandler.getListNameCustomer();
            Log.e("DATA_SIZE", arrayListProduct.size() + "");
        }
        adapterSearch = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, arrayListProduct);
        etSearch.setAdapter(adapterSearch);


        new GetCustomer().execute();

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.setText("");
                new GetCustomer().execute();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etSearch.getText() != null && !etSearch.getText().toString().isEmpty()) {
                    customerArrayList = databaseHandler.getCusomerByName(etSearch.getText().toString().trim());
                    if (customerArrayList != null && customerArrayList.size() > 0) {
                        customerAdapter = new CustomerAdapter(getActivity(), customerArrayList);
                        listCustomer.setAdapter(customerAdapter);
                    } else {
                        Toast.makeText(getActivity(), "Không tìm thấy khách hàng nào", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Bạn chưa nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return viewFragment;
    }

    private class GetCustomer extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (customerArrayList.size() > 0) {
                customerAdapter = new CustomerAdapter(getActivity(), customerArrayList);
                listCustomer.setAdapter(customerAdapter);

                listCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getActivity(), CustomerDetail.class);
                        intent.putExtra("id", customerArrayList.get(position).getId());
                        startActivityForResult(intent, ConstantHelper.DETAIL_USER);
                    }
                });
            } else {
                Toast.makeText(getActivity(), "Không tìm thấy khách hàng nào", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            customerArrayList = new ArrayList<>();
            customerArrayList = databaseHandler.getAllCustomer();
            return null;
        }
    }

    @Override
    public void onClick(View v) {

    }
}
