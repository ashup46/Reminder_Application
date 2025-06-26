package com.myprojects.todoreminder.RoomDatabaase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.myprojects.todoreminder.Values.ResourceValues;

@Database(entities = ModalReminder.class,exportSchema = false,version = ResourceValues.VERSION_DB)
public abstract class DbHelperClass extends RoomDatabase {

private static DbHelperClass dbInstance;

public static synchronized DbHelperClass getDbInstance(Context context)
{
    if (dbInstance == null)
    {
        dbInstance = Room.databaseBuilder(context,DbHelperClass.class, ResourceValues.DB_Name)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    return dbInstance;
}


public abstract ReminderDAO reminderDAO();




}
