package com.yjxs.zn.yijixingshi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yjxs.zn.yijixingshi.view.PlanTypeDialog;

/**
 * 主界面
 * author--zn
 * 2016-07-02
 * */
public class MainActivity extends BaseActivity {

    private TextView showUser;
    private Button logOut;
    private Button lookUpPlan,makeNewPlan;

    private PlanTypeDialog planTypeDialog;

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
        Intent intent = this.getIntent();
        String user = intent.getStringExtra("user");
        showUser.setText(user);

        //退出登录
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                planTypeDialog.show();
            }
        });

    }

}
