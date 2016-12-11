package dev.datvt.clothingstored3h.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import dev.datvt.clothingstored3h.R;
import dev.datvt.clothingstored3h.models.BillOnline;

/**
 * Created by DatVIT on 12/9/2016.
 */
public class BillOnlineDetail extends RootActivity {

    private ImageView btnBack;
    private TextView ten, email, diaChi, dienThoai, loaiKH, ngayMua, khuyenMai, gioiTinh;
    public static String PATH = "/D3HStore/Bill_Online/bill_online.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_online_detail);

        ten = (TextView) findViewById(R.id.ten);
        email = (TextView) findViewById(R.id.email);
        diaChi = (TextView) findViewById(R.id.diaChi);
        dienThoai = (TextView) findViewById(R.id.dienThoai);
        loaiKH = (TextView) findViewById(R.id.loaiKH);
        ngayMua = (TextView) findViewById(R.id.ngayMua);
        khuyenMai = (TextView) findViewById(R.id.khuyenMai);
        gioiTinh = (TextView) findViewById(R.id.gioiTinh);

        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            BillOnline billOnline = readJsonFile(PATH, intent.getIntExtra("pos", 0));
            if (billOnline != null) {
                ten.setText(billOnline.TenKH);
                email.setText(billOnline.Email);
                diaChi.setText(billOnline.DiaChi);
                dienThoai.setText(billOnline.DienThoai);
                loaiKH.setText(billOnline.LoaiKH);
                ngayMua.setText(billOnline.NgayMuaHang);
                khuyenMai.setText(billOnline.KhuyenMai  +"");
                gioiTinh.setText(billOnline.GioiTinh);
            } else {
                ten.setText("Unknown");
                email.setText("Unknown");
                diaChi.setText("Unknown");
                dienThoai.setText("Unknown");
                loaiKH.setText("Unknown");
                ngayMua.setText("Unknown");
                khuyenMai.setText("Unknown");
                gioiTinh.setText("Unknown");
            }
        }
    }

    private BillOnline readJsonFile(String path, int pos) {
        BillOnline billOnline = null;
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

                JSONObject c = data.getJSONObject(pos);
                Log.e("FILE_OBJECT", c.toString());
                Gson gson = new Gson();

                 billOnline = gson.fromJson(c.toString(), BillOnline.class);
                return  billOnline;
            } else {
                Log.e("FILE", "Không tìm thấy file dữ liệu");
            }
            return  billOnline;
        } catch (Exception e) {
            return  billOnline;
        }
    }
}
