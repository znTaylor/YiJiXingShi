package com.yjxs.zn.yijixingshi.fragment;


import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.yjxs.zn.yijixingshi.R;


public class YearPlanMonthDetailOneFragment extends Fragment {

    public static YearPlanMonthDetailOneFragment newInstance() {
        YearPlanMonthDetailOneFragment fragment = new YearPlanMonthDetailOneFragment();
        return fragment;
    }

    public YearPlanMonthDetailOneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_year_plan_part_1,container,false);


        return v;
    }




}
