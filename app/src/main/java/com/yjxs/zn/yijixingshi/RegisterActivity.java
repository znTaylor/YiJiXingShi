package com.yjxs.zn.yijixingshi;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yjxs.zn.yijixingshi.util.CommonUtil;
import com.yjxs.zn.yijixingshi.util.HttpUtil;
import com.yjxs.zn.yijixingshi.view.LoadingDialog;

import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class RegisterActivity extends Activity {

    private Button registerButton,checkUserName;

    //form
    private EditText userName,password,email,passwordConfirm;
    CharSequence strUserName,strPassword,strPasswordConfirm,strEmail;
    LoadingDialog dialog;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                Bundle data = msg.getData();
                String val = data.getString("result");
                Log.i("RegisterActivity", "请求结果为-->" + val);
                dialog.dismiss();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // UI界面的更新等相关操作
                try {
                    JSONObject jsonResult = new JSONObject(val);

                    if (jsonResult.getString("result").equals("0")) { //用户名可用
                        Toast toast = CommonUtil.showToast(RegisterActivity.this, "用户名可用", true);
                        toast.show();

                    } else if (jsonResult.getString("result").equals("1")) {
                        Toast toast = CommonUtil.showToast(RegisterActivity.this, "用户名已存在", false);
                        toast.show();
                    } else {
                        Toast toast = CommonUtil.showToast(RegisterActivity.this, "用户名检查失败", false);
                        toast.show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } //end of msg.what == 100

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initControls();

        //检查用户名
        checkUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strUserName = userName.getText();
                if (TextUtils.isEmpty(strUserName))
                {
                    Toast toast = CommonUtil.showToast(RegisterActivity.this,"用户名不能为空",false);
                    toast.show();
                    return;
                }


                dialog = new LoadingDialog(RegisterActivity.this,
                        R.style.loading_dialog,getString(R.string.register_loading_check_user));
                dialog.show();

                checkUserName();

            }
        });

        // 注册
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strUserName = userName.getText();
                if (TextUtils.isEmpty(strUserName))
                {
                    Toast toast = CommonUtil.showToast(RegisterActivity.this,"用户名不能为空",false);
                    toast.show();
                    return;
                }
                strEmail = email.getText();
                if (TextUtils.isEmpty(strEmail)){
                    Toast toast = CommonUtil.showToast(RegisterActivity.this,"邮箱不能为空",false);
                    toast.show();
                    return;
                }
                strPassword = password.getText();
                if (TextUtils.isEmpty(strPassword)){
                    Toast toast = CommonUtil.showToast(RegisterActivity.this,"密码不能为空",false);
                    toast.show();
                    return;
                }
                strPasswordConfirm = passwordConfirm.getText();
                if (TextUtils.isEmpty(strPasswordConfirm)){
                    Toast toast = CommonUtil.showToast(RegisterActivity.this,"密码输入不一致",false);
                    toast.show();
                    return;
                }

                //检查邮箱
                if (!CommonUtil.checkEmail(strEmail.toString())){
                    Toast toast = CommonUtil.showToast(RegisterActivity.this,"邮箱格式不正确",false);
                    toast.show();
                    return;
                }
                //检查密码的一致性
                if (!strPassword.equals(strPasswordConfirm)){
                    Toast toast = CommonUtil.showToast(RegisterActivity.this,"密码输入不一致",false);
                    toast.show();
                    return;
                }

                dialog = new LoadingDialog(RegisterActivity.this,
                        R.style.loading_dialog,getString(R.string.register_save_user));
                dialog.show();
                //POST方式注册
                saveRegisterUser();


            }
        });
    }

    private void initControls()
    {
        checkUserName = (Button) findViewById(R.id.button_check_username);
        registerButton = (Button) findViewById(R.id.button_register);

        userName = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.user_password);
        passwordConfirm = (EditText) findViewById(R.id.user_password_confirm);
        email = (EditText) findViewById(R.id.user_email);
    }

    private void checkUserName()
    {
        Runnable networkTask = new Runnable() {
            @Override
            public void run() {
                String strUrl = "http://120.27.46.194/ZnConsole/business/check_username.php";
                String params = "username=" + strUserName;
                String response = HttpUtil.sendHttpGet(strUrl, params);

                Message msg = new Message();
                msg.what = 100;
                Bundle data = new Bundle();
                data.putString("result", response);
                msg.setData(data);
                handler.sendMessage(msg);


            }
        };

        new Thread(networkTask).start();
    }


    private void saveRegisterUser(){
        Runnable networkTask = new Runnable() {
            @Override
            public void run() {
                String strUrl = "http://120.27.46.194/ZnConsole/business/register_user.php";


            }
        };

        new Thread(networkTask).start();
    }

}
