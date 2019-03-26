package com.example.protype;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
     private SmsReceiver smsReceiver;
     private Button setKeyWord;
     private EditText keyWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //判断有无sim卡，没有则直接退出
        TelephonyManager telMgr = (TelephonyManager)
                getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        int simState = telMgr.getSimState();
        boolean result = true;
        switch (simState) {
            case TelephonyManager.SIM_STATE_ABSENT:
                result = false; // 没有SIM卡
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                result = false;
                break;
        }
        if (!result){
            Toast.makeText(this,"You don't have a SIM card in your phone!Please insert one first.",Toast.LENGTH_LONG).show();
            finish();
        }

        setContentView(R.layout.activity_main);
        //检测是否为默认短信引用，
        final String myPackageName = getPackageName();
        Log.i("yjj","defeat+"+Telephony.Sms.getDefaultSmsPackage(this));
        Log.i("yjj",myPackageName);
        String defaultPackage=Telephony.Sms.getDefaultSmsPackage(this);

        //判断非空！
        if (defaultPackage!= null) {
            if (!defaultPackage.equals(myPackageName)) {
                Intent intent =
                        new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
                intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME,
                        myPackageName);
                startActivity(intent);
                Log.d("liueg", "11111111111");
            }
        }else{
            Log.i("yjj","no defeat sms app");
        }

        smsReceiver= new SmsReceiver();

        setKeyWord=findViewById(R.id.button);
        keyWord=findViewById(R.id.myKeyWord);

        setKeyWord.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String word=keyWord.getText().toString().trim();
                if (word.equals("")){
                     Toast.makeText(getApplication(),"the key word can not be empty!",Toast.LENGTH_SHORT).show();
                }
                else{
                SharedPreferences List;
                List= getSharedPreferences("user",0);
                SharedPreferences.Editor editor= List.edit();
                editor.putString("keyword",word);
                editor.commit();

                Toast.makeText(getApplication(),"You have set your key word "+word,Toast.LENGTH_SHORT).show();
                }
            }
        });

        SharedPreferences sp;
        sp=getSharedPreferences("user",0);
        String st=sp.getString("keyword","");
        if (!st.equals(""))keyWord.setText(st);



    }
}
