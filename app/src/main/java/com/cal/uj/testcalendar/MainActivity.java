package com.cal.uj.testcalendar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private SharedPreferences pref;
    private SharedPreferences.Editor prefEditor;
    private SingtonResources singtonResources;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        singtonResources = SingtonResources.getInstance();

        singtonResources.dbHelper = new DBHelper(this);
        singtonResources.db = singtonResources.dbHelper.getWritableDatabase();
        Integer Key;
        singtonResources.cursor = singtonResources.db.rawQuery("SELECT * FROM schedule", null);
        while(singtonResources.cursor.moveToNext()) {
            Key = singtonResources.cursor.getInt(1);
            singtonResources.hashMap.put(Key, singtonResources.cursor.getString(2));
        }

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
        mTabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Integer Key = null;
                singtonResources.weekSelectedPosition = -1;
                singtonResources.monthSelectedPosition = -1;
                singtonResources.intYear = singtonResources.int_curYear;
                singtonResources.intMonth = singtonResources.int_curMonth;
                singtonResources.setCalendarDate(singtonResources.int_curMonth - 1);
                singtonResources.int_selected_year = -1;
                if(tab.getPosition() == 0){
                    singtonResources.getSingleMonthlyGridAdapter().notifyDataSetChanged();
                    singtonResources.tv_month_date.setText(Integer.toString(singtonResources.intYear) + "년 - " + Integer.toString(singtonResources.intMonth) + "월");
                }
                else if(tab.getPosition() == 1){
                    singtonResources.intDay = 1 + ((singtonResources.int_curDay - 1 + singtonResources.MonthStartDayNum - 1) / 7) * 7;
                    singtonResources.singleWeekDays.clear();
                    int j = 0;
                    for(int i = 0; i < 7; i++){
                        singtonResources.singleWeekDays.add(singtonResources.singleDays.get(i));
                        singtonResources.singleWeekDays.add(singtonResources.singleDays.get(((singtonResources.intDay / 7 + 1) * 7)  + i));
                        singtonResources.singleWeekDays.add("");
                    }
                    singtonResources.getSingleWeeklyRecyclerAdapter().notifyDataSetChanged();
                    singtonResources.tv_week_date.setText(Integer.toString(singtonResources.intYear) + "년 - " + Integer.toString(singtonResources.intMonth) + "월  " + Integer.toString((singtonResources.intDay / 7) + 1) + "번째 주");
                }
                else if(tab.getPosition() == 2){
                    singtonResources.day_intDay = singtonResources.int_curDay;
                    singtonResources.tv_day_date.setText(Integer.toString(singtonResources.intYear) + "년 - " + Integer.toString(singtonResources.intMonth) + "월  " + Integer.toString(singtonResources.day_intDay) + "일");
                    String[] days = {"일", "월", "화", "수", "목", "금", "토"};
                    singtonResources.tv_day_date.append("  " + days[(singtonResources.day_intDay + singtonResources.MonthStartDayNum - 2) % 7] + "요일" + " (오늘)");
                    Key = singtonResources.intYear * 10000 + singtonResources.intMonth * 100 + singtonResources.day_intDay;
                    if(singtonResources.hashMap.containsKey(Key) == true){
                        singtonResources.tv_day_schedule.setText(singtonResources.hashMap.get(Key));
                    }
                    else{
                        singtonResources.tv_day_schedule.setText("");
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public void mOnPopupClick(View v){
        String date = null;
        String schedule = null;
        Integer Key = null;
        int requestCode = 0;
        if(mTabLayout.getSelectedTabPosition() == 2){
            date = Integer.toString(singtonResources.intYear) + "년 - " + Integer.toString(singtonResources.intMonth) + "월 " + Integer.toString(singtonResources.day_intDay) + "일";
            Key = singtonResources.intYear * 10000 + singtonResources.intMonth * 100 + singtonResources.day_intDay;
            requestCode = 2;
        }
        else{
            if(singtonResources.int_selected_year == -1){
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();     //닫기
                    }
                });
                alert.setMessage("날짜를 선택해주세요");
                alert.show();
                return;
            }
            date = Integer.toString(singtonResources.int_selected_year) + "년 - " + Integer.toString(singtonResources.int_selected_month) + "월 " + Integer.toString(singtonResources.int_selected_day) + "일";
            Key = singtonResources.int_selected_year * 10000 + singtonResources.int_selected_month * 100 + singtonResources.int_selected_day;
            requestCode = 1;
        }
        schedule = singtonResources.hashMap.get(Key);
        Intent intent = new Intent(this, ScheduleActivity.class);
        intent.putExtra("date", date);
        intent.putExtra("schedule", schedule);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            Integer Key = null;
            String result = data.getStringExtra("re_schedule");
            if (result == null || result.isEmpty() == true || result.trim().length() == 0) {
                if (requestCode == 2) {
                    Key = singtonResources.intYear * 10000 + singtonResources.intMonth * 100 + singtonResources.day_intDay;
                    singtonResources.hashMap.remove(Key);
                    singtonResources.db.execSQL("DELETE FROM schedule WHERE date = '" + Key + "';");
                }
                else if(requestCode == 1){
                    Key = singtonResources.int_selected_year * 10000 + singtonResources.int_selected_month * 100 + singtonResources.int_selected_day;
                    singtonResources.hashMap.remove(Key);
                    singtonResources.db.execSQL("DELETE FROM schedule WHERE date = '" + Key + "';");
                }
            } else {
                if (requestCode == 2) {
                    Key = singtonResources.intYear * 10000 + singtonResources.intMonth * 100 + singtonResources.day_intDay;
                    singtonResources.hashMap.put(Key, result);
                    singtonResources.db.execSQL("INSERT INTO schedule VALUES (null, '" + Key + "', '" + result + "');");
                }
                else if(requestCode == 1){
                    Key = singtonResources.int_selected_year * 10000 + singtonResources.int_selected_month * 100 + singtonResources.int_selected_day;
                    singtonResources.hashMap.put(Key, result);
                    singtonResources.db.execSQL("INSERT INTO schedule VALUES (null, '" + Key + "', '" + result + "');");
                }
            }
            singtonResources.getSingleWeeklyRecyclerAdapter().notifyDataSetChanged();
            singtonResources.getSingleMonthlyGridAdapter().notifyDataSetChanged();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        prefEditor.putInt("pageNum",mViewPager.getCurrentItem());
        prefEditor.commit();
        prefEditor.apply();
    }
}
