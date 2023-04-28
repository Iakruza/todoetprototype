package com.example.todoetprototype.store;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoetprototype.Nexus;
import com.example.todoetprototype.R;
import com.example.todoetprototype.adapter.StoreItemAdapter;
import com.example.todoetprototype.databinding.ActivityStoreBinding;
import com.example.todoetprototype.inventory.InventoryActivity;
import com.example.todoetprototype.inventory.UserModel;
import com.example.todoetprototype.pet.PetActivity;
import com.example.todoetprototype.planner.PlannerActivity;
import com.example.todoetprototype.utils.DatabaseHandler;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class StoreActivity extends AppCompatActivity {

    private ActivityStoreBinding binding;
    private StoreViewModel storeViewModel;
    private RecyclerView storeItemsView;
    private StoreItemAdapter storeItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide(); // will not show top most navigation bar

        storeViewModel = new ViewModelProvider(this).get(StoreViewModel.class);

        storeViewModel.getStoreData().observe(this, storeModel -> {

            storeItemAdapter.setStoreItemList(storeModel.getCatalog());

            // Get user coins from UserModel
            TextView userCoin = findViewById(R.id.userCoinDisplay);
            String coinDisplayFormatted = "Current coins: " + UserModel.getInstance().getCoins();
            userCoin.setText(coinDisplayFormatted);
        });

        storeItemsView = findViewById(R.id.storeItemsRecyclerView);
        storeItemsView.setLayoutManager(new LinearLayoutManager(this));
        storeItemAdapter = new StoreItemAdapter(DatabaseHandler.getInstance(), this);
        storeItemsView.setAdapter(storeItemAdapter);

        // Test adding some items
        storeItemAdapter.setStoreItemList(StoreModel.getInstance().getCatalog());

        setUpNavigationBar();
    }

    private void setUpNavigationBar() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.store_nav_item).setChecked(true);

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

    public StoreViewModel getStoreViewModel() {
        return storeViewModel;
    }
}