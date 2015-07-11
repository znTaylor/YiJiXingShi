package com.yjxs.zn.yijixingshi.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.yjxs.zn.yijixingshi.R;

public class PlanTypeDialog extends Dialog {

    private Context context;

    public PlanTypeDialog(Context context) {
        super(context);
        this.context = context;
    }

    public PlanTypeDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    protected PlanTypeDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.layout_plan_type, null);
        setContentView(v);
    }
}
