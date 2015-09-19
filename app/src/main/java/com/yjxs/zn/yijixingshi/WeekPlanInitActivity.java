package com.yjxs.zn.yijixingshi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yjxs.zn.yijixingshi.util.CommonUtil;
import com.yjxs.zn.yijixingshi.util.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class WeekPlanInitActivity extends BaseActivity {

    private TextView currMonth;
    private ImageView back;
    private int currentMonth;
    private List<String> planList = new ArrayList<String>();

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 140) {
                Bundle data = msg.getData();
                String val = data.getString("result");
                Log.i("WeekPlanInitActivity", "请求结果为-->" + val);

                try {
                    JSONObject jsonResult = new JSONObject(val);
                    String monthplanid = jsonResult.getString("monthplanid");
                    String monthplantitle = jsonResult.getString("monthplantitle");
                    String monthplancontent = jsonResult.getString("monthplancontent");

                    planList.add(0,jsonResult.getString("w1"));
                    planList.add(1,jsonResult.getString("w2"));
                    planList.add(2,jsonResult.getString("w3"));
                    planList.add(3,jsonResult.getString("w4"));

                    initControls();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_plan_init);

        Intent intent = getIntent();
        currentMonth = intent.getIntExtra("whichMonth",0);
        getMonthPlanDetail();


    }

    private void initControls(){
        currMonth = (TextView) findViewById(R.id.text_curr_month);
        currMonth.setText("当前月份：" + currentMonth);
        back = (ImageView) findViewById(R.id.week_plan_init_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void getMonthPlanDetail(){
        final String userName = CommonUtil.ReadSharedPreferences(WeekPlanInitActivity.this,
                "login_user");
        Runnable networkTask = new Runnable() {
            @Override
            public void run() {
                String strUrl = "http://120.27.46.194/ZnConsole/business/get_month_plan.php";
                String params = "username=" + userName;
                params += "&whichmonth=";
                params += currentMonth;
                String response = HttpUtil.sendHttpPost(strUrl, params);

                Message msg = new Message();
                msg.what = 140;
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
        Intent intent = new Intent(WeekPlanInitActivity.this,WeekPlanChooseMonthActivity.class);
        startActivity(intent);
        WeekPlanInitActivity.this.finish();
    }

}
