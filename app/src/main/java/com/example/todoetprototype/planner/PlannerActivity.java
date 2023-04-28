package com.example.todoetprototype.planner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoetprototype.DialogCloseListener;
import com.example.todoetprototype.Nexus;
import com.example.todoetprototype.R;
import com.example.todoetprototype.adapter.ToDoAdapter;
import com.example.todoetprototype.inventory.InventoryActivity;
import com.example.todoetprototype.pet.PetActivity;
import com.example.todoetprototype.store.StoreActivity;
import com.example.todoetprototype.utils.DatabaseHandler;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PlannerActivity extends AppCompatActivity implements DialogCloseListener {

    // Extra note I needed to change the build from 32 to 33 in the build.gradle for the app to run

    //private PlannerViewModel plannerViewModel;
    private ToDoAdapter tasksAdapter;
    private DatabaseHandler db;
    private List<PlannerItem> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);
        Objects.requireNonNull(getSupportActionBar()).hide(); // will not show top most navigation bar

        // Initialize userViewModel
//        plannerViewModel = new ViewModelProvider(this).get(PlannerViewModel.class);

        // set current date
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        TextView textViewDate = findViewById(R.id.text_date);
        textViewDate.setText(currentDate);

        // Initialize the database
        db = DatabaseHandler.getInstance();
        db.openDatabase();

        // for the recycler view: RecyclerView makes it easy to efficiently display large sets of data. You supply the data and define how each item looks, and the RecyclerView library dynamically creates the elements when they're needed.
        // As the name implies, RecyclerView recycles those individual elements. When an item scrolls off the screen, RecyclerView doesn't destroy its view. Instead, RecyclerView reuses the view for new items that have scrolled onscreen.
        // RecyclerView improves performance and your app's responsiveness, and it reduces power consumption.
        RecyclerView taskRecyclerView = findViewById(R.id.tasksRecyclerView);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // define linear layout manager
        tasksAdapter = new ToDoAdapter(db, this);
        taskRecyclerView.setAdapter(tasksAdapter);

        // Floating action button. For the button in the corner of the screen to create a new task
        FloatingActionButton fab = findViewById(R.id.fab);

        // Updates task list
        taskList = db.getAllTasks();
        PlannerModel.getInstance().setPlannerItems(taskList);
        Collections.reverse(taskList); // reverse elements in an array
        tasksAdapter.setTaskList(taskList); // add task to recycler view

        // This is a utility class to add swipe to dismiss and drag &drop support to RecyclerView.
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(taskRecyclerView);


        fab.setOnClickListener(
                v -> AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG)
        );

        setUpNavigationBar();
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        taskList = db.getAllTasks();
        PlannerModel.getInstance().setPlannerItems(taskList);
        Collections.reverse(taskList);
        tasksAdapter.setTaskList(taskList);
        tasksAdapter.notifyDataSetChanged();
    }
//
//        public PlannerViewModel getPlannerViewModel() {
//            return plannerViewModel;
//        }

    private void setUpNavigationBar() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.todo_list_nav_item).setChecked(true);

        BottomNavigationItemView todoListNavItem = bottomNavigationView.findViewById(R.id.todo_list_nav_item);
        todoListNavItem.setOnClickListener(v -> {
            Intent changeActivities = new Intent(this, PlannerActivity.class);
            startActivity(changeActivities);
        });

        BottomNavigationItemView userNavItem = bottomNavigationView.findViewById(R.id.user_nav_item);
        userNavItem.setOnClickListener(v -> {
            Intent changeActivities = new Intent(this, InventoryActivity.class);
            startActivity(changeActivities);
        });

        BottomNavigationItemView homeNavItem = bottomNavigationView.findViewById(R.id.home_nav_item);
        homeNavItem.setOnClickListener(v -> {
            Intent changeActivities = new Intent(this, Nexus.class);
            startActivity(changeActivities);
        });

        BottomNavigationItemView petsNavItem = bottomNavigationView.findViewById(R.id.pets_nav_item);
        petsNavItem.setOnClickListener(v -> {
            Intent changeActivities = new Intent(this, PetActivity.class);
            startActivity(changeActivities);
        });

        BottomNavigationItemView storeNavItem = bottomNavigationView.findViewById(R.id.store_nav_item);
        storeNavItem.setOnClickListener(v -> {
            Intent changeActivities = new Intent(this, StoreActivity.class);
            startActivity(changeActivities);
        });
    }
}
