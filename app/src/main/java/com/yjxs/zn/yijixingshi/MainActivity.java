package com.yjxs.zn.yijixingshi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yjxs.zn.yijixingshi.listener.OnPlanTypeSelectedListener;
import com.yjxs.zn.yijixingshi.util.CommonUtil;
import com.yjxs.zn.yijixingshi.view.PlanTypeDialog;

/**
 * 主界面
 * author--zn
 * 2016-07-02
 * */
public class MainActivity extends BaseActivity{

    private TextView showUser;
    private Button logOut;
    private Button lookUpPlan,makeNewPlan;

    private PlanTypeDialog planTypeDialog;

    private long exitTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControls();
        initData();

    }

    /**
     * 初始化控件
     * */
    private void initControls() {
        showUser = (TextView) this.findViewById(R.id.show_user);
        logOut = (Button) this.findViewById(R.id.button_loginout);
        lookUpPlan = (Button) this.findViewById(R.id.button_show_plan);
        makeNewPlan = (Button) this.findViewById(R.id.button_make_plan);
    }

    /**
     * 初始化数据
     * */
    private void initData(){
        String user = CommonUtil.ReadSharedPreferences(MainActivity.this,"login_user");
        showUser.setText(user);

        //退出登录
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtil.WriteSharedPreferences(MainActivity.this,"is_login","no");
                CommonUtil.WriteSharedPreferences(MainActivity.this,"login_user","");
                //跳转到登录界面
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                MainActivity.this.finish();

            }
        });

        //查看已有计划
        lookUpPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //制定新计划
        makeNewPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planTypeDialog = new PlanTypeDialog(MainActivity.this,R.style.loading_dialog);
                planTypeDialog.setOnPlanTypeSelectedListener(new OnPlanTypeSelectedListener() {
                    @Override
                    public void onPlanTypeSelected(int type) {
                        Intent intent;
                        switch (type){
                            case 0:
                                intent = new Intent(MainActivity.this,YearPlanMakingActivity.class);
                                planTypeDialog.dismiss();
                                startActivity(intent);
                                MainActivity.this.finish();
                                break;
                            case 1:
                                intent = new Intent(MainActivity.this,MonthPlanInitActivity.class);
                                planTypeDialog.dismiss();
                                startActivity(intent);
                                MainActivity.this.finish();
                                break;
                            case 2:
                                break;
                            case 3:
                                break;
                        }
                    }
                });
                planTypeDialog.show();
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String user = CommonUtil.ReadSharedPreferences(MainActivity.this,"login_user");
        showUser.setText(user);
    }
}
