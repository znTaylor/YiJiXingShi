package com.yjxs.zn.yijixingshi.util;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yjxs.zn.yijixingshi.R;

public class CommonUtil {

    public static Toast showToast(Context context,String content,boolean status){
        Toast toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
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

}
