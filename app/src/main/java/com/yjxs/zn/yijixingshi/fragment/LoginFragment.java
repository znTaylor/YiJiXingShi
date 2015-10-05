package com.yjxs.zn.yijixingshi.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yjxs.zn.yijixingshi.R;

/**
 * Login fragment.
 */
public class LoginFragment extends Fragment {


    private static LoginFragment instancce = null;
    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment getInstence(){
        if (instancce == null){
            instancce = new LoginFragment();
        }
        return instancce;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_login,container,false);

        return view;
    }


}
