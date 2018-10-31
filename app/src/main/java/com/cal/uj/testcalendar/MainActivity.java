package com.cal.uj.testcalendar;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private SharedPreferences pref;
    private SharedPreferences.Editor prefEditor;
    private SingtonResources mSingleton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        pref = getSharedPreferences("pref",MODE_PRIVATE);
        prefEditor = pref.edit();
        mTabLayout = (TabLayout)findViewById(R.id.tabLayout);

        mViewPager = (ViewPager)findViewById(R.id.vpMainActivity);
        mViewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager()));
        mViewPager.setOffscreenPageLimit(3);
        int pageNum = pref.getInt("pageNum", -1);

        if(pageNum == -1) {
            mViewPager.setCurrentItem(0);
        }
        else{
            mViewPager.setCurrentItem(pageNum);
        }
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void onPause() {
        super.onPause();
        prefEditor.putInt("pageNum",mViewPager.getCurrentItem());
        prefEditor.commit();
        prefEditor.apply();
    }
}
