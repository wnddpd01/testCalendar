package com.cal.uj.testcalendar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

public class MonthlyGridAdapter extends BaseAdapter {
    private final List<String> list;
    private final LayoutInflater inflater;
    private boolean FlagChangeTextColor;
    private int mDayNum;
    private SingtonResources singtonResources;
    /**
     * @param context
     * @param list
     */
    public MonthlyGridAdapter(Context context, List<String> list) {
        this.list = list;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        singtonResources = SingtonResources.getInstance();
    }
    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public String getItem(int position) {
        return list.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.month_item_gridview, parent, false);
            holder = new ViewHolder();
            holder.tvItemGridView = convertView.findViewById(R.id.month_gridview_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.tvItemGridView.setText("" + getItem(position));
        if(singtonResources.int_curYear == singtonResources.intYear && singtonResources.int_curMonth == singtonResources.intMonth) {
            String sToday = singtonResources.singleCurDay.format(singtonResources.singleDate);
            if (sToday.equals(getItem(position))) { //change today text color
                holder.tvItemGridView.setTextColor(convertView.getResources().getColor(R.color.colorSky, convertView.getContext().getTheme()));
                mDayNum = position;
                FlagChangeTextColor = true;
            }
        }
        else{
            if(FlagChangeTextColor = true && position == mDayNum){
                holder.tvItemGridView.setTextColor(convertView.getResources().getColor(R.color.colorPrimaryDark, convertView.getContext().getTheme()));
                FlagChangeTextColor = false;
            }
        }

        return convertView;
    }

    private class ViewHolder {
        TextView tvItemGridView;
    }
}
