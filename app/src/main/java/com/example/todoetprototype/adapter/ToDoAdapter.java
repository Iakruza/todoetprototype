package com.example.todoetprototype.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoetprototype.R;
import com.example.todoetprototype.inventory.UserModel;
import com.example.todoetprototype.inventory.UserViewModel;
import com.example.todoetprototype.planner.AddNewTask;
import com.example.todoetprototype.planner.PlannerActivity;
import com.example.todoetprototype.planner.PlannerItem;
import com.example.todoetprototype.planner.PlannerModel;
import com.example.todoetprototype.utils.DatabaseHandler;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private List<PlannerItem> todoList;
    private PlannerActivity activity;
    private DatabaseHandler db;
    private UserViewModel userViewModel;
    //private PlannerViewModel plannerViewModel;

    public ToDoAdapter(DatabaseHandler db, PlannerActivity activity) {
        this.db = db;
        this.activity = activity;
        //this.plannerViewModel = activity.getPlannerViewModel();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemView);
    }

    // standard implementation of recycler view adaptor
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        db.openDatabase(); // open database
        PlannerItem item = todoList.get(position); // in the todolist you get the item
        holder.task.setText(item.getTask()); // set the task from the item position
        holder.task.setChecked(toBoolean(item.getStatus())); // checks the status of the item if it is checked or not
        holder.task.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                db.updateStatus(item.getId(), 1);
            } else {
                db.updateStatus(item.getId(),0);
            }
        });
        holder.dueDate.setText(item.getDate());  // Set the due date of the task.
    }

    /**
     * Sets the getItem count, this will let it know how many items it needs to print
     */
    @Override
    public int getItemCount() {
        return PlannerModel.getInstance().getPlannerItems().size();
    }

    /**
     * Since the checkmark is boolean type, need to convert to boolean
     */
    private boolean toBoolean(int n) {
        return n!=0;
    }

    // For dummy data

    public void setTaskList(List<PlannerItem> todoList) {
        this.todoList = todoList;
        notifyDataSetChanged(); // so recycler view is updated
    }

    public Context getContext(){
        return activity;
    }

    /**
     * Delete item from activity
     */
    public void deleteItem(int position) {
        PlannerItem item = todoList.remove(position); // Remove from the item
        db.deleteTask(item.getId()); // Id of item being deleted

        UserModel userModel = UserModel.getInstance();
        userModel.setCoins(userModel.getCoins() + 1);

        notifyItemRemoved(position); // Notifies that the item will be removed and will automatically update view
    }

    /**
     * Update edited items
     */
    public void editItem(int position) {
        PlannerItem item = todoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id",item.getId());
        bundle.putString("task", item.getTask());
        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AddNewTask.TAG);
    }

    // This class represents the task_layout layout. This is how a task appears to the user.
    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;
        TextView dueDate;

        ViewHolder(View view) {
            super(view);
            task = view.findViewById(R.id.todoCheckBox);
            dueDate = view.findViewById(R.id.dueDate);
        }
    }
}