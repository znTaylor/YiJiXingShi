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


public class YearPlanMonthDetailThreeFragment extends Fragment {

    private EditText planSeptember,planOctober,plaNovember,planDecember;
    private Button savePlanThree;

    private String editDisabledText = "当前月份不能编辑计划";

    private  int curMonth,currDay;

    //监听器
    private MonthPlanEditCompletedListenerThree monthPlanEditCompletedListenerThree;
    public MonthPlanEditCompletedListenerThree getMonthPlanEditCompletedListenerThree(){
        return monthPlanEditCompletedListenerThree;
    }
    public void setMonthPlanEditCompletedListenerTwo(MonthPlanEditCompletedListenerThree monthPlanEditCompletedListenerThree){
        this.monthPlanEditCompletedListenerThree = monthPlanEditCompletedListenerThree;
    }


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
        } else if (curMonth == 12 ){
            planSeptember.setEnabled(false);
            planSeptember.setHint(editDisabledText);
            planOctober.setEnabled(false);
            planOctober.setHint(editDisabledText);
            plaNovember.setEnabled(false);
            plaNovember.setHint(editDisabledText);

            if (currDay > 5){
                planDecember.setEnabled(false);
                planDecember.setHint(editDisabledText);
            }
        } else if (curMonth == 11 ){
            planSeptember.setEnabled(false);
            planSeptember.setHint(editDisabledText);
            planOctober.setEnabled(false);
            planOctober.setHint(editDisabledText);
            if (currDay > 5) {
                plaNovember.setEnabled(false);
                plaNovember.setHint(editDisabledText);
            }
        } else if (curMonth == 10){
            planSeptember.setEnabled(false);
            planSeptember.setHint(editDisabledText);
            if (currDay > 5) {
                planOctober.setEnabled(false);
                planOctober.setHint(editDisabledText);
            }
        } else if (curMonth == 9){
            if (currDay > 5) {
                planSeptember.setEnabled(false);
                planSeptember.setHint(editDisabledText);
            }
        }
        savePlanThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monthPlanEditCompletedListenerThree != null) {
                    if (curMonth >= 8) {
                        monthPlanEditCompletedListenerThree.onCompleted("","","","");
                    } else if (curMonth == 7){
                        monthPlanEditCompletedListenerThree.onCompleted("","","",planDecember.getText().toString());
                    } else if (curMonth == 6){
                        monthPlanEditCompletedListenerThree.onCompleted("","",
                                plaNovember.getText().toString(),planDecember.getText().toString());
                    } else if (curMonth == 5){
                        monthPlanEditCompletedListenerThree.onCompleted(planSeptember.getText().toString()
                                ,planOctober.getText().toString(),
                                plaNovember.getText().toString(),planDecember.getText().toString());
                    }
                }
            }
        });

    }

    /**
     * 当完成9-12月份计划编辑，点击ok按钮时，触发
     *
     * */
    public interface MonthPlanEditCompletedListenerThree{
        /**
         * @param p9
         *     5月份计划
         * @param p10
         *     6月份计划
         * @param p11
         *     7月份计划
         * @param p12
         *     8月份计划
         * */
        void onCompleted(String p9,String p10,String p11,String p12);
    }

}
