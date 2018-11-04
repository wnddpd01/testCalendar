package com.cal.uj.testcalendar;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class WeeklyRecyclerAdapter extends RecyclerView.Adapter<WeeklyRecyclerAdapter.WeekViewHolder>{

    private final List<String> list;
    private SingtonResources singtonResources;
    private Context context;
    public class WeekViewHolder extends RecyclerView.ViewHolder{
        TextView tv_day;
        TextView tv_day_num;
        TextView tv_schedule;
        CardView v;
        public WeekViewHolder(View v){
            super(v);
            tv_day = (TextView)v.findViewById(R.id.tv_recycler_item_day);
            tv_day_num = v.findViewById(R.id.tv_recycler_item_daynum);
            tv_schedule = v.findViewById(R.id.tv_recycler_item_schedule);
            this.v = (CardView)v;
            this.v.setClickable(true);
            this.v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v2) {
                    int position = getAdapterPosition();
                    int lastDayPos = singtonResources.MonthLastDay + singtonResources.MonthStartDayNum - 1 ;
                    singtonResources.weekSelectedPosition = position;

                    if(singtonResources.intDay / 7 == 0 &&  position < singtonResources.MonthStartDayNum - 1){
                        if(singtonResources.intMonth == 1){
                            singtonResources.int_selected_year = singtonResources.intYear - 1;
                            singtonResources.int_selected_month = 12;
                        }
                        else{
                            singtonResources.int_selected_year = singtonResources.intYear;
                            singtonResources.int_selected_month = singtonResources.intMonth - 1;
                        }
                    }
                    else if(singtonResources.intDay / 7 == (singtonResources.MonthLastDay + singtonResources.MonthStartDayNum - 1)/ 7 && position > (lastDayPos % 7) - 1){
                        if(singtonResources.intMonth == 12){
                            singtonResources.int_selected_year = singtonResources.intYear + 1;
                            singtonResources.int_selected_month = 1;
                        }
                        else{
                            singtonResources.int_selected_year = singtonResources.intYear;
                            singtonResources.int_selected_month = singtonResources.intMonth + 1;
                        }
                    }
                    else{
                        singtonResources.int_selected_year = singtonResources.intYear;
                        singtonResources.int_selected_month = singtonResources.intMonth;
                    }
                    singtonResources.int_selected_day = Integer.parseInt(tv_day_num.getText().toString());
                    notifyDataSetChanged();
                }
            });
        }
    }

    public WeeklyRecyclerAdapter(List<String> list){
        singtonResources = SingtonResources.getInstance();
        this.list = list;
    }

    @NonNull
    @Override
    public WeekViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = (View)LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item, viewGroup, false);
        context = viewGroup.getContext();
        WeekViewHolder vh = new WeekViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(WeekViewHolder viewHolder, int position) {

        int lastDayPos = singtonResources.MonthLastDay + singtonResources.MonthStartDayNum - 1 ;

        if(position == singtonResources.weekSelectedPosition){
            viewHolder.v.setCardBackgroundColor(viewHolder.v.getResources().getColor(R.color.colorBack, viewHolder.v.getContext().getTheme()));
            singtonResources.tv_week_schedule = viewHolder.tv_schedule;
        }
        else{
            viewHolder.v.setCardBackgroundColor(viewHolder.v.getResources().getColor(R.color.colorBeige, viewHolder.v.getContext().getTheme()));
        }
        if(position < list.size() / 3) {

            viewHolder.tv_day.setText("" + list.get(position * 3));
            viewHolder.tv_day_num.setText("" + list.get((position * 3) + 1));
            viewHolder.tv_schedule.setText("");

            if(singtonResources.intDay / 7 == 0 && position < singtonResources.MonthStartDayNum - 1){
                viewHolder.tv_day.setTextColor(context.getResources().getColor(R.color.colorGrey, context.getTheme()));
                viewHolder.tv_day_num.setTextColor(context.getResources().getColor(R.color.colorGrey, context.getTheme()));
                viewHolder.tv_schedule.setTextColor(context.getResources().getColor(R.color.colorGrey, context.getTheme()));
                Integer Key;
                if(singtonResources.intMonth == 1){
                    Key = (singtonResources.intYear - 1) * 10000 + 12 * 100 + Integer.parseInt(viewHolder.tv_day_num.getText().toString());
                }
                else {
                    Key = singtonResources.intYear * 10000 + (singtonResources.intMonth - 1) * 100 + Integer.parseInt(viewHolder.tv_day_num.getText().toString());
                }
                if(singtonResources.hashMap.containsKey(Key)){
                    viewHolder.tv_schedule.setText(singtonResources.hashMap.get(Key));
                }
            }
            else if(singtonResources.intDay / 7 == (singtonResources.MonthLastDay + singtonResources.MonthStartDayNum - 1)/ 7 && position > (lastDayPos % 7) - 1){
                viewHolder.tv_day.setTextColor(context.getResources().getColor(R.color.colorGrey, context.getTheme()));
                viewHolder.tv_day_num.setTextColor(context.getResources().getColor(R.color.colorGrey, context.getTheme()));
                viewHolder.tv_schedule.setTextColor(context.getResources().getColor(R.color.colorGrey, context.getTheme()));
                Integer Key;
                if(singtonResources.intMonth == 12){
                    Key = (singtonResources.intYear + 1) * 10000 + 1 * 100 + Integer.parseInt(viewHolder.tv_day_num.getText().toString());
                }
                else{
                    Key = singtonResources.intYear * 10000 + (singtonResources.intMonth + 1) * 100 + Integer.parseInt(viewHolder.tv_day_num.getText().toString());
                }
                if(singtonResources.hashMap.containsKey(Key)){
                    viewHolder.tv_schedule.setText(singtonResources.hashMap.get(Key));
                }
            }

            else{
                viewHolder.tv_day.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark, context.getTheme()));
                if(singtonResources.intMonth == singtonResources.int_curMonth && singtonResources.intYear == singtonResources.int_curYear && (singtonResources.intDay  + 8 - singtonResources.MonthStartDayNum) / 7 == (singtonResources.int_curDay + 8 - singtonResources.MonthStartDayNum) / 7 && position == (singtonResources.int_curDay - 1 + singtonResources.MonthStartDayNum - 1) % 7){
                    viewHolder.tv_day_num.setTextColor(context.getResources().getColor(R.color.colorSky, context.getTheme()));
                }
                else {
                    viewHolder.tv_day_num.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark, context.getTheme()));
                }
                viewHolder.tv_schedule.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark, context.getTheme()));


                Integer Key = singtonResources.intYear * 10000 + singtonResources.intMonth * 100 + Integer.parseInt(viewHolder.tv_day_num.getText().toString());
                if(singtonResources.hashMap.containsKey(Key)){
                    viewHolder.tv_schedule.setText(singtonResources.hashMap.get(Key));
                }

            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size() / 3;
    }
}

