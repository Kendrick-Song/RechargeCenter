package com.example.rechargecenter;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RechargeHfFragment extends Fragment {

    private RecyclerView rv_recharge;
    private Button btn_recharge;
    private EditText et_phone;
    private ImageButton ib_phone_list;
    private String username,usernumber;
    private int[] amounts = new int[]{30, 50, 100, 200, 300, 500};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recharge_hf, container, false);
        initView(root);

        loadContacts(root);
        //充值金额选项界面设置
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        rv_recharge.setHasFixedSize(true);
        rv_recharge.setLayoutManager(gridLayoutManager);
        final ItemAdapter itemAdapter = new ItemAdapter(amounts);
        rv_recharge.setAdapter(itemAdapter);
        //点击页面空白处收起软键盘
        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                getActivity().onTouchEvent(motionEvent);
                return false;
            }
        });

        btn_recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // validate
                ItemAdapter ad = new ItemAdapter(amounts);
                String phone = et_phone.getText().toString().trim();
                if (phone.length() != 11) {
                    Toast.makeText(getActivity(), "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getActivity(), RechargePayActivity.class);
                    intent.putExtra("amount", amounts[itemAdapter.getSelect()]);
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
    }

    //点击小人头从通讯录中选择联系人
    private void loadContacts(View view){
        ib_phone_list = view.findViewById(R.id.phone_list);
        ib_phone_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent,1000);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1000){
            if (resultCode==Activity.RESULT_OK){
                if (data!=null){
                    Uri uri=data.getData();
                    String contact=getPhoneContacts(uri);
                    if (contact!=null){
                        et_phone.setText(contact);
                    }
                }
            }
        }
    }

    private String getPhoneContacts(Uri uri){
        String contact ;
        //得到ContentResolver对象
        ContentResolver cr = getActivity().getContentResolver();
        Cursor cursor = cr.query(uri, null, null, null, null);
        if (cursor != null&&cursor.moveToFirst()) {
            contact=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            cursor.close();
        } else {
            return null;
        }
        return contact;
    }
}