package com.cal.uj.testcalendar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WeeklyFragment extends Fragment {
    private TextView tvDate;
    private GridView weeklyGridView;
    private WeeklyGridAdapter weeklyGridAdapter;
    private Calendar mCal;
    private List<String> dayList;
    private ArrayList<String> WeekDayList;
    private SingtonResources mSingleton;
    private ImageButton btnPrevWeek;
    private ImageButton btnNextWeek;
    private int weekNum = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_weekly_fragment, container, false);
        super.onCreate(savedInstanceState);
        WeekDayList = new ArrayList<String>();
        mSingleton = SingtonResources.getInstance();
        tvDate = v.findViewById(R.id.week_tv_date);
        weeklyGridView = v.findViewById(R.id.gridview_week);
        btnNextWeek = v.findViewById(R.id.btn_nextWeek);
        btnPrevWeek = v.findViewById(R.id.btn_prevWeek);
        mCal = mSingleton.singleCalendar;

        dayList = mSingleton.singleDays;
        mSingleton.setCalendarDate(mSingleton.intMonth - 1);
        tvDate.setText(Integer.toString(mSingleton.intYear) + "년 - " + Integer.toString(mSingleton.intMonth) + "월  " + Integer.toString((mSingleton.intDay / 7) + 1) + "번째 주");

        if (mSingleton.getSingleWeeklyGridAdapter() == null) {
            int j = 0;
            for(int i = 0; i < 49; i++){
                if(i % 7 == 0){
                    WeekDayList.add(dayList.get(j));
                }
                else if(i % 7 == 1){
                    WeekDayList.add(dayList.get(j + 7 + (weekNum * 7)));
                    j++;
                }
                else{
                    this.WeekDayList.add("");
                }
            }
            mSingleton.setSingleWeeklyGridAdapter(inflater.getContext(), WeekDayList);
            weeklyGridAdapter = mSingleton.getSingleWeeklyGridAdapter();
        } else {
            weeklyGridAdapter = mSingleton.getSingleWeeklyGridAdapter();
        }
        weeklyGridView.setAdapter(weeklyGridAdapter);

        btnNextWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeekDayList.clear();
                mSingleton.intDay += 7;
                weekNum++;
                if(mSingleton.intDay > mSingleton.MonthLastDay){
                    mSingleton.intDay = 1;
                    if(mSingleton.intMonth == 12){
                        mSingleton.intYear += 1;
                        mSingleton.intMonth = 1;
                    }
                    else{
                        mSingleton.intMonth += 1;
                    }
                    mSingleton.setCalendarDate(mSingleton.intMonth - 1);
                    weekNum = 0;
                }
                int j = 0;
                for(int i = 0; i < 49; i++){
                    if(i % 7 == 0){
                        WeekDayList.add(dayList.get(j));
                    }
                    else if(i % 7 == 1){
                        WeekDayList.add(dayList.get(j + 7 + (weekNum * 7)));
                        j++;
                    }
                    else{
                        WeekDayList.add("");
                    }
                }
                tvDate.setText(Integer.toString(mSingleton.intYear) + "년 - " + Integer.toString(mSingleton.intMonth) + "월  " + Integer.toString((weekNum + 1)) + "번째 주");
                mSingleton.getSingleWeeklyGridAdapter().notifyDataSetChanged();
            }
        });

        btnPrevWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeekDayList.clear();
                mSingleton.intDay -= 7;
                weekNum--;
                if(mSingleton.intDay < 1){
                    mSingleton.intDay = 1;
                    if(mSingleton.intMonth == 1){
                        mSingleton.intYear -= 1;
                        mSingleton.intMonth = 12;
                    }
                    else{
                        mSingleton.intMonth -= 1;
                    }
                    mSingleton.setCalendarDate(mSingleton.intMonth - 1);
                    weekNum = mSingleton.MonthLastDay / 7;
                    mSingleton.intDay = 1 + (7 * weekNum);
                }
                int j = 0;
                for(int i = 0; i < 49; i++){
                    if(i % 7 == 0){
                        WeekDayList.add(dayList.get(j));
                    }
                    else if(i % 7 == 1){
                        WeekDayList.add(dayList.get(j + 7 + (weekNum * 7)));
                        j++;
                    }
                    else{
                        WeekDayList.add("");
                    }
                }
                tvDate.setText(Integer.toString(mSingleton.intYear) + "년 - " + Integer.toString(mSingleton.intMonth) + "월  " + Integer.toString((weekNum + 1)) + "번째 주");
                mSingleton.getSingleWeeklyGridAdapter().notifyDataSetChanged();
            }
        });
        return v;

    }


}
