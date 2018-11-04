package com.cal.uj.testcalendar;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MontlyFragment extends Fragment {
    private TextView tvDate;
    private GridView monthlyGridView;
    private MonthlyGridAdapter monthlyGridAdapter;
    private List<String> dayList;
    private SingtonResources mSingleton;
    private ImageButton btnPrevMonth;
    private ImageButton btnNextMonth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_monthly_fragment,container,false);
        super.onCreate(savedInstanceState);

        monthlyGridView = v.findViewById(R.id.gridview);
        btnNextMonth = v.findViewById(R.id.btn_nextMonth);
        btnPrevMonth = v.findViewById(R.id.btn_prevMonth);
        mSingleton = SingtonResources.getInstance();
        mSingleton.tv_month_date = v.findViewById(R.id.month_tv_date);
        tvDate = mSingleton.tv_month_date;
        dayList = mSingleton.singleDays;

        tvDate.setText(Integer.toString(mSingleton.intYear) + "년 - " + Integer.toString(mSingleton.intMonth) + "월");

        if(mSingleton.getSingleMonthlyGridAdapter() == null) {
            mSingleton.setSingleMonthlyGridAdapter(inflater.getContext(), dayList);
            monthlyGridAdapter = mSingleton.getSingleMonthlyGridAdapter();
        }
        else{
            monthlyGridAdapter = mSingleton.getSingleMonthlyGridAdapter();
        }
        monthlyGridView.setAdapter(monthlyGridAdapter);
        monthlyGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position > 6){
                    if(position - 6 < mSingleton.MonthStartDayNum){
                        if(mSingleton.intMonth == 1){
                            mSingleton.int_selected_year = mSingleton.intYear - 1;
                            mSingleton.int_selected_month = 12;
                        }
                        else{
                            mSingleton.int_selected_year = mSingleton.intYear;
                            mSingleton.int_selected_month = mSingleton.intMonth - 1;
                        }
                        mSingleton.int_selected_day = mSingleton.LastMonthLastDay + position - 7;
                    }
                    else if(position > mSingleton.MonthLastDay + mSingleton.MonthStartDayNum - 1 + 6){
                        if(mSingleton.intMonth == 12){
                            mSingleton.int_selected_month = 1;
                            mSingleton.int_selected_year = mSingleton.intYear + 1;
                        }
                        else{
                            mSingleton.int_selected_year = mSingleton.intYear;
                            mSingleton.int_selected_month = mSingleton.intMonth + 1;
                        }
                        mSingleton.int_selected_day = position - 5 - mSingleton.MonthLastDay - mSingleton.MonthStartDayNum;
                    }
                    else{
                        mSingleton.int_selected_year = mSingleton.intYear;
                        mSingleton.int_selected_month = mSingleton.intMonth;
                        mSingleton.int_selected_day = position - mSingleton.MonthStartDayNum - 5;
                    }
                    mSingleton.monthSelectedPosition = position;
                    monthlyGridAdapter.notifyDataSetChanged();
                }
                return;
            }
        });


        mSingleton.intYear = mSingleton.int_curYear;
        mSingleton.setCalendarDate(mSingleton.intMonth - 1);
        btnNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSingleton.int_selected_year = -1;
                mSingleton.monthSelectedPosition = -1;
                mSingleton.intMonth += 1;
                if(mSingleton.intMonth == 13){
                    mSingleton.intMonth = 1;
                    mSingleton.intYear += 1;
                }
                mSingleton.setCalendarDate(mSingleton.intMonth - 1);
                tvDate.setText(Integer.toString(mSingleton.intYear) + "년 - " + Integer.toString(mSingleton.intMonth) + "월");
                mSingleton.getSingleMonthlyGridAdapter().notifyDataSetChanged();
            }
        });
        btnPrevMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSingleton.int_selected_year = -1;
                mSingleton.monthSelectedPosition = -1;
                mSingleton.intMonth -= 1;
                if(mSingleton.intMonth == 0){
                    mSingleton.intMonth = 12;
                    mSingleton.intYear -= 1;
                }
                mSingleton.setCalendarDate(mSingleton.intMonth - 1);
                tvDate.setText(Integer.toString(mSingleton.intYear) + "년 - " + Integer.toString(mSingleton.intMonth) + "월");
                mSingleton.getSingleMonthlyGridAdapter().notifyDataSetChanged();
            }
        });
        return v;
    }

}
