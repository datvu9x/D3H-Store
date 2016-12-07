package dev.datvt.clothingstored3h.fragments;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dev.datvt.clothingstored3h.R;
import dev.datvt.clothingstored3h.activities.MainActivity;
import dev.datvt.clothingstored3h.models.Customer;
import dev.datvt.clothingstored3h.models.Employee;
import dev.datvt.clothingstored3h.utils.DatabaseHandler;
import dev.datvt.clothingstored3h.utils.ToolsHelper;

/**
 * Created by DatVIT on 10/16/2016.
 */

public class FragmentTransferShift extends Fragment implements View.OnClickListener {

    private View viewFragment;
    private EditText ca, gioVao, gioKetThuc, thuNgan, nguoiGiao, quyDau, tienMat, tienChenhLech, quyCuoi, banGiao, ghichu;
    private TextView btnXuat, btnNhapLai;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private String FILE = Environment.getExternalStorageDirectory().toString()
            + "/D3HStore/" + "TransferShift" + System.currentTimeMillis() + ".pdf";
    private DatabaseHandler databaseHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewFragment = inflater.inflate(R.layout.fragment_transfer_shift, container, false);

        databaseHandler = new DatabaseHandler(getActivity());

        ca = (EditText) viewFragment.findViewById(R.id.tvCa);
        btnXuat = (TextView) viewFragment.findViewById(R.id.btnXuat);
        btnNhapLai = (TextView) viewFragment.findViewById(R.id.btnNhapLai);
        gioVao = (EditText) viewFragment.findViewById(R.id.gioVao);
        gioKetThuc = (EditText) viewFragment.findViewById(R.id.gioKetThuc);
        thuNgan = (EditText) viewFragment.findViewById(R.id.thuNgan);
        nguoiGiao = (EditText) viewFragment.findViewById(R.id.nguoiGiao);
        quyDau = (EditText) viewFragment.findViewById(R.id.quyDau);
        tienMat = (EditText) viewFragment.findViewById(R.id.tienmat);
        tienChenhLech = (EditText) viewFragment.findViewById(R.id.tienChenhLech);
        quyCuoi = (EditText) viewFragment.findViewById(R.id.tienQuyCuoi);
        banGiao = (EditText) viewFragment.findViewById(R.id.tienBanGiaoLai);
        ghichu = (EditText) viewFragment.findViewById(R.id.ghiChu);

        quyDau.setText(MainActivity.money + "");
        tienMat.setText(databaseHandler.getMoneySale(simpleDateFormat.format(new Date()), MainActivity.id) + "");
        Employee employee = databaseHandler.getEmployee(MainActivity.id);
        if (employee != null) {
            nguoiGiao.setText(employee.getName());
        }

        quyCuoi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (quyCuoi.getText() != null && !quyCuoi.getText().toString().isEmpty()) {
                    double t = Double.parseDouble(quyDau.getText().toString()) + Double.parseDouble(tienMat.getText().toString())
                            - Double.parseDouble(quyCuoi.getText().toString());
                    if (t < 0) {
                        t *= -1;
                    }
                    tienChenhLech.setText(t + "");
                }
            }
        });

        gioVao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v);
            }
        });

        gioKetThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v);
            }
        });

        btnXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chuyeCa();
            }
        });

        btnNhapLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetText();
            }
        });

        return viewFragment;
    }

    private void chuyeCa() {
        String _ca = ca.getText().toString();
        String _gioVao = gioVao.getText().toString();
        String gioKet = gioKetThuc.getText().toString();
        String _thuNgan = thuNgan.getText().toString();
        String _nguoiBanGiao = nguoiGiao.getText().toString();
        String _tienDau = quyDau.getText().toString();
        String _tienMat = tienMat.getText().toString();
        String _tienChenhLech = tienChenhLech.getText().toString();
        String _tienBanGiao = banGiao.getText().toString();
        String tienCuoi = quyCuoi.getText().toString();

        if (!isCheckNull(_ca) && !isCheckNull(_gioVao) && !isCheckNull(gioKet) && !isCheckNull(_thuNgan)
                && !isCheckNull(_nguoiBanGiao) && !isCheckNull(_tienDau) &&
                !isCheckNull(_tienMat) && !isCheckNull(_tienBanGiao) && !isCheckNull(_tienChenhLech)
                && !isCheckNull(tienCuoi)) {

            Log.e("FILE", FILE);
            Document document = new Document(PageSize.A4);
            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/D3HStore");
            if (!myDir.exists()) {
                myDir.mkdirs();
            }
            try {
                PdfWriter.getInstance(document, new FileOutputStream(FILE));
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            document.open();
            addMetaData(document);
            try {
                addTitlePage(document);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            document.close();

            Toast.makeText(getActivity(), "Xuất dữ liệu chuyển ca thành công", Toast.LENGTH_SHORT).show();
            resetText();
        } else {
            Toast.makeText(getActivity(), "Bạn chưa nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }
    }

    public void addMetaData(Document document) {
        document.addTitle("BÀN GIAO CA - NGÀY " + simpleDateFormat.format(new Date()));
        document.addSubject("Bàn giao ca");
        document.addKeywords("Bàn giao, Chuyển Ca");
        document.addAuthor(nguoiGiao.getText().toString());
        document.addCreator(nguoiGiao.getText().toString());
    }

    public void addTitlePage(Document document) throws DocumentException {
        BaseFont urName = null;
        BaseFont boldFont = null;
        try {
            urName = BaseFont.createFont("assets/fonts/bryantlg.ttf", "UTF-8", BaseFont.EMBEDDED);
            boldFont = BaseFont.createFont("assets/fonts/bryantlgb.ttf", "UTF-8", BaseFont.EMBEDDED);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Font titleFont = new Font(boldFont, 25);
        Font smallBold = new Font(boldFont, 20);
        Font normal = new Font(urName, 20);

        Paragraph prHead = new Paragraph();
        prHead.setFont(titleFont);
        prHead.add("BAN GIAO CA - NGAY: " + simpleDateFormat.format(new Date()) + "\n\n\n\n\n");
        prHead.setAlignment(Element.ALIGN_CENTER);
        PdfPTable myTable = new PdfPTable(1);
        myTable.setWidthPercentage(100.0f);
        PdfPCell myCell = new PdfPCell(new Paragraph(""));
        myCell.setBorder(Rectangle.BOTTOM);
        myTable.addCell(myCell);

        document.add(prHead);

        Paragraph prPersinalInfo = new Paragraph();
        prPersinalInfo.setFont(smallBold);
        prPersinalInfo.add("Ca:  " + ca.getText().toString() + "\n\n");
        prPersinalInfo.add("");
        prPersinalInfo.add("Gio vao ca:  " + gioVao.getText().toString() + "\n\n");
        prPersinalInfo.add("");
        prPersinalInfo.add("Gio ket thuc ca:  " + gioKetThuc.getText().toString() + "\n\n");
        prPersinalInfo.add("");
        prPersinalInfo.add("Thu ngan:  " + thuNgan.getText().toString() + "\n\n");
        prPersinalInfo.add("");
        prPersinalInfo.add("Nguoi ban giao:  " + nguoiGiao.getText().toString() + "\n\n");
        prPersinalInfo.add("");
        prPersinalInfo.add("Tien quy dau ca:  " + ToolsHelper.intToString((int) Math.round(Double.parseDouble(quyDau.getText().toString()))) + " $\n\n");
        prPersinalInfo.add("");
        prPersinalInfo.add("Tien mat da thu:  " + ToolsHelper.intToString((int) Math.round(Double.parseDouble(tienMat.getText().toString()))) + " $\n\n");
        prPersinalInfo.add("");
        prPersinalInfo.add("Tien chenh lech:  " + ToolsHelper.intToString((int) Math.round(Double.parseDouble(tienChenhLech.getText().toString()))) + " $\n\n");
        prPersinalInfo.add("");
        prPersinalInfo.add("Tien quy cuoi ca:  " + ToolsHelper.intToString((int) Math.round(Double.parseDouble(quyCuoi.getText().toString()))) + " $\n\n");
        prPersinalInfo.add("");
        prPersinalInfo.add("Tien ban giao lai:  " + ToolsHelper.intToString((int) Math.round(Double.parseDouble(banGiao.getText().toString()))) + " $\n\n");
        prPersinalInfo.add("");
        prPersinalInfo.add("Ghi chu:  " + ghichu.getText().toString() + "\n\n");
        prPersinalInfo.setAlignment(Element.ALIGN_LEFT);
        document.add(prPersinalInfo);

        Paragraph prProfile = new Paragraph();
        prProfile.setFont(smallBold);
        prProfile.add("\n\n\n\n Nguoi ban giao\n ");
        prProfile.setFont(normal);
        prProfile.add("\n" + nguoiGiao.getText().toString());
        prProfile.setFont(smallBold);
        prProfile.setAlignment(Element.ALIGN_RIGHT);
        document.add(prProfile);

        document.newPage();
    }

    @Override
    public void onClick(View v) {

    }

    private boolean isCheckNull(String s) {
        if (s.isEmpty()) {
            return true;
        }
        return false;
    }

    private void resetText() {
        ca.setText("");
        gioVao.setText("");
        gioKetThuc.setText("");
        thuNgan.setText("");
        nguoiGiao.setText("");
        quyDau.setText("");
        tienMat.setText("");
        tienChenhLech.setText("");
        quyCuoi.setText("");
        banGiao.setText("");
        ghichu.setText("");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = null;
        if (v == gioVao) {
            newFragment = new TimePickerFragment();
        } else if (v == gioKetThuc) {
            newFragment = new TimePickerFinalFragment();
        }
        newFragment.show(getActivity().getSupportFragmentManager(), "TimePicker");
    }
}
