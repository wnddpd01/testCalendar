package com.cal.uj.testcalendar;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<CharSequence> tabTitleArray= new ArrayList<CharSequence>();
    private int tabCount;
    private SingtonResources singtonResources;
    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
        this.tabCount = 3;
        tabTitleArray.add("Monthly");
        tabTitleArray.add("Weekly");
        tabTitleArray.add("Daily");
        singtonResources = SingtonResources.getInstance();
    }

    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position){
            case 0:
                MontlyFragment montlyFragment = new MontlyFragment();
                return montlyFragment;
            case 1:
                WeeklyFragment weeklyFragment = new WeeklyFragment();
                return weeklyFragment;
            case 2:
                DailyFragment dailyFragment = new DailyFragment();
                return dailyFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitleArray.get(position);
    }
}