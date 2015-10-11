package com.yjxs.zn.yijixingshi.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yjxs.zn.yijixingshi.MainActivity;
import com.yjxs.zn.yijixingshi.R;
import com.yjxs.zn.yijixingshi.YiJiXingShiApp;
import com.yjxs.zn.yijixingshi.util.CommonUtil;
import com.yjxs.zn.yijixingshi.util.Constants;
import com.yjxs.zn.yijixingshi.util.HttpUtil;
import com.yjxs.zn.yijixingshi.view.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Login fragment.
 */
public class LoginFragment extends Fragment {


    private final static String TAG = "LoginFragment";
    private static LoginFragment instancce = null;
    private Context mContext;

    private EditText userName,userPasswd;
    private Button login;
    private TextView forgetPasswd,registerUser;

    LoadingDialog dialog;
    private String strUserName,strPassword;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == Constants.MESSAGE_HANDLER_WHAT_LOGIN) {
                Bundle data = msg.getData();
                String val = data.getString("result");
                Log.i("LoginActivity", "请求结果为-->" + val);

                dialog.dismiss();

                CommonUtil.Sleep(1000);

                // UI界面的更新等相关操作
                try {
                    JSONObject jsonResult = new JSONObject(val);
                    if (jsonResult.getString("result").equals("success")) {
                        Toast toast = CommonUtil.showToast(mContext, getString(R.string.login_success), true);
                        toast.show();

                        //保存用户的登录信息
                        CommonUtil.WriteSharedPreferences(mContext,
                                "login_user", strUserName);
                        CommonUtil.WriteSharedPreferences(mContext,
                                "is_login", "yes");
                        YiJiXingShiApp.isUserLogin = true;
                        YiJiXingShiApp.currUser = strUserName;

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


    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment getInstence(){
        if (instancce == null){
            instancce = new LoginFragment();
        }
        return instancce;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_login,container,false);
        mContext = LoginFragment.this.getActivity();
        init(view);
        MainActivity.getInstance().setActionBarTitle(getString(R.string.login_button_text));
        return view;
    }

    /**
     * 初始化组件
     * */
    private void init(View v){
        userName = (EditText) v.findViewById(R.id.user_name);
        userPasswd = (EditText) v.findViewById(R.id.user_password);
        forgetPasswd = (TextView) v.findViewById(R.id.forget_passwd);
        registerUser = (TextView) v.findViewById(R.id.reg_user);
        login = (Button) v.findViewById(R.id.button_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strUserName = userName.getText().toString();
                strPassword = userPasswd.getText().toString();

                if (TextUtils.isEmpty(strUserName)){
                    Toast toast = CommonUtil.showToast(mContext, getString(R.string.login_username_empty)
                            , false);
                    toast.show();

                    return;
                }
                if (TextUtils.isEmpty(strPassword)){
                    Toast toast = CommonUtil.showToast(mContext, getString(R.string.login_passwd_empty),
                            false);
                    toast.show();

                    return;
                }

                dialog = new LoadingDialog(mContext,
                        R.style.loading_dialog,getString(R.string.login_checking));
                dialog.show();
                //登录
                login();

            }
        });
        forgetPasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * login
     * */
    private void login(){
        Runnable networkTask = new Runnable() {
            @Override
            public void run() {
                String strUrl = Constants.HTTP_REQUEST_PATH_BUSINESS + "login_check.php";
                String params = "username=" + strUserName;
                params += "&password=" + strPassword;
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


}
