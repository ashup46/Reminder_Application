package com.myprojects.todoreminder.BroadcastReceiver;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.myprojects.todoreminder.MainActivity;
import com.myprojects.todoreminder.R;
import com.myprojects.todoreminder.Values.ResourceValues;

import java.util.Calendar;

public class NotificationBroadcastReceiver extends BroadcastReceiver {



private  Notification notification;

private   Calendar calendar;

private static Ringtone ringtone;

    @Override
    public void onReceive(Context context, Intent intent) {

        settingRepeatingAlarm(context,intent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        createNotificationChannel(notificationManager);
        notification = buildNotification(context,intent).build();
        notificationManager.notify(ResourceValues.NOTIFICATION_ID,notification);

        playRingtone(context);

    }

    private void playRingtone(Context context) {

       Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

       if (alarmUri == null)
       {
           alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
       }

       stopRingtone();

       ringtone = RingtoneManager.getRingtone(context,alarmUri);

       if (ringtone!=null)
       {
           ringtone.play();
       }




    }

    public static void stopRingtone() {

        if (ringtone!=null && ringtone.isPlaying())
        {
            ringtone.stop();
            ringtone = null;
        }
    }

    private void settingRepeatingAlarm(Context context,Intent intent) {

        calendar = Calendar.getInstance();

        AlarmManager alarmManager =  (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Log.d("properIntentGet", "Hours" + intent.getIntExtra("hours",0));
        Log.d("properIntentGet", "minutes" + intent.getIntExtra("minutes",0));
        Log.d("properIntentGet", "dayOfWeek" + intent.getIntExtra("dayOfWeek",0));
        Log.d("properIntentGet", "requestCode" + intent.getIntExtra("requestCode",0));


        calendar.set(Calendar.HOUR_OF_DAY,intent.getIntExtra("hours",0));
        calendar.set(Calendar.MINUTE,intent.getIntExtra("minutes",0));
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        calendar.add(Calendar.WEEK_OF_YEAR,1);
        calendar.set(Calendar.DAY_OF_WEEK,intent.getIntExtra("dayOfWeek",0));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,intent.getIntExtra("requestCode",0),intent,PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

    }
    private NotificationCompat.Builder buildNotification(Context context,Intent intent) {


        PendingIntent pendingIntent = PendingIntent.getActivity(context,ResourceValues.NOTIFICATION_REQUEST_CODE,intent,PendingIntent.FLAG_IMMUTABLE|PendingIntent.FLAG_UPDATE_CURRENT);

//        This is for stoping the ringtone
        Intent dismissintent1 = new Intent(context,CancelNotificationReciever.class);
        PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(context,200,dismissintent1,PendingIntent.FLAG_IMMUTABLE|PendingIntent.FLAG_UPDATE_CURRENT);
        return new  NotificationCompat.Builder(context,ResourceValues.NOTIFICATION_CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setContentText(intent.getStringExtra("ReminderNote"))
                .setSmallIcon(R.drawable.notificationbell)
                .setChannelId(ResourceValues.NOTIFICATION_CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDeleteIntent(dismissPendingIntent);

    }

    private void createNotificationChannel(NotificationManager notificationManager) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(ResourceValues.NOTIFICATION_CHANNEL_ID,"My Notification",NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(notificationChannel);
        }


    }
}
