package com.yjxs.zn.yijixingshi.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yjxs.zn.yijixingshi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private static MainFragment  instance= null;
    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment getInstance(){
        if (instance != null){
            return instance;
        }
        else{
            instance =  new MainFragment();
            return instance;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }


}
