package dev.datvt.clothingstored3h.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import dev.datvt.clothingstored3h.fragments.FragmentProductOffline;
import dev.datvt.clothingstored3h.fragments.FragmentProductOnline;

/**
 * Created by datvt on 7/31/2016.
 */
public class MyViewPagerAdapter extends FragmentStatePagerAdapter {

    final int TAB_COUNT = 2;
    private Fragment fr;

    public MyViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                fr = new FragmentProductOffline();
                break;
            case 1:
                fr = new FragmentProductOnline();
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
