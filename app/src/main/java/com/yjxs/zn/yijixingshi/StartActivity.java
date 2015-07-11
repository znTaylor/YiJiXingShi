package com.yjxs.zn.yijixingshi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.yjxs.zn.yijixingshi.util.CommonUtil;


public class StartActivity extends BaseActivity {


    private static final int START_DELAY_SECONDS = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.start);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                String is_login = CommonUtil.ReadSharedPreferences(StartActivity.this,
                        "is_login");
                String userName = CommonUtil.ReadSharedPreferences(StartActivity.this,
                        "login_user");

                if (is_login.equals("yes")){
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    intent.putExtra("user",userName);
                    StartActivity.this.startActivity(intent);
                    StartActivity.this.finish();
                } else {
                    Intent intent = new Intent(StartActivity.this, CheckLoginActivity.class);
                    StartActivity.this.startActivity(intent);
                    StartActivity.this.finish();
                }
            }
        }, START_DELAY_SECONDS);

    }


}
