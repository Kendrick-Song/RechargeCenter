package com.example.rechargecenter.ui.order;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.rechargecenter.OrderItemAdapter;
import com.example.rechargecenter.R;

import java.util.Objects;

public class OrderFragment extends Fragment {

    private RecyclerView rv_order;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_order, container, false);

        //设置标题
        TextView title_tv = Objects.requireNonNull(getActivity()).findViewById(R.id.title_tv);
        title_tv.setText("服务订单");

        //订单RecyclerView
        rv_order = root.findViewById(R.id.rv_order);
        rv_order.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_order.setLayoutManager(manager);
        OrderItemAdapter itemAdapter = new OrderItemAdapter();
        rv_order.setAdapter(itemAdapter);

        return root;
    }
}