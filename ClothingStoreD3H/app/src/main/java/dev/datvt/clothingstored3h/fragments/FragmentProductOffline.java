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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dev.datvt.clothingstored3h.R;
import dev.datvt.clothingstored3h.activities.CreateBill;
import dev.datvt.clothingstored3h.activities.MainActivity;
import dev.datvt.clothingstored3h.activities.StoreBillDetail;
import dev.datvt.clothingstored3h.adapters.ProductAdapter;
import dev.datvt.clothingstored3h.adapters.TicketOfflineAdapter;
import dev.datvt.clothingstored3h.models.Product;
import dev.datvt.clothingstored3h.models.StoreBill;
import dev.datvt.clothingstored3h.utils.ConstantHelper;
import dev.datvt.clothingstored3h.utils.DatabaseHandler;

/**
 * Created by DatVIT on 10/16/2016.
 */

public class FragmentProductOffline extends Fragment implements View.OnClickListener {

    private View viewFragment;
    private TextView btnCreateProductTicket;
    private ListView listView;
    private DatabaseHandler databaseHandler;
    private List<StoreBill> arrayList;
    private TicketOfflineAdapter ticketOfflineAdapter;
    private SwipeRefreshLayout ref;
    private int index = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHandler = new DatabaseHandler(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewFragment = inflater.inflate(R.layout.fragment_product_ticket_offline, container, false);

        btnCreateProductTicket = (TextView) viewFragment.findViewById(R.id.btnCreateProductTicket);
        listView = (ListView) viewFragment.findViewById(R.id.listProductTicketOffline);

        ref = (SwipeRefreshLayout) viewFragment.findViewById(R.id.swipeRefreshLayout);
        ref.setColorSchemeColors(getResources().getColor(R.color.colorOrange),
                getResources().getColor(R.color.colorOrange),
                getResources().getColor(R.color.colorOrange));

        btnCreateProductTicket.setOnClickListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index = position;
                Log.e("INDEX", index + "");
                Intent intent = new Intent(getActivity(), StoreBillDetail.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bill_detail", arrayList.get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent, ConstantHelper.REQUEST_DETAIL_BILL);
            }
        });

        ref.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetStoreBill().execute();
            }
        });

        new GetStoreBill().execute();

        return viewFragment;
    }

    private class GetStoreBill extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ref.setRefreshing(true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (arrayList.size() > 0) {
                ticketOfflineAdapter = new TicketOfflineAdapter(getActivity(), arrayList);
                listView.setAdapter(ticketOfflineAdapter);
            } else {
                Toast.makeText(getActivity(), "Không có đơn hàng nào", Toast.LENGTH_SHORT).show();
            }
            ref.setRefreshing(false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayList = databaseHandler.getAllDonHangOffline();
            Log.d("SIZE", arrayList.size() + "");

            return null;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnCreateProductTicket) {
            Intent intent = new Intent(getActivity(), CreateBill.class);
            startActivityForResult(intent, ConstantHelper.FRAGMENT_CREATE_BILL);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ConstantHelper.FRAGMENT_CREATE_BILL && data != null) {
            if (resultCode == ConstantHelper.FRAGMENT_CREATE_BILL_RESULT) {
                new GetStoreBill().execute();
            }
        }

        if (requestCode == ConstantHelper.REQUEST_DETAIL_BILL && data != null) {
            if (resultCode == ConstantHelper.RESULT_DETAIL_BILL) {
                new GetStoreBill().execute();
            }
        }
    }
}
