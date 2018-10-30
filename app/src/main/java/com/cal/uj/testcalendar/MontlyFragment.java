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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
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
    private Calendar mCal;
    private ArrayList<String> dayList;
    private SingtonResources mSingleton;
    private Button btnPrevMonth;
    private Button btnNextMonth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_monthly_fragment,container,false);
        super.onCreate(savedInstanceState);

        tvDate = v.findViewById(R.id.month_tv_date);
        monthlyGridView = v.findViewById(R.id.gridview);
        btnNextMonth = v.findViewById(R.id.btn_nextMonth);
        btnPrevMonth = v.findViewById(R.id.btn_prevMonth);
        mSingleton = SingtonResources.getInstance();

        dayList = new ArrayList<String>();

        mCal = mSingleton.singleCalendar;

        tvDate.setText(Integer.toString(mSingleton.intYear) + "년 - " + Integer.toString(mSingleton.intMonth) + "월");

        if(mSingleton.getSingleMonthlyGridAdapter() == null) {
            mSingleton.setSingleMonthlyGridAdapter(inflater.getContext(), dayList);
            monthlyGridAdapter = mSingleton.getSingleMonthlyGridAdapter();
        }
        else{
            monthlyGridAdapter = mSingleton.getSingleMonthlyGridAdapter();
        }

        monthlyGridView.setAdapter(monthlyGridAdapter);
        setCalendarDate(mSingleton.intMonth - 1);
        btnNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSingleton.intMonth += 1;
                if(mSingleton.intMonth == 13){
                    mSingleton.intMonth = 1;
                    mSingleton.intYear += 1;
                    mSingleton.singleCalendar.set(Calendar.YEAR, mSingleton.intYear);
                }
                setCalendarDate(mSingleton.intMonth - 1);
                tvDate.setText(Integer.toString(mSingleton.intYear) + "년 - " + Integer.toString(mSingleton.intMonth) + "월");
                mSingleton.getSingleMonthlyGridAdapter().notifyDataSetChanged();
            }
        });
        btnPrevMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSingleton.intMonth -= 1;
                if(mSingleton.intMonth == 0){
                    mSingleton.intMonth = 12;
                    mSingleton.intYear -= 1;
                    mSingleton.singleCalendar.set(Calendar.YEAR, mSingleton.intYear);
                }
                setCalendarDate(mSingleton.intMonth - 1);
                tvDate.setText(Integer.toString(mSingleton.intYear) + "년 - " + Integer.toString(mSingleton.intMonth) + "월");
                mSingleton.getSingleMonthlyGridAdapter().notifyDataSetChanged();
            }
        });
        return v;
    }

    private void setCalendarDate(int month) {
        mCal.set(Calendar.MONTH, month);
        dayList.clear();
        int dayNum = mCal.get(Calendar.DAY_OF_WEEK);
        dayList.add("일");
        dayList.add("월");
        dayList.add("화");
        dayList.add("수");
        dayList.add("목");
        dayList.add("금");
        dayList.add("토");
        for(int i = 1; i < dayNum; i++){
            dayList.add("");
        }
        for (int i = 0; i < mCal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            dayList.add("" + (i + 1));
        }
    }
}
