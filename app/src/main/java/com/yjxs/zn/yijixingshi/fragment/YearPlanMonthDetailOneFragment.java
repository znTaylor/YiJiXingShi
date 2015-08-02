package com.yjxs.zn.yijixingshi.fragment;


import android.app.Fragment;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import com.yjxs.zn.yijixingshi.R;
import com.yjxs.zn.yijixingshi.YearPlanMakingActivity;

import java.util.Calendar;


public class YearPlanMonthDetailOneFragment extends Fragment {

    private EditText planJanuary,planFebruary,planMarch,planApril;
    private Button savePlanOne;

    private String editDisabledText = "当前月份不能编辑计划";

    private  int curMonth,currDay;

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
        View v = inflater.inflate(R.layout.layout_year_plan_part_1,container, false);
        initControls(v);

        return v;
    }

    private void initControls(View v){
        planJanuary = (EditText) v.findViewById(R.id.yea_plan_month_1);
        planFebruary = (EditText) v.findViewById(R.id.yea_plan_month_2);
        planMarch = (EditText) v.findViewById(R.id.yea_plan_month_3);
        planApril = (EditText) v.findViewById(R.id.yea_plan_month_4);
        savePlanOne = (Button) v.findViewById(R.id.year_plan_1_ok);



        //根据当前月份判断某月份是否可以制定计划，如果已经过了该月份，则该月份不能制定计划
        Calendar calendar = Calendar.getInstance();
        curMonth = calendar.get(Calendar.MONTH) + 1;
        currDay = calendar.get(Calendar.DAY_OF_MONTH);

        if (curMonth >4){
            planJanuary.setEnabled(false);
            planJanuary.setHint(editDisabledText);
            planFebruary.setEnabled(false);
            planFebruary.setHint(editDisabledText);
            planMarch.setEnabled(false);
            planMarch.setHint(editDisabledText);
            planApril.setEnabled(false);
            planApril.setHint(editDisabledText);

            for (int i=0; i<4; i++){
                YearPlanMakingActivity.monthEditable[i] = false;
            }
        } else if (curMonth == 4 ){
            planJanuary.setEnabled(false);
            planJanuary.setHint(editDisabledText);
            planFebruary.setEnabled(false);
            planFebruary.setHint(editDisabledText);
            planMarch.setEnabled(false);
            planMarch.setHint(editDisabledText);

            for (int i=0; i<3; i++){
                YearPlanMakingActivity.monthEditable[i] = false;
            }

            if (currDay > 5){
                planApril.setEnabled(false);
                planApril.setHint(editDisabledText);
                YearPlanMakingActivity.monthEditable[3] = false;
            }
        }  else if (curMonth == 3 ){
            planJanuary.setEnabled(false);
            planJanuary.setHint(editDisabledText);
            planFebruary.setEnabled(false);
            planFebruary.setHint(editDisabledText);
            for (int i=0; i<2; i++){
                YearPlanMakingActivity.monthEditable[i] = false;
            }
            if (currDay > 5) {
                planMarch.setEnabled(false);
                planMarch.setHint(editDisabledText);
                YearPlanMakingActivity.monthEditable[2] = false;
            }
        } else if (curMonth == 2){
            planJanuary.setEnabled(false);
            planJanuary.setHint(editDisabledText);
            YearPlanMakingActivity.monthEditable[0] = false;
            if (currDay > 5) {
                planFebruary.setEnabled(false);
                planFebruary.setHint(editDisabledText);
                YearPlanMakingActivity.monthEditable[1] = false;
            }
        } else if (curMonth == 1){
            if (currDay > 5) {
                planJanuary.setEnabled(false);
                planJanuary.setHint(editDisabledText);
                YearPlanMakingActivity.monthEditable[0] = false;
            }
        }

        savePlanOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YearPlanMakingActivity.isCurrPartPlanSaved[0] = true;

                YearPlanMakingActivity.yearPlanMonthDetail[0] = planJanuary.getText().toString();
                YearPlanMakingActivity.yearPlanMonthDetail[1] = planFebruary.getText().toString();
                YearPlanMakingActivity.yearPlanMonthDetail[2] = planMarch.getText().toString();
                YearPlanMakingActivity.yearPlanMonthDetail[3] = planApril.getText().toString();

            }
        });
    }

}
