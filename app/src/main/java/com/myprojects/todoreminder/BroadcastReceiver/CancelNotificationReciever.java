package com.myprojects.todoreminder.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class CancelNotificationReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

            NotificationBroadcastReceiver.stopRingtone();

        Log.d("properIntentGet","i am in Cancel Notification");
    }
}
