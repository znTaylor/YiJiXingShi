package com.yjxs.zn.yijixingshi.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yjxs.zn.yijixingshi.MainActivity;
import com.yjxs.zn.yijixingshi.R;
import com.yjxs.zn.yijixingshi.util.CommonUtil;
import com.yjxs.zn.yijixingshi.util.HttpUtil;
import com.yjxs.zn.yijixingshi.view.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * make year plan fragment.
 */
public class MakeYearPlanFragment extends Fragment {

    public static final String TAG = "MakeYearPlanFragment";
    private static MakeYearPlanFragment instance = null;

    //UI components
    EditText inputShow;
    int[] buttonIds = {R.id.month1,R.id.month2,R.id.month3,R.id.month4,R.id.month5,R.id.month6,
        R.id.month7,R.id.month8,R.id.month9,R.id.month10,R.id.month11,R.id.month12,
            R.id.btn_plan_title,R.id.btn_plan_content};
    Button[] monthButton;
    Button saveYearPlan;

    //其它变量
    /**
     * 保存年计划的详情，即每个月的计划内容外加看计划title和content
     * */
    private String[] yearPlanMonthDetail = {"","","","","","","","","","","","","",""};

    /**
     * 正在编辑的月份
     * */
    private int editingMonth;
    /**
     * 已经编辑过的月份
     * */
    private int editedMonth;
    LoadingDialog dialog;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 110) {
                Bundle data = msg.getData();
                String val = data.getString("result");
                Log.i("YearPlanMaking", "请求结果为-->" + val);

                dialog.dismiss();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // UI界面的更新等相关操作
                try {
                    JSONObject jsonResult = new JSONObject(val);
                    if (jsonResult.getString("result").equals("success")) {
                        Toast toast = CommonUtil.showToast(getActivity(), getString(R.string.save_year_plan_success), true);
                        toast.show();

                    } else if (jsonResult.getString("result").equals("exists")) {
                        Toast toast = CommonUtil.showToast(getActivity(), getString(R.string.save_year_plan_repeat), false);
                        toast.show();
                    } else if (jsonResult.getString("result").equals("failed")){
                        Toast toast = CommonUtil.showToast(getActivity(), getString(R.string.save_year_plan_fail), false);
                        toast.show();
                    } else {
                        Toast toast = CommonUtil.showToast(getActivity(), getString(R.string.save_year_plan_unknown_error), false);
                        toast.show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    };


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
        MainActivity.getInstance().setActionBarTitle(getString(R.string.my_plan_options_make_year_plan));
        inputShow = (EditText) v.findViewById(R.id.input_show);
        saveYearPlan = (Button) v.findViewById(R.id.save);

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

        saveYearPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new LoadingDialog(MakeYearPlanFragment.this.getActivity(),
                        R.style.loading_dialog,getString(R.string.save_year_plan_detail));
                dialog.show();
                //验证计划内容
               // validateYearPlanDetail();
                //验证通过后保存
                saveYearPlanDetail(formatPlanDetail());

            }
        });

        setAllMonthsDisabled();
        getMonthsPlanedAbled();



    }


    /**
     * 判断可以制定计划的月份
     * */
    private void getMonthsPlanedAbled(){
        int currMonth = CommonUtil.getCurrentMonth();
        Log.d(TAG, "CURR-MONTH:" + currMonth);
        for (int i=0; i<buttonIds.length-2; i++){
            if ((i+1)<currMonth)
                continue;

            if ((i+1)==currMonth){
                int currDays = CommonUtil.getCurrentDayOfMonth();
                if (currDays>10){
                    continue;
                }
                else{
                    monthButton[i].setEnabled(true);
                    continue;
                }
            }

            monthButton[i].setEnabled(true);

        }
    }

    /**
     * 使所有的月份都不能编辑
     * */
    private void setAllMonthsDisabled(){
        for (int i=0; i<monthButton.length-2; i++){
            monthButton[i].setEnabled(false);
        }
    }

    /**
     * 获取缓存的指定月的计划
     * */
    private String getSingleMonthPlan(int month){
        return yearPlanMonthDetail[month];
    }

    /**
     * 保存前检查年计划详情
     * */
    private void validateYearPlanDetail(){
        if (TextUtils.isEmpty(getSingleMonthPlan(12))){ //title
            Toast toast = CommonUtil.showToast(getActivity(),
                    getString(R.string.save_year_plan_check_title_empty),
                    false);
            toast.show();
            return;
        }
        if (TextUtils.isEmpty(getSingleMonthPlan(13))){//content
            Toast toast = CommonUtil.showToast(getActivity(),
                    getString(R.string.save_year_plan_check_content_empty),
                    false);
            toast.show();
            return;
        }

        int enabledMonth = 0;
        for (int i=0; i<monthButton.length-2; i++){
            if (monthButton[i].isEnabled()){
                enabledMonth = (i+1);
                break;
            }
        }
        for (int i=enabledMonth; i<yearPlanMonthDetail.length-2; i++){
            if (TextUtils.isEmpty(yearPlanMonthDetail[i])){
                Toast toast = CommonUtil.showToast(getActivity(),
                        getString(R.string.save_year_plan_check_month_empty),
                        false);
                toast.show();
                return;
            }
        }

    }

    /**
     * 格式化计划详情
     * */
    private String formatPlanDetail(){
        String s = "";
        s += yearPlanMonthDetail[12];
        s += ",";
        s += yearPlanMonthDetail[13];
        s += ",";
        for (int i=0; i<yearPlanMonthDetail.length-2; i++){
            s += yearPlanMonthDetail[i];
            s += ",";
        }


        return s.substring(0,s.length()-1);
    }

    /**
     * 保存年计划详细信息
     * */
    private void saveYearPlanDetail(final String planDetail){
        Runnable networkTask = new Runnable() {
            @Override
            public void run() {
                String strUrl = "http://120.27.46.194/ZnConsole/business/save_year_plan.php";
                String params = "yearPlan=" + planDetail;

                String response = HttpUtil.sendHttpPost(strUrl, params);

                Message msg = new Message();
                msg.what = 110;
                Bundle data = new Bundle();
                data.putString("result", response);
                msg.setData(data);
                handler.sendMessage(msg);


            }
        };

        new Thread(networkTask).start();
    }

}
