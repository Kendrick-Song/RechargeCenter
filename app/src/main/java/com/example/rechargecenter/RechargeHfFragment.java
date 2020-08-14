package com.example.rechargecenter;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class RechargeHfFragment extends Fragment {

    private RecyclerView rv_recharge;
    private Button btn_recharge;
    private EditText et_phone;
    private ImageButton ib_phone_list;
    private TextView phone_area;
    private ItemAdapter itemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_recharge_hf, container, false);

        //初始化view
        initView(root);

        //通讯录获取联系人
        loadContacts(root);

        //从服务器获取数据
        getPrice();

        //充值金额选项界面设置
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        rv_recharge.setHasFixedSize(true);
        rv_recharge.setLayoutManager(gridLayoutManager);
        itemAdapter = new ItemAdapter();
        rv_recharge.setAdapter(itemAdapter);

        //点击页面空白处收起软键盘
        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                getActivity().onTouchEvent(motionEvent);
                return false;
            }
        });

        //重置按钮相关逻辑判断与数据传递
        btn_recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = et_phone.getText().toString().trim();
                if (phone.length() != 11) {
                    //判断号码格式
                    Toast.makeText(getActivity(), "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                } else {
                    //传递所选金额
                    Intent intent = new Intent(getActivity(), RechargePayActivity.class);
                    intent.putExtra("mobile", phone);
                    intent.putExtra("amount", itemAdapter.getResultBeanList().get(itemAdapter.getSelect()).getSs_amount());
                    startActivity(intent);
                }
            }
        });
        return root;
    }

    private void initView(View view) {
        rv_recharge = view.findViewById(R.id.rv_recharge);
        et_phone = view.findViewById(R.id.input_phone);
        btn_recharge = view.findViewById(R.id.btn_recharge_now);
        phone_area = view.findViewById(R.id.phone_area);
    }

    //点击小人头从通讯录中选择联系人
    private void loadContacts(View view) {
        ib_phone_list = view.findViewById(R.id.phone_list);
        ib_phone_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent, 1000);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Uri uri = data.getData();
                    String contact = getPhoneContacts(uri);
                    if (contact != null) {
                        et_phone.setText(contact);
                    }
                }
            }
        }
    }

    private String getPhoneContacts(Uri uri) {
        String contact;
        //得到ContentResolver对象
        ContentResolver cr = getActivity().getContentResolver();
        Cursor cursor = cr.query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            contact = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            cursor.close();
        } else {
            return null;
        }
        return contact;
    }

    void getPrice() {
        OkHttpUtils
                .post()
                .url("https://zhongtai.syt1000.com/Home/Recharge/getNewMobilePriceList")
                .addParams("mobile", "17666275435")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getActivity(), "网络请求错误，请稍后再试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        //处理返回数据
                        PhoneGoodsBean phoneGoodsBean = JSON.parseObject(response, PhoneGoodsBean.class);
                        phone_area.setText("账号绑定号码（" + phoneGoodsBean.getResult().get(0).getProvince() + phoneGoodsBean.getResult().get(0).getOperator() + "）");
                        itemAdapter.setResultBeanList(phoneGoodsBean.getResult());
                    }
                });
    }
}