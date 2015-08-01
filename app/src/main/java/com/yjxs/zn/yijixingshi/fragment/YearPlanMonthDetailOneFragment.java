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

import java.util.Calendar;


public class YearPlanMonthDetailOneFragment extends Fragment {

    private EditText planJanuary,planFebruary,planMarch,planApril;
    private Button savePlanOne;

    private String editDisabledText = "当前月份不能编辑计划";

    private  int curMonth,currDay;

    //监听器
    private MonthPlanEditCompletedListenerOne monthPlanEditCompletedListenerOne;
    public MonthPlanEditCompletedListenerOne getMonthPlanEditCompletedListenerOne(){
        return monthPlanEditCompletedListenerOne;
    }
    public void setMonthPlanEditCompletedListenerOne(MonthPlanEditCompletedListenerOne monthPlanEditCompletedListenerOne){
        this.monthPlanEditCompletedListenerOne = monthPlanEditCompletedListenerOne;
    }

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
        } else if (curMonth == 4 ){
            planJanuary.setEnabled(false);
            planJanuary.setHint(editDisabledText);
            planFebruary.setEnabled(false);
            planFebruary.setHint(editDisabledText);
            planMarch.setEnabled(false);
            planMarch.setHint(editDisabledText);

            if (currDay > 5){
                planApril.setEnabled(false);
                planApril.setHint(editDisabledText);
            }
        }  else if (curMonth == 3 ){
            planJanuary.setEnabled(false);
            planJanuary.setHint(editDisabledText);
            planFebruary.setEnabled(false);
            planFebruary.setHint(editDisabledText);
            if (currDay > 5) {
                planMarch.setEnabled(false);
                planMarch.setHint(editDisabledText);
            }
        } else if (curMonth == 2){
            planJanuary.setEnabled(false);
            planJanuary.setHint(editDisabledText);
            if (currDay > 5) {
                planFebruary.setEnabled(false);
                planFebruary.setHint(editDisabledText);
            }
        } else if (curMonth == 1){
            if (currDay > 5) {
                planJanuary.setEnabled(false);
                planJanuary.setHint(editDisabledText);
            }
        }

        savePlanOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monthPlanEditCompletedListenerOne != null) {
                    if (curMonth >= 4) {
                        monthPlanEditCompletedListenerOne.onCompleted("","","","");
                    } else if (curMonth == 3){
                        monthPlanEditCompletedListenerOne.onCompleted("","","",planApril.getText().toString());
                    } else if (curMonth == 2){
                        monthPlanEditCompletedListenerOne.onCompleted("","",
                                planMarch.getText().toString(),planApril.getText().toString());
                    } else if (curMonth == 1){
                        monthPlanEditCompletedListenerOne.onCompleted(planJanuary.getText().toString()
                                ,planFebruary.getText().toString(),
                                planMarch.getText().toString(),planApril.getText().toString());
                    }
                }

            }
        });
    }


    /**
     * 当完成1-4月份计划编辑，点击ok按钮时，触发
     *
     * */
    public interface MonthPlanEditCompletedListenerOne{
        /**
         * @param p1
         *     1月份计划
         * @param p2
         *     2月份计划
         * @param p3
         *     3月份计划
         * @param p4
         *     4月份计划
         * */
        void onCompleted(String p1,String p2,String p3,String p4);
    }



}
