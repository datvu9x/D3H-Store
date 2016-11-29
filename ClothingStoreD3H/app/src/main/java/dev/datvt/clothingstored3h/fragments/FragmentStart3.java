package dev.datvt.clothingstored3h.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.datvt.clothingstored3h.R;

/**
 * Created by DatVIT on 10/13/2016.
 */

public class FragmentStart3 extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.start3_fragment, container, false);
        return view;
    }
}
