package com.myprojects.todoreminder.Values;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.util.Log;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Calendar;

public class ResourceValues {
    public static final String DB_Name ="reminderDB";
    public static final String TABLE_NAME= "reminderNotesTable";
    public static final String KEY_NOTES= "Notes";
    public static final String KEY_HOURS= "Hours";
    public static final String KEY_MINUTES= "Minutes";

    public static final int VERSION_DB = 1;

    public static final String NOTIFICATION_CHANNEL_ID = "notify_1";

    public static final int NOTIFICATION_REQUEST_CODE = 100;

    public static final int NOTIFICATION_ID = 1;


//    This Method is to getting the list of weeks into the integer value from the Calender.Weeks
public static ArrayList<Integer> gettingWeeksList(String allWeeks) {

    ArrayList<Integer> arrayList = new ArrayList<Integer>();

    if(allWeeks.contains("Sun"))
    {
        arrayList.add(Calendar.SUNDAY);
    }

    if (allWeeks.contains("Mon"))
    {
        arrayList.add(Calendar.MONDAY);

    }
    if(allWeeks.contains("Tue"))
    {
        arrayList.add(Calendar.TUESDAY);
    }
    if(allWeeks.contains("Wed"))
    {
        arrayList.add(Calendar.WEDNESDAY);
    }
    if(allWeeks.contains("Thu"))
    {
        arrayList.add(Calendar.THURSDAY);
    }
    if(allWeeks.contains("Fri"))
    {
        arrayList.add(Calendar.FRIDAY);
    }
    if(allWeeks.contains("Sat"))
    {
        arrayList.add(Calendar.SATURDAY);
    }
return arrayList;

}




}
