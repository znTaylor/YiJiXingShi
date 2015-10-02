package com.yjxs.zn.yijixingshi.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yjxs.zn.yijixingshi.R;

/**
 * make year plan fragment.
 */
public class MakeYearPlanFragment extends Fragment {


    private static MakeYearPlanFragment instance = null;
    public MakeYearPlanFragment() {
        // Required empty public constructor
    }

    public static MakeYearPlanFragment getInstance(){
        if (instance == null){
            instance = new MakeYearPlanFragment();
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
        return inflater.inflate(R.layout.fragment_make_year_plan, container, false);
    }


}
