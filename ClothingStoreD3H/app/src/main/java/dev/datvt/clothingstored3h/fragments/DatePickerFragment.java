package dev.datvt.clothingstored3h.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import dev.datvt.clothingstored3h.R;

/**
 * Created by DatVIT on 12/7/2016.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        EditText etDate = (EditText) getActivity().findViewById(R.id.etDate);
        int ngay = view.getDayOfMonth();
        int thang = view.getMonth() + 1;
        if (ngay < 10 && thang >= 10) {
            etDate.setText("0" + ngay + "/" + thang + "/" + view.getYear());
        } else if (ngay >= 10 && thang < 10) {
            etDate.setText(ngay + "/" + "0" + thang + "/" + view.getYear());
        } else if (ngay >= 10 && thang >= 10) {
            etDate.setText(ngay + "/" + thang + "/" + view.getYear());
        } else {
            etDate.setText("0" + ngay + "/" + "0" + thang + "/" + view.getYear());
        }
    }
}