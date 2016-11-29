package dev.datvt.clothingstored3h.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import dev.datvt.clothingstored3h.R;

/**
 * Created by DatVIT on 10/16/2016.
 */

public class FragmentTransferShift extends Fragment implements View.OnClickListener {

    private View viewFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewFragment = inflater.inflate(R.layout.fragment_transfer_shift, container, false);


        return viewFragment;
    }

    @Override
    public void onClick(View v) {

    }
}
