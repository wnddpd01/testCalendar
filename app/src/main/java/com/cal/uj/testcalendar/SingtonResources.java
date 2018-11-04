package com.cal.uj.testcalendar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SingtonResources {
    public Calendar singleCalendar;
    public SimpleDateFormat singleCurYear;
    public SimpleDateFormat singleCurMonth;
    public SimpleDateFormat singleCurDay;
    public List<String> singleWeekDays;
    public List<String> singleDays;
    public TextView tv_month_date;
    public TextView tv_week_date;
    public TextView tv_day_date;
    public TextView tv_week_schedule;
    public TextView tv_day_schedule;
    public int int_selected_year;
    public int int_selected_month;
    public int int_selected_day;
    public int int_curYear;
    public int int_curMonth;
    public int int_curDay;
    public int intYear;
    public int intMonth;
    public int intDay;
    public int day_intDay;
    public int MonthLastDay;
    public int MonthStartDayNum;
    public int LastMonthLastDay;
    public Date singleDate;
    public int weekSelectedPosition;
    public int monthSelectedPosition;
    private MonthlyGridAdapter singleMonthlyGridAdapter = null;
    private WeeklyRecyclerAdapter singleWeeklyRecyclerAdapter = null;
    private static SingtonResources singtonResources;

    public DBHelper dbHelper;
    public SQLiteDatabase db;
    public Cursor cursor;
    public HashMap<Integer, String> hashMap;
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
        intDay = 1;
        int_curDay = Integer.parseInt(singleCurDay.format(singleDate));
        day_intDay = int_curDay;
        singleCalendar.set(intYear, intMonth - 1, 1);
        MonthLastDay = singleCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        singleWeekDays = new ArrayList<String>();
        singleDays = new ArrayList<String>();
        int_selected_year = -1;
        weekSelectedPosition = -1;
        monthSelectedPosition = -1;
        hashMap = new HashMap<Integer, String>();
    }
    public static synchronized SingtonResources getInstance(){
        if(singtonResources == null){
            singtonResources = new SingtonResources();
        }
        return singtonResources;
    }

    public void setSingleMonthlyGridAdapter(Context context, List<String> singleDays){
        singleMonthlyGridAdapter = new MonthlyGridAdapter(context, singleDays);
    }

    public MonthlyGridAdapter getSingleMonthlyGridAdapter(){
        return singleMonthlyGridAdapter;
    }

    public void setSingleWeeklyRecyclerAdapter(List<String> singleDays){
        singleWeekDays = singleDays;
        singleWeeklyRecyclerAdapter = new WeeklyRecyclerAdapter(singleDays);
    }

    public WeeklyRecyclerAdapter getSingleWeeklyRecyclerAdapter() { return singleWeeklyRecyclerAdapter; }

    public void setCalendarDate(int month) {
        singleCalendar.set(intYear, month - 1, 1);
        LastMonthLastDay = singleCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        singleCalendar.set(intYear, month, 1);
        int dayNum = singleCalendar.get(Calendar.DAY_OF_WEEK);
        MonthLastDay = singleCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        MonthStartDayNum = singtonResources.singleCalendar.get(Calendar.DAY_OF_WEEK);
        LastMonthLastDay -= (dayNum - 1) - 1;
        singleDays.clear();
        singleDays.add("일");
        singleDays.add("월");
        singleDays.add("화");
        singleDays.add("수");
        singleDays.add("목");
        singleDays.add("금");
        singleDays.add("토");
        for (int i = 1; i < dayNum; i++) {
            singleDays.add(Integer.toString(LastMonthLastDay + i - 1));
        }
        for (int i = 0; i < singleCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            singleDays.add("" + (i + 1));
        }
        for (int i = 0; singleDays.size() % 7 != 0; i++) {
            singleDays.add("" + (i + 1));
        }
    }
}
