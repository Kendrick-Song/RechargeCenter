package com.example.rechargecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RechargeHfFragment extends Fragment {

    private RecyclerView rv_recharge;
    private Button btn_recharge;
    private EditText et_phone;
    private int[] amounts = new int[]{30, 50, 100, 200, 300, 500};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recharge_hf, container, false);
        initView(root);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        rv_recharge.setHasFixedSize(true);
        rv_recharge.setLayoutManager(gridLayoutManager);
        rv_recharge.setAdapter(new ItemAdapter(amounts));
        return root;
    }

    private void initView(View view) {
        rv_recharge = view.findViewById(R.id.rv_recharge);
        et_phone = view.findViewById(R.id.input_phone);
        btn_recharge = view.findViewById(R.id.btn_recharge_now);
        btn_recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // validate
                String phone = et_phone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getActivity(), "充值号码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getActivity(), RechargePayActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

}