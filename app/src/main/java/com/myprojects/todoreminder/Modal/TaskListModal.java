package com.myprojects.todoreminder.Modal;

public class TaskListModal {

   private String taskStartingName,timing,week;

    public TaskListModal(String taskStartingName, String timing, String week) {
        this.taskStartingName = taskStartingName;
        this.timing = timing;
        this.week = week;
    }

    public String getTaskStartingName() {
        return taskStartingName;
    }

    public String getTiming() {
        return timing;
    }

    public String getWeek() {
        return week;
    }
}
