package com.myprojects.todoreminder.RoomDatabaase;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.myprojects.todoreminder.Values.ResourceValues;

@Entity(tableName = ResourceValues.TABLE_NAME)
public class ModalReminder {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = ResourceValues.KEY_NOTES)
    private String reminderNotes;


    @ColumnInfo(name = ResourceValues.KEY_HOURS)
    private String time;


    @ColumnInfo(name = ResourceValues.KEY_MINUTES)
    private String weeks;

    public ModalReminder(String time, int id, String weeks, String reminderNotes) {
        this.time = time;
        this.id = id;
        this.weeks = weeks;
        this.reminderNotes = reminderNotes;
    }

    @Ignore
    public ModalReminder(String time, String weeks, String reminderNotes) {
        this.time = time;
        this.weeks = weeks;
        this.reminderNotes = reminderNotes;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWeeks() {
        return weeks;
    }

    public void setWeeks(String weeks) {
        this.weeks = weeks;
    }

    public String getReminderNotes() {
        return reminderNotes;
    }

    public void setReminderNotes(String reminderNotes) {
        this.reminderNotes = reminderNotes;
    }
}
