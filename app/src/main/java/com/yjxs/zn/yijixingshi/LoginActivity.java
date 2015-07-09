package com.yjxs.zn.yijixingshi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yjxs.zn.yijixingshi.util.CommonUtil;
import com.yjxs.zn.yijixingshi.util.HttpUtil;
import com.yjxs.zn.yijixingshi.view.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private Button btnLogin;
    private EditText username,password;
    LoadingDialog dialog;
    private String strUserName,strPassword;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 102) {
                Bundle data = msg.getData();
                String val = data.getString("result");
                Log.i("LoginActivity", "请求结果为-->" + val);

                dialog.dismiss();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // UI界面的更新等相关操作
                try {
                    JSONObject jsonResult = new JSONObject(val);
                    if (jsonResult.getString("result").equals("success")) {
                        Toast toast = CommonUtil.showToast(LoginActivity.this, "登录成功,赶快管理你的计划吧！", true);
                        toast.show();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        LoginActivity.this.startActivity(intent);
                        LoginActivity.this.finish();

                    } else if (jsonResult.getString("result").equals("wrong user")) {
                        Toast toast = CommonUtil.showToast(LoginActivity.this, "用户名或密码错误！", false);
                        toast.show();
                    } else {
                        Toast toast = CommonUtil.showToast(LoginActivity.this, jsonResult.getString("result"), false);
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
        setContentView(R.layout.layout_login);
        initControls();
    }

    private void initControls(){
        btnLogin = (Button) this.findViewById(R.id.button_login);
        username = (EditText) this.findViewById(R.id.user_name);
        password = (EditText) this.findViewById(R.id.user_password);

        btnLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        strUserName = username.getText().toString();
        strPassword = password.getText().toString();

        if (TextUtils.isEmpty(strUserName)){
            Toast toast = CommonUtil.showToast(LoginActivity.this, "请输入用户名", false);
            toast.show();

            return;
        }
        if (TextUtils.isEmpty(strPassword)){
            Toast toast = CommonUtil.showToast(LoginActivity.this, "请输入密码", false);
            toast.show();

            return;
        }

        dialog = new LoadingDialog(LoginActivity.this,
                R.style.loading_dialog,getString(R.string.login_checking));
        dialog.show();
        //登录
        login();

    }

    private void login(){
        Runnable networkTask = new Runnable() {
            @Override
            public void run() {
                String strUrl = "http://120.27.46.194/ZnConsole/business/login_check.php";
                String params = "username=" + strUserName;
                params += "&password=" + strPassword;
                String response = HttpUtil.sendHttpPost(strUrl, params);

                Message msg = new Message();
                msg.what = 102;
                Bundle data = new Bundle();
                data.putString("result", response);
                msg.setData(data);
                handler.sendMessage(msg);


            }
        };

        new Thread(networkTask).start();
    }
}
