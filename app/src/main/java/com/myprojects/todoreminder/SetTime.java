package com.myprojects.todoreminder;

import static androidx.core.app.PendingIntentCompat.getActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.myprojects.todoreminder.BroadcastReceiver.NotificationBroadcastReceiver;
import com.myprojects.todoreminder.RoomDatabaase.DbHelperClass;
import com.myprojects.todoreminder.RoomDatabaase.ModalReminder;
import com.myprojects.todoreminder.Values.ResourceValues;

import java.util.ArrayList;
import java.util.Calendar;

public class SetTime extends AppCompatActivity implements WeekFragment.ClickOnListner {

   private Toolbar toolbar;

    private EditText taskReminder;

    private TextView setTime,setWeek;

    private AppCompatButton setTimeBtn,setWeekBtn,submitBtn;



    private int hours,minutes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_set_time);

        toolbar = findViewById(R.id.actionToolbarNewTaskId);
        taskReminder = findViewById(R.id.taskTodoId);
        setTime = findViewById(R.id.timeEdtId);
        setWeek = findViewById(R.id.WeakEdtId);
        setTimeBtn = findViewById(R.id.timePickerId);
        setWeekBtn = findViewById(R.id.weekPickerId);
        submitBtn = findViewById(R.id.submitId);

//        This is for the support Action bar
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
//      This is for picking up the time

        setTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimeSetDailog();
                Log.d("setTime","i am in setTimebtn Listner");
            }
        });


//        This is for picking up the Week
        setWeekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWeekSetFragment();
            }
        });

//        Setting Up for the Room DataBase

        DbHelperClass dbHelperClass = DbHelperClass.getDbInstance(this);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String notes = taskReminder.getText().toString();
                String time = setTime.getText().toString();
                String week = setWeek.getText().toString();

               int primaryId_key = (int) dbHelperClass.reminderDAO().addNotes(new ModalReminder(time,week,notes));

                Log.d("setTime","id" + week );

                setAlarm(primaryId_key);

                Intent intent = new Intent(SetTime.this,MainActivity.class);
                startActivity(intent);
                finish();


            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setAlarm( int primaryId_key) {

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int flag = 0;

        String tempString = setWeek.getText().toString();

        ArrayList<Integer> weekList = ResourceValues.gettingWeeksList(tempString);


        for (int i = 0 ;i<weekList.size();i++)
         {
            Calendar calendar =Calendar.getInstance();
            int request_id =Integer.parseInt(String.valueOf(primaryId_key) + weekList.get(i));

            Intent intent = new Intent(this, NotificationBroadcastReceiver.class);

            calendar.set(Calendar.HOUR_OF_DAY,hours);
            calendar.set(Calendar.MINUTE,minutes);
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MILLISECOND,0);

            intent.putExtra("ReminderNote", taskReminder.getText().toString());
            intent.putExtra("requestCode",request_id);
            intent.putExtra("hours",hours);
            intent.putExtra("minutes",minutes);
            intent.putExtra("dayOfWeek",weekList.get(i));


           if (calendar.get(Calendar.DAY_OF_WEEK) > weekList.get(i))
            {
                calendar.add(Calendar.WEEK_OF_YEAR,1);
                Log.d("WeekDetail", "I am in Condition");

            }
            calendar.set(Calendar.DAY_OF_WEEK,weekList.get(i));
            Log.d("WeekDetail", "Day of Week " + Calendar.DAY_OF_WEEK);
            Log.d("WeekDetail", "List of Week" +weekList.get(i));
            Log.d("WeekDetail", "" + calendar.getTime());
            Log.d("WeekDetail", "" + flag);






            PendingIntent pendingIntent = PendingIntent.getBroadcast(this,request_id,intent,PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
             flag++;

        }








    }



    private void openWeekSetFragment()
    {
        WeekFragment weekFragment = new WeekFragment();
        weekFragment.setClickListner(SetTime.this);
        weekFragment.show(getSupportFragmentManager(),weekFragment.getTag());
    }


    private void openTimeSetDailog()
    {

        Log.d("setTime","i am in OpenTimeDailog Method");
        TimePickerDialog timePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                if (hourOfDay == 0)
                {
                    hourOfDay = 1;
                }
                if (minute == 0)
                {
                    minute = 1;
                }

//                This if for saving the value and passing the intent
                hours = hourOfDay;
                minutes = minute;






                Log.d("setTime","Hour" + (hourOfDay*60*60*1000) + (minute*60*1000));

                 int hoursInThe12Hours = hourOfDay;
                String AmPm  = "AM";

                if (hourOfDay > 12)
                {
                    hoursInThe12Hours =    hourOfDay -  12 ;
                    AmPm = "PM";
                }

                if (hoursInThe12Hours<10)
                {
                    if (minute<10)
                    {
                        setTime.setText( "0" + hoursInThe12Hours + ":" + "0" + minute +  " " +AmPm);
                    }
                    else
                    {
                        setTime.setText( "0" + hoursInThe12Hours + ":" + minute +  " " +AmPm);
                    }

                }
                else
                {
                    if (minute<10)
                    {
                        setTime.setText(hoursInThe12Hours + ":" + "0" +  minute +  " " +AmPm);
                    }
                    else
                    {
                        setTime.setText(hoursInThe12Hours + ":" + minute +  " " +AmPm);
                    }

                }


            }
        },15,00,false);

        timePicker.show();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home)
        {
            Intent intent = new Intent(SetTime.this,MainActivity.class);
            startActivity(intent);
            finish();
        }


        return true;
    }


    @Override
    public void onSetListner(String[] weekList) {

        String tempString = weekList[0];
        int flag = 0;

        for (int i = 1 ; i < weekList.length ; i++ )
        {

            if (!weekList[i].isEmpty())
            {
                    tempString = tempString + "," + weekList[i];
            }

        }

        if (tempString.charAt(0) == ',')
        {
            Log.d("weekList", "i m in TempString.charAt(0)" );
          tempString =   tempString.substring(1);
        }

        setWeek.setText(tempString);
        Log.d("weekList", tempString );
    }
}