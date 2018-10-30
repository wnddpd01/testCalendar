package com.cal.uj.testcalendar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SingtonResources {
    public Calendar singleCalendar;
    public SimpleDateFormat singleCurYear;
    public SimpleDateFormat singleCurMonth;
    public SimpleDateFormat singleCurDay;
    public int int_curYear;
    public int int_curMonth;
    public int intYear;
    public int intMonth;
    public int intDay;
    public Date singleDate;
    private MonthlyGridAdapter singleMonthlyGridAdapter = null;
    private static SingtonResources singtonResources;
    private SingtonResources(){
        long now = System.currentTimeMillis();
        singleDate = new Date(now);

        singleCurYear = new SimpleDateFormat("yyy", Locale.KOREA);
        singleCurMonth = new SimpleDateFormat("MM", Locale.KOREA);
        singleCurDay = new SimpleDateFormat("dd", Locale.KOREA);
        singleCalendar = Calendar.getInstance();
        intYear = Integer.parseInt(singleCurYear.format(singleDate));
        int_curYear = intYear;
        intMonth = Integer.parseInt(singleCurMonth.format(singleDate));
        int_curMonth = intMonth;
        intDay = Integer.parseInt(singleCurDay.format(singleDate));
        singleCalendar.set(intYear, intMonth - 1, 1);
    }
    public static synchronized SingtonResources getInstance(){
        if(singtonResources == null){
            singtonResources = new SingtonResources();
        }
        return singtonResources;
    }

    public void setSingleMonthlyGridAdapter(Context context, ArrayList<String> dayList){
        singleMonthlyGridAdapter = new MonthlyGridAdapter(context, dayList);
    }

    public MonthlyGridAdapter getSingleMonthlyGridAdapter(){
        return singleMonthlyGridAdapter;
    }
}
