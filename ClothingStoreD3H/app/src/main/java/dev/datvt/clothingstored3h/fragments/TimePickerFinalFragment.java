package dev.datvt.clothingstored3h.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import dev.datvt.clothingstored3h.R;

/**
 * Created by DatVIT on 12/7/2016.
 */
public class TimePickerFinalFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        EditText gioKetThuc = (EditText) getActivity().findViewById(R.id.gioKetThuc);
        gioKetThuc.setText(view.getCurrentHour() + ":" + view.getCurrentMinute());

    }
}