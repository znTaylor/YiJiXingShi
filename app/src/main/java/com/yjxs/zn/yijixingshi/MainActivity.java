package com.yjxs.zn.yijixingshi;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yjxs.zn.yijixingshi.fragment.MainFragment;

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
