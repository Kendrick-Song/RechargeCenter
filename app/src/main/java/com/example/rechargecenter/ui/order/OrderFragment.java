package com.example.rechargecenter.ui.order;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.rechargecenter.OrderItemAdapter;
import com.example.rechargecenter.OrderItemBean;
import com.example.rechargecenter.R;
import com.example.rechargecenter.RechargePayActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Calendar;
import java.util.Objects;

import okhttp3.Call;

public class OrderFragment extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
    OrderItemAdapter itemAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_order, container, false);

        //设置标题
        TextView title_tv = Objects.requireNonNull(getActivity()).findViewById(R.id.title_tv);
        title_tv.setText("服务订单");

        getOrder();

        //订单RecyclerView
        RecyclerView rv_order = root.findViewById(R.id.rv_order);
        rv_order.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_order.setLayoutManager(manager);
        itemAdapter = new OrderItemAdapter();
        rv_order.setAdapter(itemAdapter);

        //页面刷新
        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        return root;
    }

    void getOrder() {
//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH) + 1;
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        String time = year + "-" + month + "-" + day;
        OkHttpUtils
                .post()
                .url("http://zhongtai.syt1000.com/api/applets/ApporderList")
                .addParams("alias", "yt00003")
                .addParams("type", "1")
                .addParams("status", "0")
                .addParams("begin_time", "")
                .addParams("end_time", "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getActivity(), "网络请求错误，请稍后再试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JSONObject object = JSON.parseObject(response);
                        if (object.getIntValue("status") == 1) {
                            OrderItemBean orderItemBean = JSON.parseObject(response, OrderItemBean.class);
                            itemAdapter.setResultEntityList(orderItemBean.getResult());
                        }
                    }
                });
    }

    void refresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);  //为体现刷新效果，做了休眠线程
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //切回主线程
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getOrder();
                        itemAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
}