package dev.datvt.clothingstored3h.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dev.datvt.clothingstored3h.R;
import dev.datvt.clothingstored3h.adapters.SummaryAdapter;
import dev.datvt.clothingstored3h.models.Summary;
import dev.datvt.clothingstored3h.utils.DatabaseHandler;
import dev.datvt.clothingstored3h.utils.NumberTextWatcherForThousand;
import dev.datvt.clothingstored3h.utils.ToolsHelper;

/**
 * Created by DatVIT on 10/16/2016.
 */

public class FragmentSummary extends Fragment {

    private View viewFragment;
    private ListView listView;
    private SummaryAdapter summaryAdapter;
    private List<Summary> summaryList;
    private DatabaseHandler databaseHandler;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private EditText etDate;
    private TextView tienVon;
    private TextView tienLai;
    private TextView titleSummary;
    private ImageView loadData;
    private LinearLayout frameSummary;
    private double lai = 0, von = 0;
    private AVLoadingIndicatorView avi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewFragment = inflater.inflate(R.layout.fragment_summary, container, false);

        databaseHandler = new DatabaseHandler(getActivity());

        listView = (ListView) viewFragment.findViewById(R.id.listSummary);
        etDate = (EditText) viewFragment.findViewById(R.id.etDate);
        tienVon = (TextView) viewFragment.findViewById(R.id.tvTienVon);
        tienLai = (TextView) viewFragment.findViewById(R.id.tvTienLai);
        titleSummary = (TextView) viewFragment.findViewById(R.id.titleSummary);
        loadData = (ImageView) viewFragment.findViewById(R.id.loadData);
        frameSummary = (LinearLayout) viewFragment.findViewById(R.id.frameSummary);
        avi = (AVLoadingIndicatorView) viewFragment.findViewById(R.id.avi);

        etDate.setText(simpleDateFormat.format(new Date()));

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        loadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etDate.getText() != null && !etDate.getText().toString().isEmpty()) {
                    if (summaryList != null && summaryList.size() > 0) {
                        summaryList.clear();
                    }
                    if (summaryAdapter != null) {
                        summaryAdapter.notifyDataSetChanged();
                    }
                    new GetSummary().execute(etDate.getText().toString());
                }
            }
        });

        new GetSummary().execute(simpleDateFormat.format(new Date()));

        return viewFragment;
    }

    private class GetSummary extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            avi.smoothToShow();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            avi.smoothToHide();
            if (summaryList.size() > 0) {
                frameSummary.setVisibility(View.VISIBLE);
                summaryAdapter = new SummaryAdapter(getActivity(), summaryList);
                listView.setAdapter(summaryAdapter);

                von = 0;
                lai = 0;
                for (int i = 0; i < summaryList.size(); i++) {
                    von += summaryList.get(i).getDonGiaNhap() * summaryList.get(i).getSoLuongBan();
                    lai += (summaryList.get(i).getDonGiaBan() * summaryList.get(i).getSoLuongBan()) - (summaryList.get(i).getDonGiaNhap() * summaryList.get(i).getSoLuongBan());
                }

                tienVon.setText(NumberTextWatcherForThousand.getDecimalFormattedString(von + "") + " $");
                if (lai < 0) {
                    tienLai.setText("-" + NumberTextWatcherForThousand.getDecimalFormattedString((lai * (-1)) + "") + " $");
                } else {
                    tienVon.setText(NumberTextWatcherForThousand.getDecimalFormattedString(lai + "") + " $");
                }

                if (lai > 0) {
                    titleSummary.setText("Hôm nay được lãi");
                } else if (lai == 0) {
                    titleSummary.setText("Hôm nay hòa vốn");
                } else {
                    titleSummary.setText("Hôm nay lỗ vốn");
                }

            } else {
                frameSummary.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), "Hôm này không bán được mặt hàng nào", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected Void doInBackground(String... params) {
            if (params != null && !params[0].isEmpty()) {
                summaryList = new ArrayList<>();
                summaryList = databaseHandler.getSumary(params[0]);
                Log.e("SUMMARY", "SIZE: " + summaryList.size());
            }
            return null;
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getActivity().getSupportFragmentManager(), "DatePicker");
    }
}
