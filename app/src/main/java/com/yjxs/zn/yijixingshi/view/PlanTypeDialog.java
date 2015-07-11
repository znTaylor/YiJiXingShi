package com.yjxs.zn.yijixingshi.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.yjxs.zn.yijixingshi.R;
import com.yjxs.zn.yijixingshi.listener.OnPlanTypeSelectedListener;


public class PlanTypeDialog extends Dialog {

    private Context context;
    private Button yearPlan,monthPlan,weekPlan,customPlan;

    private OnPlanTypeSelectedListener onPlanTypeSelectedListener;
    public void setOnPlanTypeSelectedListener(OnPlanTypeSelectedListener listener){
        this.onPlanTypeSelectedListener = listener;
    }
    public OnPlanTypeSelectedListener getOnPlanTypeSelectedListener(){
        return onPlanTypeSelectedListener;
    }


    public PlanTypeDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.layout_plan_type, null);
        setContentView(v);
        initControls();
    }

    /**
     * 初始化控件
     * */
    private void initControls(){
        yearPlan = (Button) this.findViewById(R.id.button_year_plan);
        monthPlan = (Button) this.findViewById(R.id.button_month_plan);
        weekPlan = (Button) this.findViewById(R.id.button_week_plan);
        customPlan = (Button) this.findViewById(R.id.button_custom_plan);

        //制定年计划
        yearPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPlanTypeSelectedListener != null){
                    onPlanTypeSelectedListener.onPlanTypeSelected(0);
                }
            }
        });
        //制定月计划
        monthPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPlanTypeSelectedListener != null){
                    onPlanTypeSelectedListener.onPlanTypeSelected(1);
                }
            }
        });
        //制定周计划
        weekPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPlanTypeSelectedListener != null){
                    onPlanTypeSelectedListener.onPlanTypeSelected(2);
                }
            }
        });
        //制定自定义计划
        customPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPlanTypeSelectedListener != null){
                    onPlanTypeSelectedListener.onPlanTypeSelected(3);
                }
            }
        });
    }
}
