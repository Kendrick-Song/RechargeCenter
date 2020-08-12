package com.example.rechargecenter;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class ContactsActivity extends AppCompatActivity {

    EditText phone_number;
    String username,usernumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phone_number = findViewById(R.id.input_phone);

        Intent intent=new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(intent,0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0){
            if (resultCode==RESULT_OK){
                if (data!=null){
                    Uri uri=data.getData();
                    String[] contact=getPhoneContacts(uri);
                    if (contact!=null){
                        phone_number.setText(contact[1]);
                    }
                }
            }
        }
    }

    private String[] getPhoneContacts(Uri uri){
        String[] contact = new String[2];
        //得到ContentResolver对象
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(uri, null, null, null, null);
        if (cursor != null&&cursor.moveToFirst()) {
            //取得联系人姓名
            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            contact[0] = cursor.getString(nameFieldColumnIndex);
            contact[1]=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Log.i("contacts",contact[0]);
            Log.i("contactsUsername",contact[1]);
            cursor.close();
        } else {
            return null;
        }
        return contact;
    }
}