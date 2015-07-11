package com.yjxs.zn.yijixingshi.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.yjxs.zn.yijixingshi.R;


public class LoadingDialog extends Dialog
{
    private Context mContext;
    private  String titleText;
    private TextView showTitle;
    public LoadingDialog(Context context)
    {
        super(context);
        mContext = context;
    }
    public LoadingDialog(Context context, int theme)
    {
        super(context, theme);
        mContext = context;
    }
    public LoadingDialog(Context context, int theme,String text)
    {
        super(context, theme);
        mContext = context;
        titleText = text;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.layout_loading_dialog, null);
        ImageView loadingImage = (ImageView) v.findViewById(R.id.loading_img);
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.loading_animation);
        loadingImage.setAnimation(animation);

        setContentView(v);
        showTitle = (TextView) v.findViewById(R.id.loading_text);

        showTitle.setText(titleText);

    }


}
