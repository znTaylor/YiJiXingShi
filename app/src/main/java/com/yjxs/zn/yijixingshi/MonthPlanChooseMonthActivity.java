package com.yjxs.zn.yijixingshi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.yjxs.zn.yijixingshi.adapter.MonthPlanDetailOfCurrYearAdapter;

import java.util.ArrayList;


public class MonthPlanChooseMonthActivity extends BaseActivity {

    private ListView monthsList;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_plan_choose_month);
        Intent i = getIntent();
        initControls(i);

    }

    private void initControls(Intent intent){
        String[] temp = intent.getStringArrayExtra("planList");
        ArrayList<String> tempData = new ArrayList<String>();
        for (int i=0; i<temp.length; i++){
            tempData.add(i,temp[i]);
        }

        monthsList = (ListView) this.findViewById(R.id.listview_12_months);
        MonthPlanDetailOfCurrYearAdapter adapter = new MonthPlanDetailOfCurrYearAdapter(
                this,
                R.layout.layout_month_list_item,
                tempData
        );
        monthsList.setAdapter(adapter);
        monthsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MonthPlanChooseMonthActivity.this,MonthPlanInitActivity.class);
                intent.putExtra("whichMonth",(position+1));
                startActivity(intent);
                MonthPlanChooseMonthActivity.this.finish();
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




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MonthPlanChooseMonthActivity.this,MonthPlanInitActivity.class);
        startActivity(intent);
        MonthPlanChooseMonthActivity.this.finish();
    }


}
