package com.yjxs.zn.yijixingshi.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.yjxs.zn.yijixingshi.R;

import java.util.Calendar;


public class YearPlanMonthDetailTwoFragment extends Fragment {

    private EditText planMay,planJune,plaJuly,planAugust;
    private Button savePlanTwo;

    private String editDisabledText = "当前月份不能编辑计划";

    private  int curMonth,currDay;

    //监听器
    private MonthPlanEditCompletedListenerTwo monthPlanEditCompletedListenerTwo;
    public MonthPlanEditCompletedListenerTwo getMonthPlanEditCompletedListenerTwo(){
        return monthPlanEditCompletedListenerTwo;
    }
    public void setMonthPlanEditCompletedListenerTwo(MonthPlanEditCompletedListenerTwo monthPlanEditCompletedListenerTwo){
        this.monthPlanEditCompletedListenerTwo = monthPlanEditCompletedListenerTwo;
    }


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
        } else if (curMonth == 8 ) {
            planMay.setEnabled(false);
            planMay.setHint(editDisabledText);
            planJune.setEnabled(false);
            planJune.setHint(editDisabledText);
            plaJuly.setEnabled(false);
            plaJuly.setHint(editDisabledText);

            if (currDay > 5) {
                planAugust.setEnabled(false);
                planAugust.setHint(editDisabledText);
            }
        } else if (curMonth == 7 ){
            planMay.setEnabled(false);
            planMay.setHint(editDisabledText);
            planJune.setEnabled(false);
            planJune.setHint(editDisabledText);
            if (currDay > 5) {
                plaJuly.setEnabled(false);
                plaJuly.setHint(editDisabledText);
            }
        } else if (curMonth == 6){
            planMay.setEnabled(false);
            planMay.setHint(editDisabledText);
            if (currDay > 5) {
                planJune.setEnabled(false);
                planJune.setHint(editDisabledText);
            }
        } else if (curMonth == 5){
            if (currDay > 5) {
                planMay.setEnabled(false);
                planMay.setHint(editDisabledText);
            }
        }
        savePlanTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monthPlanEditCompletedListenerTwo != null) {
                    if (curMonth >= 8) {
                        monthPlanEditCompletedListenerTwo.onCompleted("", "", "", "");
                    } else if (curMonth == 7) {
                        monthPlanEditCompletedListenerTwo.onCompleted("", "", "", planAugust.getText().toString());
                    } else if (curMonth == 6) {
                        monthPlanEditCompletedListenerTwo.onCompleted("", "",
                                plaJuly.getText().toString(), planAugust.getText().toString());
                    } else if (curMonth == 5) {
                        monthPlanEditCompletedListenerTwo.onCompleted(planMay.getText().toString()
                                , planJune.getText().toString(),
                                plaJuly.getText().toString(), planAugust.getText().toString());
                    }
                }
            }
        });


    }


    /**
     * 当完成5-8月份计划编辑，点击ok按钮时，触发
     *
     * */
    public interface MonthPlanEditCompletedListenerTwo{
        /**
         * @param p5
         *     5月份计划
         * @param p6
         *     6月份计划
         * @param p7
         *     7月份计划
         * @param p8
         *     8月份计划
         * */
        void onCompleted(String p5,String p6,String p7,String p8);
    }


}
