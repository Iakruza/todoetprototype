package com.example.todoetprototype.planner;

import androidx.annotation.NonNull;

import java.util.Locale;

public class PlannerItem {

    private int id, status; // Id name of the task to execute query
    private boolean canGivenCoins = true;
    private String task; // Actual text of the task
    private String date;  // Date to complete task

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean canGivenCoins() {
        return canGivenCoins;
    }

    public void setCanGivenCoins(boolean canGivenCoins) {
        this.canGivenCoins = canGivenCoins;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.ENGLISH,
                "ID: %d, Status: %d, Coin Give-able: %b, Task Name: %s, Due Date: %s",
                id, status, canGivenCoins, task, date);
    }
}

