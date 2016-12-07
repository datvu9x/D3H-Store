package dev.datvt.clothingstored3h.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import dev.datvt.clothingstored3h.fragments.FragmentAllCustomer;
import dev.datvt.clothingstored3h.fragments.FragmentCustomerNo;
import dev.datvt.clothingstored3h.fragments.FragmentProductOffline;
import dev.datvt.clothingstored3h.fragments.FragmentProductOnline;

/**
 * Created by datvt on 7/31/2016.
 */
public class MyCustomerPagerAdapter extends FragmentStatePagerAdapter {

    final int TAB_COUNT = 2;
    private Fragment fr;

    public MyCustomerPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                fr = new FragmentAllCustomer();
                break;
            case 1:
                fr = new FragmentCustomerNo();
                break;
            default:
                break;
        }
        return fr;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }
}
