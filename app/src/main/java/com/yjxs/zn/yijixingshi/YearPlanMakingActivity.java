package com.yjxs.zn.yijixingshi;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class YearPlanMakingActivity extends BaseActivity {

    private TextView year_title;
    private ImageView year_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_plan_making);
        initControls();
    }


    private void initControls(){
        year_title = (TextView) this.findViewById(R.id.text_year_plan);
        year_back = (ImageView) this.findViewById(R.id.year_plan_back);
        year_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        year_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }





    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(YearPlanMakingActivity.this,MainActivity.class);
        startActivity(intent);
        YearPlanMakingActivity.this.finish();
    }
}
