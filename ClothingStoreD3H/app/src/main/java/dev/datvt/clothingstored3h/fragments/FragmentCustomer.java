package dev.datvt.clothingstored3h.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import dev.datvt.clothingstored3h.R;
import dev.datvt.clothingstored3h.adapters.CustomerAdapter;
import dev.datvt.clothingstored3h.models.Customer;

/**
 * Created by DatVIT on 10/16/2016.
 */

public class FragmentCustomer extends Fragment implements View.OnClickListener {

    private View viewFragment;
    private ListView listCustomer;
    private CustomerAdapter customerAdapter;
    private ArrayList<Customer> customerArrayList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewFragment = inflater.inflate(R.layout.fragment_customer, container, false);

        listCustomer = (ListView) viewFragment.findViewById(R.id.listCustomer);

        new GetCustomer().execute();

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
            customerAdapter = new CustomerAdapter(getActivity(), customerArrayList);
            listCustomer.setAdapter(customerAdapter);
        }

        @Override
        protected Void doInBackground(Void... params) {
            customerArrayList = new ArrayList<>();
            customerArrayList.add(new Customer("Vũ Thế Đạt", "Gia Lâm - Hà Nội"));
            customerArrayList.add(new Customer("Nguyễn Minh Đức", "Bắc Ninh"));
            customerArrayList.add(new Customer("Đào Quang Duy", "Hà Tây"));
            customerArrayList.add(new Customer("Vũ Quang Hải", "Hải Dương"));
            customerArrayList.add(new Customer("Hoàng Thế Hà", "Thái Nguyên"));
            customerArrayList.add(new Customer("Nguyễn Thị Mai Giang", "Hải Phòng"));
            customerArrayList.add(new Customer("Nguyễn Thị Xuân Lâm", "Lào Cai"));
            customerArrayList.add(new Customer("Đặng Thị Thu Thảo", "Thanh Hóa"));
            customerArrayList.add(new Customer("Trần Thị Thu Hằng", "Hà Nội"));
            return null;
        }
    }

    @Override
    public void onClick(View v) {

    }
}
