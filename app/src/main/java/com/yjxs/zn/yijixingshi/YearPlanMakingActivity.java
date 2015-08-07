package com.yjxs.zn.yijixingshi;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yjxs.zn.yijixingshi.fragment.YearPlanMonthDetailOneFragment;
import com.yjxs.zn.yijixingshi.fragment.YearPlanMonthDetailThreeFragment;
import com.yjxs.zn.yijixingshi.fragment.YearPlanMonthDetailTwoFragment;
import com.yjxs.zn.yijixingshi.util.CommonUtil;
import com.yjxs.zn.yijixingshi.util.HttpUtil;
import com.yjxs.zn.yijixingshi.view.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;


public class YearPlanMakingActivity extends BaseActivity {

    private TextView year_title;
    private ImageView year_back;

    private EditText yearPlanTitle,yearPlanContent;

    private Button yearPlanPartOne,yearPlanPartTwo,yearPlanPartThree;
    private Button saveYearPlan;

    private YearPlanMonthDetailOneFragment yearPlanOneFragment;
    private YearPlanMonthDetailTwoFragment yearPlanTwoFragment;
    private YearPlanMonthDetailThreeFragment yearPlanThreeFragment;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    /**
     * 指示哪个月份可以编辑计划,默认都可以编辑
     * */
    public static boolean[] monthEditable = {true,true,true,true,true,true,true,true,true,true,true,true};
    /**
     * 保存年计划的详情，即每个月的计划内容
     * */
    public static String[] yearPlanMonthDetail = {"","","","","","","","","","","",""};
    /**
     * 年计划制定时分为三部分，4个月为一部分。判断每一部分的数据是否已经保存
     * */
    public static boolean[] isCurrPartPlanSaved = {false,false,false};
    LoadingDialog dialog;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 110) {
                Bundle data = msg.getData();
                String val = data.getString("result");
                Log.i("YearPlanMakingActivity", "请求结果为-->" + val);

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
                        Toast toast = CommonUtil.showToast(YearPlanMakingActivity.this, "保存成功!", true);
                        toast.show();

                    } else if (jsonResult.getString("result").equals("exists")) {
                        Toast toast = CommonUtil.showToast(YearPlanMakingActivity.this, "年计划已存在！", false);
                        toast.show();
                    } else {
                        Toast toast = CommonUtil.showToast(YearPlanMakingActivity.this, "保存失败", false);
                        toast.show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_plan_making);
        initControls();
    }


    private void initControls(){

        yearPlanTitle = (EditText) this.findViewById(R.id.year_plan_title);
        yearPlanContent = (EditText) this.findViewById(R.id.year_plan_content);

        fragmentManager = getFragmentManager();
        yearPlanOneFragment = new YearPlanMonthDetailOneFragment();
        yearPlanTwoFragment = new YearPlanMonthDetailTwoFragment();
        yearPlanThreeFragment = new YearPlanMonthDetailThreeFragment();

        //初始fragment 1-4
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.year_plan_detail_frame, yearPlanOneFragment);
        fragmentTransaction.commit();

        year_title = (TextView) this.findViewById(R.id.text_year_plan);
        year_back = (ImageView) this.findViewById(R.id.year_plan_back);
        year_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        year_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        yearPlanPartOne = (Button) this.findViewById(R.id.year_month_1);
        yearPlanPartOne.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.year_plan_detail_frame,yearPlanOneFragment);
                fragmentTransaction.commit();
            }
        });
        yearPlanPartTwo = (Button) this.findViewById(R.id.year_month_2);
        yearPlanPartTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.year_plan_detail_frame,yearPlanTwoFragment);
                fragmentTransaction.commit();
            }
        });
        yearPlanPartThree = (Button) this.findViewById(R.id.year_month_3);
        yearPlanPartThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.year_plan_detail_frame,yearPlanThreeFragment);
                fragmentTransaction.commit();
            }
        });

        //保存年计划
        saveYearPlan = (Button) this.findViewById(R.id.button_save);
        saveYearPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断年计划标题和内容
                if (TextUtils.isEmpty(yearPlanTitle.getText().toString()) ||
                        TextUtils.isEmpty(yearPlanContent.getText().toString()) ){
                    Toast toast = CommonUtil.showToast(YearPlanMakingActivity.this,
                            "年计划内容或标题不能为空！", false);
                    toast.show();
                    return;
                }

                //判断isCurrPartPlanSaved的值
                for (int i=0; i<isCurrPartPlanSaved.length; i++){
                    if (isCurrPartPlanSaved[i] = false){ //如果有数据未保存，则不能继续进行下去
                        Toast toast = CommonUtil.showToast(YearPlanMakingActivity.this,
                                "有未保存的计划，请确认！", false);
                        toast.show();
                        return;
                    }
                }

                //判断monthEditable和yearPlanMonthDetail
                for (int i=0; i<monthEditable.length; i++){
                    if (monthEditable[i]){
                        if (TextUtils.isEmpty(yearPlanMonthDetail[i])){
                            Toast toast = CommonUtil.showToast(YearPlanMakingActivity.this,
                                    (i+1)+ "月的计划未制定，请确认！", false);
                            toast.show();
                            return;
                        }
                    }
                }



                //保存
                dialog = new LoadingDialog(YearPlanMakingActivity.this,
                        R.style.loading_dialog,getString(R.string.save_year_plan_detail));
                dialog.show();

                String userName = CommonUtil.ReadSharedPreferences(YearPlanMakingActivity.this,
                        "login_user");

                String planDetail = "";
                planDetail += userName;
                planDetail += ",";
                planDetail += yearPlanTitle.getText().toString();
                planDetail += ",";
                planDetail += yearPlanContent.getText().toString();
                planDetail += ",";
                for (int i=0; i<yearPlanMonthDetail.length;i++){
                    planDetail += yearPlanMonthDetail[i];
                    planDetail += ",";
                }
                planDetail.substring(0,planDetail.length()-1);
                saveYearPlanDetail(planDetail);

            }
        });
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



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(YearPlanMakingActivity.this,MainActivity.class);
        startActivity(intent);
        YearPlanMakingActivity.this.finish();
    }
}
