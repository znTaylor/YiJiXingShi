package com.yjxs.zn.yijixingshi;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yjxs.zn.yijixingshi.fragment.YearPlanMonthDetailOneFragment;
import com.yjxs.zn.yijixingshi.fragment.YearPlanMonthDetailThreeFragment;
import com.yjxs.zn.yijixingshi.fragment.YearPlanMonthDetailTwoFragment;


public class YearPlanMakingActivity extends BaseActivity {

    private TextView year_title;
    private ImageView year_back;

    private Button yearPlanPartOne,yearPlanPartTwo,yearPlanPartThree;

    private YearPlanMonthDetailOneFragment yearPlanOneFragment;
    private YearPlanMonthDetailTwoFragment yearPlanTwoFragment;
    private YearPlanMonthDetailThreeFragment yearPlanThreeFragment;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_plan_making);
        initControls();
    }


    private void initControls(){

        fragmentManager = getFragmentManager();
        yearPlanOneFragment = new YearPlanMonthDetailOneFragment();
        yearPlanTwoFragment = new YearPlanMonthDetailTwoFragment();
        yearPlanThreeFragment = new YearPlanMonthDetailThreeFragment();

        //初始fragment 1-4
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.year_plan_detail_frame,yearPlanOneFragment);
        fragmentTransaction.commit();

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

        yearPlanPartOne = (Button) this.findViewById(R.id.year_month_1);
        yearPlanPartOne.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.year_plan_detail_frame,yearPlanOneFragment);
                fragmentTransaction.commit();
            }
        });
        yearPlanPartTwo = (Button) this.findViewById(R.id.year_month_2);
        yearPlanPartTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.year_plan_detail_frame,yearPlanTwoFragment);
                fragmentTransaction.commit();
            }
        });
        yearPlanPartThree = (Button) this.findViewById(R.id.year_month_3);
        yearPlanPartThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.year_plan_detail_frame,yearPlanThreeFragment);
                fragmentTransaction.commit();
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
