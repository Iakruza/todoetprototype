package com.example.todoetprototype.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoetprototype.R;
import com.example.todoetprototype.store.StoreActivity;
import com.example.todoetprototype.store.StoreItem;
import com.example.todoetprototype.store.StoreViewModel;
import com.example.todoetprototype.utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class StoreItemAdapter extends RecyclerView.Adapter<StoreItemAdapter.ViewHolder> {

    private final StoreActivity activity;
    private final StoreViewModel storeViewModel;
    private List<StoreItem> storeItemList;
    private DatabaseHandler db;

    public StoreItemAdapter(DatabaseHandler db, StoreActivity activity) {
        this.db = db;
        this.activity = activity;
        this.storeItemList = new ArrayList<>();
        this.storeViewModel = activity.getStoreViewModel();
    }

    public void setStoreItemList(List<StoreItem> storeItemList) {
        this.storeItemList = storeItemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.store_item_layout, parent, false);
        return new StoreItemAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StoreItem item = storeItemList.get(position); // in the todolist you get the item
        holder.storeItemName.setText(item.getItemName()); // set the task from the item position
        holder.storeItemDescription.setText(item.getItemDescription()); // set the description
        holder.storeItemPrice.setText(String.valueOf(item.getItemPrice())); // set the price

        holder.buyItemBtn.setOnClickListener(v -> {
            boolean purchaseSucceeded = storeViewModel.buyItem(position);
            if (purchaseSucceeded)
                Toast.makeText(activity, "Buying item", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(activity, "Unable to buy item", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return storeItemList.size();
    }

    /**
     * This class represents the store_item_layout layout. This is how a store item appears to the
     * user.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // Item name
        TextView storeItemName;
        TextView storeItemDescription;
        TextView storeItemPrice;
        Button buyItemBtn;


        ViewHolder(View view){
            super(view);
            storeItemName = view.findViewById(R.id.storeItemName);
            storeItemDescription = view.findViewById(R.id.storeItemDescription);
            storeItemPrice = view.findViewById(R.id.storeItemPrice);
            buyItemBtn = view.findViewById(R.id.buyItemBtn);
        }
    }
}
