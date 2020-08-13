package com.example.rechargecenter.ui.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.rechargecenter.R;

import java.util.Objects;

public class OrderFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_order, container, false);
        TextView title_tv = Objects.requireNonNull(getActivity()).findViewById(R.id.title_tv);
        title_tv.setText("服务订单");
        return root;
    }
}