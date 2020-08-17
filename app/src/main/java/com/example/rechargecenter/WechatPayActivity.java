package com.example.rechargecenter;

import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class WechatPayActivity extends AppCompatActivity {

    private String url;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechat_pay);

        //隐藏系统自带标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        //返回Button
        ImageButton title_btn_back = findViewById(R.id.title_btn_back);
        title_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //显示支付页面
        webView = findViewById(R.id.wv_wechat_pay);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        WebSettings mSettings = webView.getSettings();

        // 支持JS
        mSettings.setJavaScriptEnabled(true);
        mSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mSettings.setBuiltInZoomControls(true);
        mSettings.setDisplayZoomControls(true);
        mSettings.setLoadWithOverviewMode(true);
        // 自适应屏幕
        mSettings.setUseWideViewPort(true);
        mSettings.setLoadWithOverviewMode(true);
        // 支持缩放
        mSettings.setSupportZoom(false);//就是这个属性把我搞惨了，
        // 隐藏原生缩放控件
        mSettings.setDisplayZoomControls(false);
        // 支持内容重新布局
        mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mSettings.supportMultipleWindows();
        mSettings.setSupportMultipleWindows(true);
        // 设置缓存模式
        mSettings.setDomStorageEnabled(true);
        mSettings.setDatabaseEnabled(true);
        mSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        mSettings.setAppCacheEnabled(true);
        mSettings.setAppCachePath(this.getCacheDir().getAbsolutePath());
        // 设置可访问文件
        mSettings.setAllowFileAccess(true);
        mSettings.setNeedInitialFocus(true);
        mSettings.setBlockNetworkImage(false);
        // 支持自定加载图片
        if (Build.VERSION.SDK_INT >= 19) {
            mSettings.setLoadsImagesAutomatically(true);
        } else {
            mSettings.setLoadsImagesAutomatically(false);
        }
        mSettings.setNeedInitialFocus(true);
        //设置默认编码
        mSettings.setDefaultTextEncodingName("utf-8");

        //请求支付页面
        WxPay();
    }

    void WxPay() {
        String mobile = getIntent().getStringExtra("mobile");
        String recharge_amount = getIntent().getStringExtra("recharge_amount");
        OkHttpUtils
                .post()
                .url("http://zhongtai.syt1000.com/Api/Applets/hfpay")
                .addParams("mobile", mobile)
                .addParams("recharge_amount", recharge_amount)
                .addParams("alias", "yt00003")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(WechatPayActivity.this, "网络请求错误，请稍后再试", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        //处理返回数据
                        JSONObject object = JSON.parseObject(response);
                        if (object.getIntValue("status") == 1) {
                            url = JSON.parseObject(object.getString("result")).getString("timeStamp");
                            webView.loadUrl(url);
                        } else {
                            Toast.makeText(WechatPayActivity.this, "网络请求错误，请稍后再试", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}