package com.yjxs.zn.yijixingshi.fragment;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

    //其它变量
    /**
     * 保存年计划的详情，即每个月的计划内容
     * */
    private String[] yearPlanMonthDetail = {"","","","","","","","","","","",""};

    /**
     * 正在编辑的月份
     * */
    private int editingMonth;
    /**
     * 已经编辑过的月份
     * */
    private int editedMonth;


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

        inputShow.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Log.d(TAG, "READY TO INPUT");
                }
                else {
                    Log.d(TAG, "INPUT COMPLETED");
                    yearPlanMonthDetail[editedMonth] = inputShow.getText().toString();
                    if (!TextUtils.isEmpty(inputShow.getText().toString())){
                        inputShow.setText(null);
                    }
                    else{
                        monthButton[editedMonth].setBackgroundResource(R.drawable.round_button_selector);
                    }
                }
            }
        });


        monthButton = new Button[buttonIds.length];
        for (int i=0; i<buttonIds.length; i++){
            final int index = i;
            monthButton[i] = (Button) v.findViewById(buttonIds[i]);
            monthButton[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (inputShow.isFocused()) {
                        inputShow.clearFocus();//输入框失去焦点
                    }
                    editingMonth = index;
                    inputShow.setText(getSingleMonthPlan(editingMonth));
                    inputShow.requestFocus();//输入框获取焦点(layout文件中设置focusable=true)
                    editedMonth = editingMonth;
                    monthButton[index].setBackgroundResource(R.drawable.round_button_selector2);
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

    /**
     * 获取缓存的指定月的计划
     * */
    private String getSingleMonthPlan(int month){
        return yearPlanMonthDetail[month];
    }

}
