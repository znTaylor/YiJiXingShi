package com.yjxs.zn.yijixingshi;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yjxs.zn.yijixingshi.fragment.LoginFragment;
import com.yjxs.zn.yijixingshi.fragment.MainFragment;
import com.yjxs.zn.yijixingshi.fragment.MeFragment;
import com.yjxs.zn.yijixingshi.fragment.MyPlanFragment;
import com.yjxs.zn.yijixingshi.util.CommonUtil;
import com.yjxs.zn.yijixingshi.util.Constants;
import com.yjxs.zn.yijixingshi.util.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

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
    AlertDialog loginDialog = null;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == Constants.MESSAGE_HANDLER_WHAT_LOGIN) {
                Bundle data = msg.getData();
                String val = data.getString("result");
                Log.i("LoginActivity", "请求结果为-->" + val);

                CommonUtil.Sleep(1000);

                // UI界面的更新等相关操作
                try {
                    JSONObject jsonResult = new JSONObject(val);
                    if (jsonResult.getString("result").equals("success")) {
                        Toast toast = CommonUtil.showToast(mContext, getString(R.string.login_success), true);
                        toast.show();

                        YiJiXingShiApp.isUserLogin = true;
                        YiJiXingShiApp.currUser = CommonUtil.ReadSharedPreferences(mContext,"login_user");

                        //加载默认frgment
                        MainActivity.getInstance().setCurrentFragment(MainFragment.getInstance());

                    } else if (jsonResult.getString("result").equals("wrong user")) {
                        Toast toast = CommonUtil.showToast(mContext, getString(R.string.login_user_or_passwd_error),
                                false);
                        toast.show();
                    } else {
                        Toast toast = CommonUtil.showToast(mContext, jsonResult.getString("result"), false);
                        toast.show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        //如果不加这句的话，从一个fragment跳转到另一个fragment时，会提示MainActivity已销毁
        mMainActivity = this;
        setCurrentFragment(MainFragment.getInstance());
        initControls();
        checkLogin();

    }

    public static MainActivity getInstance(){
        if (mMainActivity == null){
            mMainActivity = new MainActivity();
            return mMainActivity;
        }
        else{
            return mMainActivity;
        }
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

    /**
     * 检查用户是否已经登录
     * */
    public void checkLogin(){
        String isLogin = CommonUtil.ReadSharedPreferences(mContext, "is_login");
        if (isLogin.equals("yes")){
            String user = CommonUtil.ReadSharedPreferences(mContext,"login_user");
            String pass = CommonUtil.ReadSharedPreferences(mContext,"login_passwd");
            autoLogin(user,pass);
        }
        else{
            //进入登录fragment
            setCurrentFragment(LoginFragment.getInstence());
        }
    }

    /**
     * auto login
     * */
    private void autoLogin(final String user,final String pass){
        Runnable networkTask = new Runnable() {
            @Override
            public void run() {
                String strUrl = Constants.HTTP_REQUEST_PATH_BUSINESS + "login_check.php";
                String params = "username=" + user;
                params += "&password=" + pass;
                String response = HttpUtil.sendHttpPost(strUrl, params);

                Message msg = new Message();
                msg.what = Constants.MESSAGE_HANDLER_WHAT_LOGIN;
                Bundle data = new Bundle();
                data.putString("result", response);
                msg.setData(data);
                handler.sendMessage(msg);


            }
        };

        new Thread(networkTask).start();
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
