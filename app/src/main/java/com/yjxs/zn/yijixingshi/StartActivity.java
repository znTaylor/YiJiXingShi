package com.yjxs.zn.yijixingshi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;


public class StartActivity extends Activity {


    private static final int START_DELAY_SECONDS = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.start);

        //ÔÝÍ£3Ãëºó£¬Ìø×ªµ½MainActivity
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(StartActivity.this, CheckLoginActivity.class);
                StartActivity.this.startActivity(intent);
                StartActivity.this.finish();
            }
        }, START_DELAY_SECONDS);

    }


}
