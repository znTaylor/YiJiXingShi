package com.yjxs.zn.yijixingshi.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yjxs.zn.yijixingshi.R;

/**
 * Fragment of my plan options
 */
public class MyPlanFragment extends Fragment {


    private static MyPlanFragment instance = null;
    public MyPlanFragment() {
    }

    public static MyPlanFragment getInstance(){
        if (instance == null){
            instance = new MyPlanFragment();
            return instance;
        }
        else {
            return instance;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_plan, container, false);
    }


}
