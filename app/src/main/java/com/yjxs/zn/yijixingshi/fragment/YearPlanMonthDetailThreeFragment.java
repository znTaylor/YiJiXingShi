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


public class YearPlanMonthDetailThreeFragment extends Fragment {

    public static String TAG = "YearPlanMonthDetailThreeFragment";

    private EditText planSeptember,planOctober,plaNovember,planDecember;
    private Button savePlanThree;

    private String editDisabledText = "当前月份不能编辑计划";

    private  int curMonth,currDay;

    public static YearPlanMonthDetailThreeFragment newInstance() {
        YearPlanMonthDetailThreeFragment fragment = new YearPlanMonthDetailThreeFragment();
        return fragment;
    }

    public YearPlanMonthDetailThreeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_year_plan_part_3,null);
        initControls(v);

        return v;
    }

    private void initControls(View v){
        planSeptember = (EditText) v.findViewById(R.id.yea_plan_month_9);
        planOctober = (EditText) v.findViewById(R.id.yea_plan_month_10);
        plaNovember = (EditText) v.findViewById(R.id.yea_plan_month_11);
        planDecember = (EditText) v.findViewById(R.id.yea_plan_month_12);
        savePlanThree = (Button) v.findViewById(R.id.year_plan_3_ok);

        //根据当前月份判断某月份是否可以制定计划，如果已经过了该月份，则该月份不能制定计划
        Calendar calendar = Calendar.getInstance();
        curMonth = calendar.get(Calendar.MONTH) + 1;
        currDay = calendar.get(Calendar.DAY_OF_MONTH);

        if (curMonth >12){
            planSeptember.setEnabled(false);
            planSeptember.setHint(editDisabledText);
            planOctober.setEnabled(false);
            planOctober.setHint(editDisabledText);
            plaNovember.setEnabled(false);
            plaNovember.setHint(editDisabledText);
            planDecember.setEnabled(false);
            planDecember.setHint(editDisabledText);

            for (int i=8; i<12; i++){
                YearPlanMakingActivity.monthEditable[i] = false;
            }
        } else if (curMonth == 12 ){
            planSeptember.setEnabled(false);
            planSeptember.setHint(editDisabledText);
            planOctober.setEnabled(false);
            planOctober.setHint(editDisabledText);
            plaNovember.setEnabled(false);
            plaNovember.setHint(editDisabledText);

            for (int i=8; i<11; i++){
                YearPlanMakingActivity.monthEditable[i] = false;
            }

            if (currDay > 5){
                planDecember.setEnabled(false);
                planDecember.setHint(editDisabledText);
                YearPlanMakingActivity.monthEditable[11] = false;
            }
        } else if (curMonth == 11 ){
            planSeptember.setEnabled(false);
            planSeptember.setHint(editDisabledText);
            planOctober.setEnabled(false);
            planOctober.setHint(editDisabledText);
            for (int i=8; i<10; i++){
                YearPlanMakingActivity.monthEditable[i] = false;
            }

            if (currDay > 5) {
                plaNovember.setEnabled(false);
                plaNovember.setHint(editDisabledText);
                YearPlanMakingActivity.monthEditable[10] = false;

            }
        } else if (curMonth == 10){
            planSeptember.setEnabled(false);
            planSeptember.setHint(editDisabledText);
            YearPlanMakingActivity.monthEditable[8] = false;
            if (currDay > 5) {
                planOctober.setEnabled(false);
                planOctober.setHint(editDisabledText);
                YearPlanMakingActivity.monthEditable[9] = false;
            }
        } else if (curMonth == 9){
            if (currDay > 5) {
                planSeptember.setEnabled(false);
                planSeptember.setHint(editDisabledText);
                YearPlanMakingActivity.monthEditable[8] = false;
            }
        }
        savePlanThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YearPlanMakingActivity.isCurrPartPlanSaved[2] = true;

                YearPlanMakingActivity.yearPlanMonthDetail[8] = planSeptember.getText().toString();
                YearPlanMakingActivity.yearPlanMonthDetail[9] = planOctober.getText().toString();
                YearPlanMakingActivity.yearPlanMonthDetail[10] = plaNovember.getText().toString();
                YearPlanMakingActivity.yearPlanMonthDetail[11] = planDecember.getText().toString();

                Log.d(TAG, "YearPlanMonthDetailThreeFragment_onSave,execute save operation");
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        if (YearPlanMakingActivity.isCurrPartPlanSaved[2]){
            YearPlanMakingActivity.isCurrPartPlanSaved[2] = true;

            YearPlanMakingActivity.yearPlanMonthDetail[8] = planSeptember.getText().toString();
            YearPlanMakingActivity.yearPlanMonthDetail[9] = planOctober.getText().toString();
            YearPlanMakingActivity.yearPlanMonthDetail[10] = plaNovember.getText().toString();
            YearPlanMakingActivity.yearPlanMonthDetail[11] = planDecember.getText().toString();

            Log.d(TAG, "YearPlanMonthDetailThreeFragment_onStop,execute save operation");
        }
    }
}
