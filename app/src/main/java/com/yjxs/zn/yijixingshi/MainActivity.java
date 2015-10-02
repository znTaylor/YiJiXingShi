package com.yjxs.zn.yijixingshi;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yjxs.zn.yijixingshi.fragment.MainFragment;
import com.yjxs.zn.yijixingshi.fragment.MeFragment;
import com.yjxs.zn.yijixingshi.fragment.MyPlanFragment;

/**
 * 主界面
 * author--zn
 * 2016-07-02
 * */
public class MainActivity extends BaseActivity{

    private long exitTime = 0;
    private static MainActivity mMainActivity = null;
    private Context mContext;

    //Components
    LinearLayout linearHome,linearPlan,linearMe;
    ImageView imgHome,imgPlan,imgMe;
    ImageView imgBack;
    TextView txtActionBarTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainActivity = this;
        mContext = this;
        setCurrentFragment(MainFragment.getInstance());
        initControls();

    }

    /**
     * 初始化控件
     * */
    private void initControls() {
        txtActionBarTitle = (TextView) findViewById(R.id.actionbar_title);
        linearPlan = (LinearLayout) findViewById(R.id.linear_bottom_plan);
        linearHome = (LinearLayout) findViewById(R.id.linear_bottom_home);
        linearMe = (LinearLayout) findViewById(R.id.linear_bottom_me);

        imgHome = (ImageView) findViewById(R.id.img_bottom_home);
        imgPlan = (ImageView) findViewById(R.id.img_bottom_plan);
        imgMe = (ImageView) findViewById(R.id.img_bottom_me);


        linearHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setCurrentFragment(MainFragment.getInstance());
                setActionBarTitle(getString(R.string.activity_main_bottom_bar_home_text));
                setBottomBarBackground(true,false,false);
            }
        });
        linearPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentFragment(MyPlanFragment.getInstance());
                setActionBarTitle(getString(R.string.activity_main_bottom_bar_plan_text));
                setBottomBarBackground(false,true,false);
            }
        });

        linearMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentFragment(MeFragment.getInstance());
                setActionBarTitle(getString(R.string.activity_main_bottom_bar_me_text));
                setBottomBarBackground(false, false, true);
            }
        });

        setActionBarTitle(getString(R.string.activity_main_bottom_bar_home_text));
    }


    /**
     * set title of actionbar in mainactivity
     * @param s
     *     title of current fragment or activity
     * */
    public void setActionBarTitle(String s){

        txtActionBarTitle.setText(s);
    }

    /**
     * 设定底部导航的背景
     * @param homeSelected
     *     选中了Home
     * @param planSelected
     *     选中了 my plan
     * @param meSelected
     *     选中了me
     * */
    public void setBottomBarBackground(boolean homeSelected,boolean planSelected,boolean meSelected){
        if (homeSelected){
            imgHome.setBackgroundResource(R.mipmap.home_select);
        }
        else{
            imgHome.setBackgroundResource(R.mipmap.home);
        }

        if (planSelected){
            imgPlan.setBackgroundResource(R.mipmap.my_plan_select);
        }
        else{
            imgPlan.setBackgroundResource(R.mipmap.my_plan);
        }

        if (meSelected){
            imgMe.setBackgroundResource(R.mipmap.me_select);
        }
        else{
            imgMe.setBackgroundResource(R.mipmap.me);
        }

    }

    /**
     * set current fragment in mainactivity
     * @param fragment
     *     要显示的fragment
     * */
    public void setCurrentFragment(Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
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
            Toast.makeText(getApplicationContext(), mContext.getString(
                            R.string.activity_main_press_again_exit),
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

}
