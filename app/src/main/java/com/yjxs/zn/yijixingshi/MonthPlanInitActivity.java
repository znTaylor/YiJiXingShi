package com.yjxs.zn.yijixingshi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yjxs.zn.yijixingshi.util.CommonUtil;
import com.yjxs.zn.yijixingshi.util.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;


public class MonthPlanInitActivity extends BaseActivity {

    private TextView currYearPlan,monthPlanTitle,monthPlanContent,beginAndEndMonth,monthToBePlaned;
    private Button makeMonthPlan;
    private ImageView monthPlanBack;
    private String[] planList = new String[12];

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 120) {
                Bundle data = msg.getData();
                String val = data.getString("result");
                Log.i("MonthPlanInitActivity", "请求结果为-->" + val);

                // UI界面的更新等相关操作
                try {
                    JSONObject jsonResult = new JSONObject(val);
                    String yearplanid = jsonResult.getString("yearplanid");
                    String yearplantitle = jsonResult.getString("yearplantitle");
                    String yearplancontent = jsonResult.getString("yearplancontent");
                    String beginmonth = jsonResult.getString("beginmonth");

                    planList[0]= jsonResult.getString("plan1");
                    planList[1]= jsonResult.getString("plan2");
                    planList[2]= jsonResult.getString("plan3");
                    planList[3]= jsonResult.getString("plan4");
                    planList[4]= jsonResult.getString("plan5");
                    planList[5]= jsonResult.getString("plan6");
                    planList[6]= jsonResult.getString("plan7");
                    planList[7]= jsonResult.getString("plan8");
                    planList[8]= jsonResult.getString("plan9");
                    planList[9]= jsonResult.getString("plan10");
                    planList[10]= jsonResult.getString("plan11");
                    planList[11]= jsonResult.getString("plan12");


                    currYearPlan.setText(yearplanid);
                    monthPlanTitle.setText(yearplantitle);
                    monthPlanContent.setText(yearplancontent);
                    beginAndEndMonth.setText(beginmonth+"-"+"12");

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_plan_init);
        Intent intent = getIntent();
        initControls(intent);
    }


    /**
     * 初始化控件
     * */
    private void initControls(Intent intent){
        currYearPlan = (TextView) this.findViewById(R.id.curr_year_plan);
        monthPlanTitle = (TextView) this.findViewById(R.id.month_plan_title);
        monthPlanContent = (TextView) this.findViewById(R.id.month_plan_content);
        beginAndEndMonth = (TextView) this.findViewById(R.id.month_begin_end_date);
        monthToBePlaned = (TextView) this.findViewById(R.id.choose_month);
        monthToBePlaned.setOnClickListener(new View.OnClickListener() { //点击跳转到月份选择界面
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MonthPlanInitActivity.this, MonthPlanChooseMonthActivity.class);
                intent.putExtra("planList", planList);
                startActivity(intent);
                MonthPlanInitActivity.this.finish();
            }
        });

        //初始化数据
        getYearPlanDetail();

        makeMonthPlan = (Button) this.findViewById(R.id.button_make_month_plan);
        makeMonthPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        monthPlanBack = (ImageView) this.findViewById(R.id.month_plan_back);
        monthPlanBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (intent.hasExtra("whichMonth")){
            int index = intent.getIntExtra("whichMonth",0);
            monthToBePlaned.setText("" + index);
        }
    }

    private void getYearPlanDetail(){
        final String userName = CommonUtil.ReadSharedPreferences(MonthPlanInitActivity.this,
                "login_user");
        Runnable networkTask = new Runnable() {
            @Override
            public void run() {
                String strUrl = "http://120.27.46.194/ZnConsole/business/get_year_plan.php";
                String params = "username=" + userName;
                String response = HttpUtil.sendHttpPost(strUrl, params);

                Message msg = new Message();
                msg.what = 120;
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
        Intent intent = new Intent(MonthPlanInitActivity.this,MainActivity.class);
        startActivity(intent);
        MonthPlanInitActivity.this.finish();
    }

}
