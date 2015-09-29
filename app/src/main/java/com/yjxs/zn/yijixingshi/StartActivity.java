package com.yjxs.zn.yijixingshi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.yjxs.zn.yijixingshi.util.CommonUtil;


public class StartActivity extends BaseActivity {


    private static final int START_DELAY_SECONDS = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.start);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                StartActivity.this.startActivity(intent);
                StartActivity.this.finish();

            }
        }, START_DELAY_SECONDS);

    }


}
