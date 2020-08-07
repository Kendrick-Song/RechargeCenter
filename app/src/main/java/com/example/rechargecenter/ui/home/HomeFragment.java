package com.example.rechargecenter.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.rechargecenter.BottomNavActivity;
import com.example.rechargecenter.R;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        BottomNavActivity bottomNavActivity = (BottomNavActivity) getActivity();
        if (bottomNavActivity != null) {
            bottomNavActivity.initTab(root.findViewById(R.id.recharge_tab),root.findViewById(R.id.recharge_pager));
        }
        return root;
    }
}