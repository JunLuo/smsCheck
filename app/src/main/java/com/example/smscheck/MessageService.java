package com.example.smscheck;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class MessageService extends Service {
    private MessageReciever mReceiver;

    private String keywords;
    private String phoneNum;
    private Boolean allCheck;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
    @Override
    public void onCreate() {
        mReceiver = new MessageReciever();
        IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(mReceiver, filter);
    }
    @Override
    public void onDestroy() {
        if(null != mReceiver)
        {
            unregisterReceiver(mReceiver);
            mReceiver = null;
        }
    }

    @SuppressLint("ForegroundServiceType")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.keywords = intent.getStringExtra("keywords");
        this.phoneNum = intent.getStringExtra("phoneNum");
        this.allCheck = intent.getBooleanExtra("allCheck",true);
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public String getKeywords(){
        return this.keywords;
    }

    public String getPhoneNum(){
        return this.phoneNum;
    }
    public Boolean getAllCheck(){
        return this.allCheck;
    }
}

