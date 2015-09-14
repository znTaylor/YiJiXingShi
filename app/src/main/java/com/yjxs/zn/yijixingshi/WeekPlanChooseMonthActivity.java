package com.yjxs.zn.yijixingshi;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.yjxs.zn.yijixingshi.adapter.MonthPlanDetailOfCurrYearAdapter;
import com.yjxs.zn.yijixingshi.util.CommonUtil;
import com.yjxs.zn.yijixingshi.util.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class WeekPlanChooseMonthActivity extends BaseActivity {

    private ListView monthsList;
    private ImageView back;
    private List<String> planList = new ArrayList<String>();

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 130) {
                Bundle data = msg.getData();
                String val = data.getString("result");
                Log.i("WeekPlanChooseMonth", "请求结果为-->" + val);

                try {
                    JSONObject jsonResult = new JSONObject(val);
                    String yearplanid = jsonResult.getString("yearplanid");
                    String yearplantitle = jsonResult.getString("yearplantitle");
                    String yearplancontent = jsonResult.getString("yearplancontent");
                    String beginmonth = jsonResult.getString("beginmonth");

                    planList.add(0,jsonResult.getString("plan1"));
                    planList.add(1,jsonResult.getString("plan2"));
                    planList.add(2,jsonResult.getString("plan3"));
                    planList.add(3,jsonResult.getString("plan4"));
                    planList.add(4,jsonResult.getString("plan5"));
                    planList.add(5,jsonResult.getString("plan6"));
                    planList.add(6,jsonResult.getString("plan7"));
                    planList.add(7,jsonResult.getString("plan8"));
                    planList.add(8,jsonResult.getString("plan9"));
                    planList.add(9,jsonResult.getString("plan10"));
                    planList.add(10,jsonResult.getString("plan11"));
                    planList.add(11,jsonResult.getString("plan12"));

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
        setContentView(R.layout.activity_week_plan_choose_month);



        getYearPlanDetail();
    }

    private void initControls(){

        monthsList = (ListView) this.findViewById(R.id.weekplan_listview_12_months);
        MonthPlanDetailOfCurrYearAdapter adapter = new MonthPlanDetailOfCurrYearAdapter(
                this,
                R.layout.layout_month_list_item,
                planList
        );
        monthsList.setAdapter(adapter);
        monthsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(WeekPlanChooseMonthActivity.this,WeekPlanInitActivity.class);
                intent.putExtra("whichMonth",(position+1));
                startActivity(intent);
                WeekPlanChooseMonthActivity.this.finish();
            }
        });


        //回退
        back = (ImageView) this.findViewById(R.id.month_plan_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void getYearPlanDetail(){
        final String userName = CommonUtil.ReadSharedPreferences(WeekPlanChooseMonthActivity.this,
                "login_user");
        Runnable networkTask = new Runnable() {
            @Override
            public void run() {
                String strUrl = "http://120.27.46.194/ZnConsole/business/get_year_plan.php";
                String params = "username=" + userName;
                String response = HttpUtil.sendHttpPost(strUrl, params);

                Message msg = new Message();
                msg.what = 130;
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
        Intent intent = new Intent(WeekPlanChooseMonthActivity.this,MainActivity.class);
        startActivity(intent);
        WeekPlanChooseMonthActivity.this.finish();
    }
}
