package dev.datvt.clothingstored3h.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import dev.datvt.clothingstored3h.R;
import dev.datvt.clothingstored3h.adapters.MyViewPagerAdapter;

/**
 * Created by DatVIT on 10/27/2016.
 */

public class BillActivity extends RootActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private MyViewPagerAdapter myViewPagerAdapter;

    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_fragment);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBlack));
        }

        mViewPager = (ViewPager) findViewById(R.id.pager);
        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myViewPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.custom_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.custom_online_shop);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mViewPager.setCurrentItem(0);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnBack) {
            finish();
        }
    }
}
