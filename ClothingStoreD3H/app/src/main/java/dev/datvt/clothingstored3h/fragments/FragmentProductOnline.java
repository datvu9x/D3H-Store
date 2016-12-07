package dev.datvt.clothingstored3h.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import dev.datvt.clothingstored3h.R;
import dev.datvt.clothingstored3h.adapters.TicketOfflineAdapter;
import dev.datvt.clothingstored3h.models.StoreBill;
import dev.datvt.clothingstored3h.utils.DatabaseHandler;

/**
 * Created by DatVIT on 10/16/2016.
 */

public class FragmentProductOnline extends Fragment {

    private View viewFragment;
    private ListView listView;
    private DatabaseHandler databaseHandler;
    private List<StoreBill> arrayList;
    private TicketOfflineAdapter ticketOfflineAdapter;
    private int index = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHandler = new DatabaseHandler(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewFragment = inflater.inflate(R.layout.fragment_product_ticket_online, container, false);

        listView = (ListView) viewFragment.findViewById(R.id.listProductTicketOnline);

        getDonHang();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!arrayList.get(position).getTrangThai().equals("Đã thanh toán")) {
                    index = position;
                    Log.e("INDEX", index + "");
                } else {
                }
            }
        });

        return viewFragment;
    }

    private void getDonHang() {
        arrayList = databaseHandler.getAllDonHangOnline();
        if (arrayList.size() > 0) {
            ticketOfflineAdapter = new TicketOfflineAdapter(getActivity(), arrayList);
            listView.setAdapter(ticketOfflineAdapter);
        }
//        else {
//            Toast.makeText(getActivity(), "Không có đơn nào hàng", Toast.LENGTH_SHORT).show();
//        }
    }
}
