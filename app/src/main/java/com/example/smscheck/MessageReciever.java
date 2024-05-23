package com.example.smscheck;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MessageReciever extends BroadcastReceiver {

    private static final String SMS_RECEIVER_ACTION = "android.provider.Telephony.SMS_RECEIVED";
    @Override
    public void onReceive(Context context, Intent intent) {
        MessageService ms = (MessageService) context;
        String keywords = ms.getKeywords();
        String phoneNum = ms.getPhoneNum();
        Boolean allCheck = ms.getAllCheck();
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(keywords).append("\n");
        sBuilder.append(phoneNum).append("\n");
        sBuilder.append(allCheck).append("\n");
        String format = intent.getStringExtra("format");
        String[] allKeyword = keywords.split("，");
        if(SMS_RECEIVER_ACTION.equals(intent.getAction()))
        {
            Bundle bundle = intent.getExtras();
            if(null != bundle)
            {
                Object[] pdus = (Object[])bundle.get("pdus");
                assert pdus != null;
                SmsMessage[] messages = new SmsMessage[pdus.length];
                for(int i = 0; i < messages.length; ++i)
                {
                    messages[i] = SmsMessage.createFromPdu((byte[])pdus[i],format);
                }
                for(SmsMessage msg : messages)
                {
                    String msgBody = msg.getDisplayMessageBody();
                    int checked = 0;
                    for (String keyword : allKeyword){
                        if (msgBody.contains(keyword)){
                            checked++;
                        }
                    }
                    if ((!allCheck && checked != 0) || (allCheck && checked == allKeyword.length)){
                        Intent call = new Intent();
                        call.setAction(Intent.ACTION_CALL);
                        call.setData(Uri.parse("tel:"+phoneNum));
                        call.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                        context.startActivity(call);
                        sBuilder.append("【严重告警】").append(msgBody).append("\n");

                    }
                }
            }
        }
        Toast.makeText(context, "您收到了一条短信!!\n" + sBuilder.toString(), Toast.LENGTH_LONG).show();
    }

}
