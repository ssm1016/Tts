package com.cookandroid.cookmap.tts;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;
/**
 * Created by 507 on 2017-12-07.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String sMsg;
        switch (getResultCode()) {
            case Activity.RESULT_OK:
                sMsg = "Success!";
                break;
            case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                sMsg = "Failed!";
                break;
            default:
                sMsg = "UnKonown error";
        }
        Toast.makeText(context, sMsg, Toast.LENGTH_LONG).show();

    }
}



