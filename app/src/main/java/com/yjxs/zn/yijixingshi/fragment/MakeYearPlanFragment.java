package com.yjxs.zn.yijixingshi.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yjxs.zn.yijixingshi.R;
import com.yjxs.zn.yijixingshi.util.CommonUtil;

/**
 * make year plan fragment.
 */
public class MakeYearPlanFragment extends Fragment {

    public static final String TAG = "MakeYearPlanFragment";
    private static MakeYearPlanFragment instance = null;

    //UI components
    EditText inputShow;
    Button planTitle,planContent;
    int[] buttonIds = {R.id.month1,R.id.month2,R.id.month3,R.id.month4,R.id.month5,R.id.month6,
        R.id.month7,R.id.month8,R.id.month9,R.id.month10,R.id.month11,R.id.month12};
    Button[] monthButton;


    public MakeYearPlanFragment() {
        // Required empty public constructor
    }

    public static MakeYearPlanFragment getInstance(){
        if (instance == null){
            instance = new MakeYearPlanFragment();
            return instance;
        }
        else {
            return instance;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view =  inflater.inflate(R.layout.fragment_make_year_plan, container, false);
        init(view);
        return view;
    }

    /**
     * 初始化控件
     * @param v
     *     view
     * */
    private void init(View v){
        planTitle = (Button) v.findViewById(R.id.btn_plan_title);
        planContent = (Button) v.findViewById(R.id.btn_plan_content);
        inputShow = (EditText) v.findViewById(R.id.input_show);

        planTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        planContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        monthButton = new Button[buttonIds.length];
        for (int i=0; i<buttonIds.length; i++){
            final int index = i;
            monthButton[i] = (Button) v.findViewById(buttonIds[i]);
            monthButton[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        setAllMonthsDisabled();
        getMonthsPlanedAbled();



    }


    /**
     * 判断可以制定计划的月份
     * */
    private void getMonthsPlanedAbled(){
        int currMonth = CommonUtil.getCurrentMonth();
        Log.d(TAG, "CURR-MONTH:" + currMonth);
        for (int i=0; i<buttonIds.length; i++){
            if ((i+1)<currMonth)
                continue;

            if ((i+1)==currMonth){
                int currDays = CommonUtil.getCurrentDayOfMonth();
                if (currDays>10){
                    continue;
                }
                else{
                    monthButton[i].setEnabled(true);
                   // monthButton[i].setBackgroundResource(R.drawable.round_button_selector2);
                    continue;
                }
            }

            monthButton[i].setEnabled(true);
           // monthButton[i].setBackgroundResource(R.drawable.round_button_selector2);

        }
    }

    /**
     * 使所有的月份都不能编辑
     * */
    private void setAllMonthsDisabled(){
        for (int i=0; i<monthButton.length; i++){
            monthButton[i].setEnabled(false);
        }
    }


}
