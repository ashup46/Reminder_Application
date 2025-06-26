package com.myprojects.todoreminder;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.myprojects.todoreminder.Adapters.AdapterRecyclerView;
import com.myprojects.todoreminder.BroadcastReceiver.NotificationBroadcastReceiver;
import com.myprojects.todoreminder.Modal.TaskListModal;
import com.myprojects.todoreminder.RoomDatabaase.DbHelperClass;
import com.myprojects.todoreminder.RoomDatabaase.ModalReminder;
import com.myprojects.todoreminder.Values.ResourceValues;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;

    RecyclerView recyclerView;

    FloatingActionButton floatingActionButton;



    List<ModalReminder> taskList = new ArrayList<ModalReminder>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.actionBarId);
        recyclerView = findViewById(R.id.recyclerListViewId);
        floatingActionButton = findViewById(R.id.floatingActionBtnId);

        setSupportActionBar(toolbar);

        taskList = DbHelperClass.getDbInstance(this).reminderDAO().getAllNotes();



//        This is for setting the recyclerView and Adapters
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AdapterRecyclerView adapterRecyclerView  = new AdapterRecyclerView(this,taskList);
        recyclerView.setAdapter(adapterRecyclerView);



// For adding New Task Reminder

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SetTime.class);
                startActivity(intent);
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//    This is for Deleting the item by sliding
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                Snackbar snackbar = Snackbar.make(findViewById(R.id.main),"Reminder Deleted",Snackbar.LENGTH_LONG);
                snackbar.show();

                int idTemp = taskList.get(viewHolder.getAdapterPosition()).getId();
                String noteTemp = taskList.get(viewHolder.getAdapterPosition()).getReminderNotes();
                String timeTemp = taskList.get(viewHolder.getAdapterPosition()).getTime();
                String weekTemp = taskList.get(viewHolder.getAdapterPosition()).getWeeks();

            //This Snippet is for Deleleting the Column from the Database table
                DbHelperClass.getDbInstance(MainActivity.this)
                        .reminderDAO()
                        .deleteNotes(new ModalReminder(timeTemp,idTemp,weekTemp,noteTemp));

                //     This Snippet is for Deleleting from the arraylist only

                taskList.remove(viewHolder.getAdapterPosition());

                adapterRecyclerView.notifyDataSetChanged();

            //      This Snippet is for Cancelling the Alarm

                cancelAlarm(idTemp,weekTemp);
            }
        };
        ItemTouchHelper helper = new ItemTouchHelper(callback);

        helper.attachToRecyclerView(recyclerView);

    }

//    This is for Canceling the Alarm
    private void cancelAlarm(int idTemp,String allWeeks) {

        Intent intent = new Intent(this, NotificationBroadcastReceiver.class);

        ArrayList<Integer> weekList = ResourceValues.gettingWeeksList(allWeeks);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        for (int i = 0 ;i<weekList.size();i++)
        {

         int request_id =Integer.parseInt(String.valueOf(idTemp) + weekList.get(i));

            Log.d("WeekDetail", "" + request_id);

         PendingIntent pendingIntent = PendingIntent.getBroadcast(this,request_id,intent,PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);



         alarmManager.cancel(pendingIntent);
        }




    }




}