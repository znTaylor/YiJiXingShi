package com.yjxs.zn.yijixingshi;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class CheckLoginActivity extends Activity {

    private Button headerRegister,headerLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_login);
        headerRegister = (Button) this.findViewById(R.id.button_register);

        headerRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toRegister = new Intent(CheckLoginActivity.this,RegisterActivity.class);
                startActivity(toRegister);
                CheckLoginActivity.this.finish();
            }
        });
        headerLogin = (Button) this.findViewById(R.id.button_login);
        headerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toRegister = new Intent(CheckLoginActivity.this,LoginActivity.class);
                startActivity(toRegister);
                CheckLoginActivity.this.finish();
            }
        });


    }


}
