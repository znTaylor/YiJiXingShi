package com.yjxs.zn.yijixingshi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yjxs.zn.yijixingshi.R;

import java.util.List;

/**
 * Created by Taylor on 2015/8/22.
 */
public class MonthPlanDetailOfCurrYearAdapter extends ArrayAdapter<String> {
    private  Context context;
    private int resourceId;
    private List<String> planDetail;
    public MonthPlanDetailOfCurrYearAdapter(Context context, int resourceId, List<String> detail) {
        super(context, resourceId, detail);
        this.context = context;
        this.resourceId = resourceId;
        this.planDetail = detail;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout linear;
        if (convertView == null){
            linear = new LinearLayout(context);
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(resourceId, linear, true);
        } else{
            linear = (LinearLayout)convertView;
        }

        TextView monthIndex = (TextView) linear.findViewById(R.id.month_index);
        monthIndex.setText(""+(position+1));
        TextView monthTitle = (TextView) linear.findViewById(R.id.month_title);
        monthTitle.setText(planDetail.get(position));


        return linear;
    }
}
