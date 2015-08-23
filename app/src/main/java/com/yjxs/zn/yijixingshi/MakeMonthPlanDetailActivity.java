package com.yjxs.zn.yijixingshi;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yjxs.zn.yijixingshi.util.CommonUtil;
import com.yjxs.zn.yijixingshi.util.HttpUtil;
import com.yjxs.zn.yijixingshi.view.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;


public class MakeMonthPlanDetailActivity extends BaseActivity {
    private TextView monthToBePlaned;
    private EditText planWeek1,planWeek2,planWeek3,planWeek4;
    private Button saveMonthPlanDetail;
    LoadingDialog dialog;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 122) {
                Bundle data = msg.getData();
                String val = data.getString("result");
                Log.i("MakeMonthPlanDetail", "请求结果为-->" + val);

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
                        Toast toast = CommonUtil.showToast(MakeMonthPlanDetailActivity.this, "保存成功!", true);
                        toast.show();

                    } else if (jsonResult.getString("result").equals("exists")) {
                        Toast toast = CommonUtil.showToast(MakeMonthPlanDetailActivity.this, "月计划已存在！", false);
                        toast.show();
                    } else if (jsonResult.getString("result").equals("failed")){
                        Toast toast = CommonUtil.showToast(MakeMonthPlanDetailActivity.this, "保存失败", false);
                        toast.show();
                    } else {
                        Toast toast = CommonUtil.showToast(MakeMonthPlanDetailActivity.this, "未知错误", false);
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
        setContentView(R.layout.activity_make_month_plan_detail);

        Intent intent = getIntent();
        initControls(intent);
    }


    private void initControls(final Intent intent){
        monthToBePlaned = (TextView) this.findViewById(R.id.curr_plan_month);
        planWeek1 = (EditText) this.findViewById(R.id.plan_week_1);
        planWeek2 = (EditText) this.findViewById(R.id.plan_week_2);
        planWeek3 = (EditText) this.findViewById(R.id.plan_week_3);
        planWeek4 = (EditText) this.findViewById(R.id.plan_week_4);
        saveMonthPlanDetail = (Button) this.findViewById(R.id.button_make_month_plan);
        saveMonthPlanDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //保存月计划
                String plan1 = planWeek1.getText().toString();
                String plan2 = planWeek2.getText().toString();
                String plan3 = planWeek3.getText().toString();
                String plan4 = planWeek4.getText().toString();

                if (TextUtils.isEmpty(plan1) || TextUtils.isEmpty(plan2) ||
                        TextUtils.isEmpty(plan3) || TextUtils.isEmpty(plan4)) {
                    Toast toast = CommonUtil.showToast(MakeMonthPlanDetailActivity.this, "周计划为必填项", false);
                    toast.show();
                    return;
                }

                //保存
                dialog = new LoadingDialog(MakeMonthPlanDetailActivity.this,
                        R.style.loading_dialog,getString(R.string.save_year_plan_detail));
                dialog.show();

                String userName = CommonUtil.ReadSharedPreferences(MakeMonthPlanDetailActivity.this,
                        "login_user");
                String params = "";
                params += userName;
                params += ",";
                params += intent.getStringExtra("monthToBePlaned");
                params += ",";
                params += plan1;
                params += ",";
                params += plan2;
                params += ",";
                params += plan3;
                params += ",";
                params += plan4;

                //保存
                saveMonthPlanDetail(params);

            }
        });

        if (intent.hasExtra("monthToBePlaned")){
            monthToBePlaned.setText(intent.getStringExtra("monthToBePlaned"));
        }

    }


    /**
     * 保存年计划详细信息
     * */
    private void saveMonthPlanDetail(final String planDetail){
        Runnable networkTask = new Runnable() {
            @Override
            public void run() {
                String strUrl = "http://120.27.46.194/ZnConsole/business/save_month_plan.php";
                String params = "monthPlan=" + planDetail;

                String response = HttpUtil.sendHttpPost(strUrl, params);

                Message msg = new Message();
                msg.what = 122;
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
        Intent intent = new Intent(this,MonthPlanInitActivity.class);
        startActivity(intent);
        this.finish();
    }


}
