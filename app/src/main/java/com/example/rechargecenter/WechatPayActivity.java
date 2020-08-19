package com.example.rechargecenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class WechatPayActivity extends AppCompatActivity {

    private String url;
    WebView webView;
    boolean wxStatus;

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
                            Map<String, String> extraHeaders = new HashMap<>();
                            extraHeaders.put("Referer", "http://zhongtai.syt1000.com");
                            webView.loadUrl(url, extraHeaders);
                            webView.setWebViewClient(new WebViewClient() {
                                @Override
                                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                    try {
                                        if (url.startsWith("weixin://wap/pay?")) {
                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                            startActivity(intent);
                                            wxStatus = true;
                                            return true;
                                        } else if (url.startsWith("https://wx.tenpay.com")) {
                                            Map<String, String> extraHeaders = new HashMap<>();
                                            extraHeaders.put("Referer", "http://zhongtai.syt1000.com");
                                            view.loadUrl(url, extraHeaders);
                                            wxStatus = true;
                                            return true;
                                        }
                                    } catch (Exception e) {
                                        //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                                        wxStatus = false;
                                        return true;//没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
                                    }
                                    // 在APP内部打开链接，不要调用系统浏览器
                                    view.loadUrl(url);
                                    return true;
                                }
                            });
                        } else {
                            Toast.makeText(WechatPayActivity.this, "网络请求错误，请稍后再试", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wxStatus) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(WechatPayActivity.this);
            dialog.setTitle("支付结果确认");
            dialog.setMessage("是否成功支付？");
            dialog.setCancelable(false);
            dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(WechatPayActivity.this, RechargeResultActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            dialog.show();
        }
    }
}