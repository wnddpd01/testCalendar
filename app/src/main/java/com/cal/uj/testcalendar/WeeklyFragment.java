package com.cal.uj.testcalendar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
    private SingtonResources mSingleton;
    private ImageButton btnPrevWeek;
    private ImageButton btnNextWeek;

    private RecyclerView recyclerViewWeek;
    private RecyclerView.Adapter adapterWeek;
    private RecyclerView.LayoutManager layoutManagerWeek;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_weekly_fragment, container, false);
        super.onCreate(savedInstanceState);

        recyclerViewWeek = (RecyclerView)v.findViewById(R.id.recyclerview_week);
        recyclerViewWeek.setHasFixedSize(true);
        layoutManagerWeek = new GridLayoutManager(getActivity() , 7, GridLayoutManager.HORIZONTAL, false);
        recyclerViewWeek.setLayoutManager(layoutManagerWeek);

        mSingleton = SingtonResources.getInstance();
        mSingleton.tv_week_date = v.findViewById(R.id.week_tv_date);
        tvDate = mSingleton.tv_week_date;
        btnNextWeek = v.findViewById(R.id.btn_nextWeek);
        btnPrevWeek = v.findViewById(R.id.btn_prevWeek);
        mSingleton.intYear = mSingleton.int_curYear;
        mSingleton.setCalendarDate(mSingleton.int_curMonth - 1);
        mSingleton.intDay = 1 + ((mSingleton.int_curDay + mSingleton.MonthStartDayNum - 1) / 7) * 7;
        tvDate.setText(Integer.toString(mSingleton.intYear) + "년 - " + Integer.toString(mSingleton.intMonth) + "월  " + Integer.toString((mSingleton.intDay / 7) + 1) + "번째 주");

        if (mSingleton.getSingleWeeklyRecyclerAdapter() == null) {
            int j = 0;
            for(int i = 0; i < 7; i++){
                mSingleton.singleWeekDays.add(mSingleton.singleDays.get(i));
                mSingleton.singleWeekDays.add(mSingleton.singleDays.get(((mSingleton.intDay / 7 + 1) * 7)  + i));
                mSingleton.singleWeekDays.add("");
            }
            mSingleton.setSingleWeeklyRecyclerAdapter(mSingleton.singleWeekDays);
            adapterWeek = mSingleton.getSingleWeeklyRecyclerAdapter();
        } else {
            adapterWeek = mSingleton.getSingleWeeklyRecyclerAdapter();
        }
        recyclerViewWeek.setAdapter(adapterWeek);

        btnNextWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSingleton.weekSelectedPosition = -1;
                mSingleton.singleWeekDays.clear();
                mSingleton.intDay += 7;
                mSingleton.int_selected_year = -1;
                int weekNum = mSingleton.intDay / 7;
                if(mSingleton.intDay > mSingleton.MonthLastDay + mSingleton.MonthStartDayNum - 1){
                    mSingleton.intDay = 1;
                    if(mSingleton.intMonth == 12){
                        mSingleton.intYear += 1;
                        mSingleton.intMonth = 1;
                    }
                    else{
                        mSingleton.intMonth += 1;
                    }
                    mSingleton.setCalendarDate(mSingleton.intMonth - 1);
                    weekNum = mSingleton.intDay / 7;
                }
                for(int i = 0; i < 7; i++){
                    mSingleton.singleWeekDays.add(mSingleton.singleDays.get(i));
                    mSingleton.singleWeekDays.add(mSingleton.singleDays.get(((weekNum + 1) * 7)  + i));
                    mSingleton.singleWeekDays.add("");
                }
                tvDate.setText(Integer.toString(mSingleton.intYear) + "년 - " + Integer.toString(mSingleton.intMonth) + "월  " + Integer.toString((weekNum + 1)) + "번째 주");
                mSingleton.getSingleWeeklyRecyclerAdapter().notifyDataSetChanged();
            }
        });

        btnPrevWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSingleton.weekSelectedPosition = -1;
                mSingleton.singleWeekDays.clear();
                mSingleton.intDay -= 7;
                mSingleton.int_selected_year = -1;
                int weekNum = mSingleton.intDay / 7;
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
                    weekNum = (mSingleton.MonthLastDay + mSingleton.MonthStartDayNum - 2) / 7;
                    mSingleton.intDay = (weekNum * 7) + 1;
                }
                for(int i = 0; i < 7; i++){
                    mSingleton.singleWeekDays.add(mSingleton.singleDays.get(i));
                    mSingleton.singleWeekDays.add(mSingleton.singleDays.get(((weekNum + 1) * 7)  + i));
                    mSingleton.singleWeekDays.add("");
                }
                tvDate.setText(Integer.toString(mSingleton.intYear) + "년 - " + Integer.toString(mSingleton.intMonth) + "월  " + Integer.toString((weekNum + 1)) + "번째 주");
                mSingleton.getSingleWeeklyRecyclerAdapter().notifyDataSetChanged();
            }
        });
        return v;

    }


}
