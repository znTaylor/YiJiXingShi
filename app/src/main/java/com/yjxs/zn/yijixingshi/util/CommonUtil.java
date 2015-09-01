package com.yjxs.zn.yijixingshi.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yjxs.zn.yijixingshi.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CommonUtil {

    public static Toast showToast(Context context, String content, boolean status) {
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        View v = View.inflate(context, R.layout.layout_custom_toast, null);
        ImageView image = (ImageView) v.findViewById(R.id.toast_image);
        TextView text = (TextView) v.findViewById(R.id.toast_text);
        if (status) {
            image.setBackgroundResource(R.mipmap.ok);
        } else {
            image.setBackgroundResource(R.mipmap.fail);
        }
        text.setText(content);
        toast.setView(v);

        return toast;
    }

    public static boolean checkEmail(String email) {
        boolean tag = true;
        final String pattern1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        final Pattern pattern = Pattern.compile(pattern1);
        final Matcher mat = pattern.matcher(email);
        if (!mat.find()) {
            tag = false;
        }
        return tag;
    }

    public static void WriteSharedPreferences(Context context, String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences("yijixingshi", context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }


    public static String ReadSharedPreferences(Context context, String key) {
        SharedPreferences preference = context.getSharedPreferences("yijixingshi", context.MODE_PRIVATE);
        return preference.getString(key, "");
    }

    public static void Sleep(long miliSeconds){
        try{
            Thread.sleep(miliSeconds);
        } catch(InterruptedException e){

        }
    }
}