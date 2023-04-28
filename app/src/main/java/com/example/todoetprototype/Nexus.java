package com.example.todoetprototype;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todoetprototype.inventory.InventoryActivity;
import com.example.todoetprototype.inventory.UserModel;
import com.example.todoetprototype.pet.PetActivity;
import com.example.todoetprototype.pet.PetModel;
import com.example.todoetprototype.planner.PlannerActivity;
import com.example.todoetprototype.planner.PlannerModel;
import com.example.todoetprototype.store.StoreActivity;
import com.example.todoetprototype.store.StoreModel;
import com.example.todoetprototype.utils.DatabaseHandler;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class Nexus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nexus);
        Objects.requireNonNull(getSupportActionBar()).hide(); // will not show top most navigation bar

        // Prime database handler
        DatabaseHandler.getInstance(this.getApplicationContext());
        // Initialize user model
        UserModel.getInstance().loadInventory();

        setUpNavigationButtons();

        setUpNavigationBar();
    }

    private void setUpNavigationButtons() {

        // Navigate to the pet page
        ImageButton petPageBtn = findViewById(R.id.petPageBtn);
        petPageBtn.setOnClickListener(view -> {
            Toast.makeText(this, "Changing To Pet Activity", Toast.LENGTH_SHORT).show();
            Intent changeActivities = new Intent(this, PetActivity.class);
            startActivity(changeActivities);
        });

        // Navigate to the planner page
        ImageButton plannerPageBtn = findViewById(R.id.plannerPageBtn);
        plannerPageBtn.setOnClickListener(view -> {
            Toast.makeText(this, "Changing To Planner Activity", Toast.LENGTH_SHORT).show();
            Intent changeActivities = new Intent(this, PlannerActivity.class);
            startActivity(changeActivities);
        });

        // Navigate to the Inventory page
        ImageButton inventoryPageBtn = findViewById(R.id.inventoryPageBtn);
        inventoryPageBtn.setOnClickListener(view -> {
            Toast.makeText(this, "Changing To Inventory Activity", Toast.LENGTH_SHORT).show();
            Intent changeActivities = new Intent(this, InventoryActivity.class);
            startActivity(changeActivities);
        });

        // Navigate to the Store page
        ImageButton storePageBtn = findViewById(R.id.storePageBtn);
        storePageBtn.setOnClickListener(view -> {
            Toast.makeText(this, "Changing To Store Activity", Toast.LENGTH_SHORT).show();
            Intent changeActivities = new Intent(this, StoreActivity.class);
            startActivity(changeActivities);
        });
    }

    private void setUpNavigationBar() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.home_nav_item).setChecked(true);

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