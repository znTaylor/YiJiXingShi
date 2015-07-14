package com.yjxs.zn.yijixingshi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MonthPlanMakingActivity extends BaseActivity {
    private ImageView month_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_plan_making);
        initControls();

    }



    private void initControls(){
        month_back = (ImageView) this.findViewById(R.id.month_plan_back);
        month_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MonthPlanMakingActivity.this,MainActivity.class);
        startActivity(intent);
        MonthPlanMakingActivity.this.finish();
    }
}
