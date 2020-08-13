package com.example.rechargecenter;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class RechargePayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_pay);

        //隐藏系统自带标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        //设置标题
        TextView title_tv = findViewById(R.id.title_tv);
        title_tv.setText("充值支付");
        //返回Button
        ImageButton title_btn_back = findViewById(R.id.title_btn_back);
        title_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //设置金额
        TextView tv_pay_num = findViewById(R.id.tv_pay_num);
        String amount = String.valueOf(getIntent().getIntExtra("amount",0));
        tv_pay_num.setText(amount + ".00");
        Button btn_confirm_pay = findViewById(R.id.btn_confirm_pay);
        btn_confirm_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RechargePayActivity.this,RechargeResultActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}