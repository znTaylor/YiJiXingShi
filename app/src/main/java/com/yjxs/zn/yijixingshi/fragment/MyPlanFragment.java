package com.yjxs.zn.yijixingshi.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.yjxs.zn.yijixingshi.MainActivity;
import com.yjxs.zn.yijixingshi.R;

/**
 * Fragment of my plan options
 */
public class MyPlanFragment extends Fragment {

    private static final String TAG = "MyPlanFragment";
    private static MyPlanFragment instance = null;

    RelativeLayout layoutMakeYearPlan,layoutMakeMonthPlan,layoutMakeWeekPlan,layoutCustomPlan;

    public MyPlanFragment() {
    }

    public static MyPlanFragment getInstance(){
        if (instance == null){
            instance = new MyPlanFragment();
            return instance;
        }
        else {
            return instance;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_plan, container, false);
        init(v);
        return v;
    }

    /**
     * 初始化组件
     * @param v
     *     view
     * */
    private void init(View v){
        //进入年计划制定界面
        layoutMakeYearPlan = (RelativeLayout) v.findViewById(R.id.layout_make_year_plan);
        layoutMakeYearPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getInstance().setCurrentFragment(MakeYearPlanFragment.getInstance());
            }
        });
    }



}
