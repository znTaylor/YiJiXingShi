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


public class YearPlanMonthDetailTwoFragment extends Fragment {

    public static String TAG = "YearPlanMonthDetailTwoFragment";

    private EditText planMay,planJune,plaJuly,planAugust;
    private Button savePlanTwo;

    private String editDisabledText = "当前月份不能编辑计划";

    private  int curMonth,currDay;

    public static YearPlanMonthDetailTwoFragment newInstance() {
        YearPlanMonthDetailTwoFragment fragment = new YearPlanMonthDetailTwoFragment();
        return fragment;
    }

    public YearPlanMonthDetailTwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_year_plan_part_2,container,false);
        initControls(v);


        return v;
    }


    /**
     * 初始化控件
     * */
    private void initControls(View v){
        planMay = (EditText) v.findViewById(R.id.yea_plan_month_5);
        planJune = (EditText) v.findViewById(R.id.yea_plan_month_6);
        plaJuly = (EditText) v.findViewById(R.id.yea_plan_month_7);
        planAugust = (EditText) v.findViewById(R.id.yea_plan_month_8);
        savePlanTwo = (Button) v.findViewById(R.id.year_plan_2_ok);

        //根据当前月份判断某月份是否可以制定计划，如果已经过了该月份，则该月份不能制定计划
        Calendar calendar = Calendar.getInstance();
        curMonth = calendar.get(Calendar.MONTH) + 1;
        currDay = calendar.get(Calendar.DAY_OF_MONTH);

        if (curMonth >8){
            planMay.setEnabled(false);
            planMay.setHint(editDisabledText);
            planJune.setEnabled(false);
            planJune.setHint(editDisabledText);
            plaJuly.setEnabled(false);
            plaJuly.setHint(editDisabledText);
            planAugust.setEnabled(false);
            planAugust.setHint(editDisabledText);
            for (int i=4; i<8; i++){
                YearPlanMakingActivity.monthEditable[i] = false;
            }

        } else if (curMonth == 8 ) {
            planMay.setEnabled(false);
            planMay.setHint(editDisabledText);
            planJune.setEnabled(false);
            planJune.setHint(editDisabledText);
            plaJuly.setEnabled(false);
            plaJuly.setHint(editDisabledText);
            for (int i=4; i<7; i++){
                YearPlanMakingActivity.monthEditable[i] = false;
            }


            if (currDay > 5) {
                planAugust.setEnabled(false);
                planAugust.setHint(editDisabledText);
                YearPlanMakingActivity.monthEditable[7] = false;
            }
        } else if (curMonth == 7 ){
            planMay.setEnabled(false);
            planMay.setHint(editDisabledText);
            planJune.setEnabled(false);
            planJune.setHint(editDisabledText);

            for (int i=4; i<6; i++){
                YearPlanMakingActivity.monthEditable[i] = false;
            }

            if (currDay > 5) {
                plaJuly.setEnabled(false);
                plaJuly.setHint(editDisabledText);
                YearPlanMakingActivity.monthEditable[6] = false;
            }
        } else if (curMonth == 6){
            planMay.setEnabled(false);
            planMay.setHint(editDisabledText);
            YearPlanMakingActivity.monthEditable[4] = false;
            if (currDay > 5) {
                planJune.setEnabled(false);
                planJune.setHint(editDisabledText);
                YearPlanMakingActivity.monthEditable[5] = false;
            }
        } else if (curMonth == 5){
            if (currDay > 5) {
                planMay.setEnabled(false);
                planMay.setHint(editDisabledText);
                YearPlanMakingActivity.monthEditable[4] = false;
            }
        }
        savePlanTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YearPlanMakingActivity.isCurrPartPlanSaved[1] = true;

                YearPlanMakingActivity.yearPlanMonthDetail[0] = planMay.getText().toString();
                YearPlanMakingActivity.yearPlanMonthDetail[1] = planJune.getText().toString();
                YearPlanMakingActivity.yearPlanMonthDetail[2] = plaJuly.getText().toString();
                YearPlanMakingActivity.yearPlanMonthDetail[3] = planAugust.getText().toString();
                Log.d(TAG, "YearPlanMonthDetailTwoFragment_onSave,execute save operation");
            }
        });


    }

    @Override
    public void onStop() {
        super.onStop();
        if (!YearPlanMakingActivity.isCurrPartPlanSaved[1]){
            YearPlanMakingActivity.isCurrPartPlanSaved[1] = true;

            YearPlanMakingActivity.yearPlanMonthDetail[4] = planMay.getText().toString();
            YearPlanMakingActivity.yearPlanMonthDetail[5] = planJune.getText().toString();
            YearPlanMakingActivity.yearPlanMonthDetail[6] = plaJuly.getText().toString();
            YearPlanMakingActivity.yearPlanMonthDetail[7] = planAugust.getText().toString();
            Log.d(TAG, "YearPlanMonthDetailTwoFragment_onStop,execute save operation");

        }
    }
}
