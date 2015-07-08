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

import com.yjxs.zn.yijixingshi.view.LoadingDialog;

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
    CharSequence strUserName;
    LoadingDialog dialog;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("result");
            Log.i("RegisterActivity", "请求结果为-->" + val);
            dialog.dismiss();
            // UI界面的更新等相关操作
            try {
                JSONObject jsonResult = new JSONObject(val);

                if (jsonResult.getString("result").equals("0")) { //用户名可用
                    Toast.makeText(RegisterActivity.this,"用户名可用",Toast.LENGTH_SHORT).show();
                }
                else if (jsonResult.getString("result").equals("1")){
                    Toast.makeText(RegisterActivity.this,"用户名已存在",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(RegisterActivity.this,"用户名检查失败",Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e){
                e.printStackTrace();
            }




        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initControls();

        checkUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strUserName = userName.getText();
                if (TextUtils.isEmpty(strUserName))
                {
                    Toast.makeText(RegisterActivity.this,getString(R.string.register_username_null),
                            Toast.LENGTH_SHORT).show();
                    return;
                }


                dialog = new LoadingDialog(RegisterActivity.this,
                        R.style.loading_dialog,getString(R.string.register_loading_check_user));
                dialog.show();

                checkUserName();

            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                String strUrl = "http://120.27.46.194/ZnConsole/business/check_username.php?username=" +
                        strUserName;
                URL url;
                try {
                    url = new URL(strUrl);
                    HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                    InputStreamReader in = new InputStreamReader(urlConn.getInputStream());
                    BufferedReader bufferReader = new BufferedReader(in);
                    String result = "";
                    String readLine;
                    while ((readLine = bufferReader.readLine()) != null) {
                        result += readLine;
                    }

                    Message msg = new Message();
                    Bundle data = new Bundle();
                    data.putString("result", result);
                    msg.setData(data);
                    handler.sendMessage(msg);

                    in.close();
                    urlConn.disconnect();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        new Thread(networkTask).start();
    }



}
