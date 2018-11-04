package com.cal.uj.testcalendar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class DailyFragment extends Fragment {
    public TextView tvDate;
    public TextView tvSchedule;
    public SingtonResources mSingleton;
    public ImageButton btnNextDay;
    public ImageButton btnPrevDay;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_daily_fragment,container,false);
        super.onCreate(savedInstanceState);
        mSingleton = SingtonResources.getInstance();
        tvDate = v.findViewById(R.id.day_tv_date);
        tvSchedule = v.findViewById(R.id.tv_day_schedule);
        mSingleton.tv_day_schedule = tvSchedule;
        mSingleton.tv_day_date = tvDate;
        btnNextDay = v.findViewById(R.id.btn_nextDay);
        btnPrevDay = v.findViewById(R.id.btn_prevDay);

        mSingleton.intYear = mSingleton.int_curYear;
        mSingleton.setCalendarDate(mSingleton.int_curMonth - 1);

        mSingleton.day_intDay = mSingleton.int_curDay;
        tvDate.setText(Integer.toString(mSingleton.intYear) + "년 - " + Integer.toString(mSingleton.intMonth) + "월  " + Integer.toString(mSingleton.day_intDay) + "일");
        String[] days = {"일", "월", "화", "수", "목", "금", "토"};
        tvDate.append("  " + days[(mSingleton.day_intDay + mSingleton.MonthStartDayNum - 2) % 7] + "요일" + " (오늘)");
        Integer Key = mSingleton.intYear * 10000 + mSingleton.intMonth * 100 + mSingleton.day_intDay;
        if(mSingleton.hashMap.containsKey(Key) == true){
            tvSchedule.setText(mSingleton.hashMap.get(Key));
        }
        btnNextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSingleton.day_intDay += 1;
                if(mSingleton.day_intDay > mSingleton.MonthLastDay){
                    if(mSingleton.intMonth == 12){
                        mSingleton.intYear += 1;
                        mSingleton.intMonth = 1;
                    }
                    else{
                        mSingleton.intMonth += 1;
                    }
                    mSingleton.setCalendarDate(mSingleton.intMonth - 1);
                    mSingleton.day_intDay = 1;
                }
                tvDate.setText(Integer.toString(mSingleton.intYear) + "년 - " + Integer.toString(mSingleton.intMonth) + "월  " + Integer.toString(mSingleton.day_intDay) + "일");

                String[] days = {"일", "월", "화", "수", "목", "금", "토"};
                tvDate.append("  " + days[(mSingleton.day_intDay + mSingleton.MonthStartDayNum - 2) % 7] + "요일");
                if(mSingleton.intYear == mSingleton.int_curYear && mSingleton.intMonth == mSingleton.int_curMonth && mSingleton.int_curDay == mSingleton.day_intDay){
                    tvDate.append(" " + "(오늘)");
                }
                Integer Key = mSingleton.intYear * 10000 + mSingleton.intMonth * 100 + mSingleton.day_intDay;
                if(mSingleton.hashMap.containsKey(Key) == true){
                    tvSchedule.setText(mSingleton.hashMap.get(Key));
                }
                else{
                    tvSchedule.setText("");
                }
            }
        });
        btnPrevDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSingleton.day_intDay -= 1;
                if(mSingleton.day_intDay < 1){
                    if(mSingleton.intMonth == 1){
                        mSingleton.intYear -= 1;
                        mSingleton.intMonth = 12;
                    }
                    else{
                        mSingleton.intMonth -= 1;
                    }
                    mSingleton.setCalendarDate(mSingleton.intMonth - 1);
                    mSingleton.day_intDay = mSingleton.MonthLastDay;
                }
                tvDate.setText(Integer.toString(mSingleton.intYear) + "년 - " + Integer.toString(mSingleton.intMonth) + "월  " + Integer.toString(mSingleton.day_intDay) + "일");

                String[] days = {"일", "월", "화", "수", "목", "금", "토"};
                tvDate.append("  " + days[(mSingleton.day_intDay + mSingleton.MonthStartDayNum - 2) % 7] + "요일");
                if(mSingleton.intYear == mSingleton.int_curYear && mSingleton.intMonth == mSingleton.int_curMonth && mSingleton.int_curDay == mSingleton.day_intDay){
                    tvDate.append(" " + "(오늘)");
                }
                Integer Key = mSingleton.intYear * 10000 + mSingleton.intMonth * 100 + mSingleton.day_intDay;
                if(mSingleton.hashMap.containsKey(Key) == true){
                    tvSchedule.setText(mSingleton.hashMap.get(Key));
                }
                else{
                    tvSchedule.setText("");
                }
            }
        });

        return v;
    }
}
