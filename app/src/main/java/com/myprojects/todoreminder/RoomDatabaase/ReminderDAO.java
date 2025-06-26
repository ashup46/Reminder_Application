package com.myprojects.todoreminder.RoomDatabaase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.myprojects.todoreminder.Values.ResourceValues;

import java.util.List;

@Dao
public interface ReminderDAO {

    @Query("SELECT * FROM " + ResourceValues.TABLE_NAME)
    List<ModalReminder> getAllNotes();

    @Insert
    long addNotes(ModalReminder modalReminder);

    @Update
    void updateNotes(ModalReminder modalReminder);

    @Delete
    void deleteNotes(ModalReminder modalReminder);

}
