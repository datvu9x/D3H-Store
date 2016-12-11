package dev.datvt.clothingstored3h.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import dev.datvt.clothingstored3h.R;
import dev.datvt.clothingstored3h.activities.BillOnlineDetail;
import dev.datvt.clothingstored3h.activities.CreateBillOnline;
import dev.datvt.clothingstored3h.adapters.TicketOnlineAdapter;
import dev.datvt.clothingstored3h.models.BillOnline;
import dev.datvt.clothingstored3h.utils.ConstantHelper;
import dev.datvt.clothingstored3h.utils.DatabaseHandler;
import dev.datvt.clothingstored3h.utils.FileOperations;

/**
 * Created by DatVIT on 10/16/2016.
 */

public class FragmentProductOnline extends Fragment {

    private View viewFragment;
    private ListView listView;
    private DatabaseHandler databaseHandler;
    private List<BillOnline> billArrayList;
    private TicketOnlineAdapter ticketOnlineAdapter;
    private AVLoadingIndicatorView avi;
    public static String PATH = "/D3HStore/Bill_Online/bill_online.json";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHandler = new DatabaseHandler(getActivity());
        billArrayList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewFragment = inflater.inflate(R.layout.fragment_product_ticket_online, container, false);

        listView = (ListView) viewFragment.findViewById(R.id.listProductTicketOnline);
        avi = (AVLoadingIndicatorView) viewFragment.findViewById(R.id.avi);

        registerForContextMenu(listView);

        new GetBillOnline().execute(PATH);

        return viewFragment;
    }

    private class GetBillOnline extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            avi.smoothToShow();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            avi.smoothToHide();
            if (billArrayList != null && billArrayList.size() > 0) {
                ticketOnlineAdapter = new TicketOnlineAdapter(getActivity(), billArrayList);
                listView.setAdapter(ticketOnlineAdapter);
            } else {
                Toast.makeText(getActivity(), "Không có đơn hàng online nào", Toast.LENGTH_SHORT);
            }
        }

        @Override
        protected Void doInBackground(String... params) {
            if (params != null && !params[0].isEmpty()) {
                readJsonFile(params[0]);
            }
            return null;
        }
    }

    private void readJsonFile(String path) {
        try {
            File yourFile = new File(Environment.getExternalStorageDirectory(), path);
            Log.e("FILE", yourFile.getAbsolutePath());
            if (yourFile.exists()) {
                FileInputStream stream = new FileInputStream(yourFile);
                String jsonStr = null;
                try {
                    FileChannel fc = stream.getChannel();
                    MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

                    jsonStr = Charset.defaultCharset().decode(bb).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    stream.close();
                }

                JSONArray data = new JSONArray(jsonStr);
                Log.e("FILE_ARRAY", data.toString());
                billArrayList = new ArrayList<>();
                for (int i = 0; i < data.length(); i++) {
                    JSONObject c = data.getJSONObject(i);
                    Log.e("FILE_OBJECT", c.toString());
                    Gson gson = new Gson();

                    BillOnline billOnline = gson.fromJson(c.toString(), BillOnline.class);
                    billArrayList.add(billOnline);
                }
            } else {
                Log.e("FILE", "Không tìm thấy file dữ liệu");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.menu_online, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.nav_create_bill:
                if (billArrayList != null && billArrayList.size() > 0) {
                    Intent intent = new Intent(getActivity(), CreateBillOnline.class);
                    intent.putExtra("pos", info.position);
                    startActivityForResult(intent, ConstantHelper.REQUEST_BILL_ONLINE);
                }
                break;
            case R.id.nav_info:
                if (billArrayList != null && billArrayList.size() > 0) {
                    Intent intent = new Intent(getActivity(), BillOnlineDetail.class);
                    intent.putExtra("pos", info.position);
                    startActivityForResult(intent, ConstantHelper.BILL_ONLINE_DETAIL);
                }
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ConstantHelper.REQUEST_BILL_ONLINE && data != null) {
            if (resultCode == ConstantHelper.RESULT_BILL_ONLINE) {
                final int pos = data.getIntExtra("index", 0);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        deleteBill(pos);
                    }
                });
                new GetBillOnline().execute(PATH);
            }
        }
    }

    private void deleteBill(int pos) {
        FileOperations fop = new FileOperations();
        File f = new File(Environment.getExternalStorageDirectory(), PATH);
        String path = Environment.getExternalStorageDirectory() + PATH;
        if (!f.exists()) {
            fop.write(path, "[]");
        }

        try {
            String jstr = fop.read(path);
            JSONArray jarr = new JSONArray(jstr);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                jarr.remove(pos);
            } else {
                ArrayList<String> list = new ArrayList<String>();
                if (jarr != null) {
                    for (int i = 0; i < jarr.length(); i++) {
                        if (i != pos) {
                            list.add(jarr.get(i).toString());
                        }
                    }
                }
                jarr = new JSONArray(list);
            }
            fop.write(path, jarr.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
