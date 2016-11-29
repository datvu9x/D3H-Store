package dev.datvt.clothingstored3h.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dev.datvt.clothingstored3h.R;
import dev.datvt.clothingstored3h.activities.CreateBill;
import dev.datvt.clothingstored3h.utils.ConstantHelper;

/**
 * Created by DatVIT on 10/16/2016.
 */

public class FragmentProductOffline extends Fragment implements View.OnClickListener {

    private View viewFragment;
    private TextView btnCreateProductTicket;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewFragment = inflater.inflate(R.layout.fragment_product_ticket_offline, container, false);

        btnCreateProductTicket = (TextView) viewFragment.findViewById(R.id.btnCreateProductTicket);
        btnCreateProductTicket.setOnClickListener(this);

        return viewFragment;
    }

    @Override
    public void onClick(View v) {
        if (v == btnCreateProductTicket) {
            Intent intent = new Intent(getActivity(), CreateBill.class);
            startActivityForResult(intent, ConstantHelper.FRAGMENT_CREATE_BILL);
        }
    }
}
