package com.example.todoetprototype.adapter;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoetprototype.R;
import com.example.todoetprototype.inventory.InventoryActivity;
import com.example.todoetprototype.inventory.UserViewModel;
import com.example.todoetprototype.store.StoreItem;
import com.example.todoetprototype.utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class InventoryItemAdapter extends RecyclerView.Adapter<InventoryItemAdapter.ViewHolder> {

    private static InventoryActivity activity;
    private UserViewModel userViewModel;

    private List<StoreItem> inventoryItemList;
    private DatabaseHandler db;

    public InventoryItemAdapter(DatabaseHandler db, InventoryActivity activity) {
        this.db = db;
        this.activity = activity;
        this.inventoryItemList = new ArrayList<>();
        this.userViewModel = activity.getUserViewModel();
    }

    public void setInventoryItemList(List<StoreItem> inventoryItemList) {
        this.inventoryItemList = inventoryItemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public InventoryItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_inventory_layout, parent, false);
        return new InventoryItemAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryItemAdapter.ViewHolder holder, int position) {
        StoreItem item = inventoryItemList.get(position); // in the todolist you get the item
        holder.inventoryItemName.setText(item.getItemName()); // set the task from the item position
        holder.inventoryItemDescription.setText(item.getItemDescription()); // set the description

        final int imageID = item.getDrawable();
        final Resources resources = activity.getResources();
        final Drawable imageDrawable = ResourcesCompat.getDrawable(resources, imageID, null);
        if (imageDrawable == null)
            throw new RuntimeException("Broken");

        holder.inventoryItemImage.setImageDrawable(imageDrawable);
    }

    @Override
    public int getItemCount() {
        return inventoryItemList.size();
    }

    /**
     * This class represents the store_item_layout layout. This is how a store item appears to the
     * user.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // Item name
        TextView inventoryItemName;
        TextView inventoryItemDescription;
        ImageView inventoryItemImage;

        ViewHolder(View view){
            super(view);
            inventoryItemName = view.findViewById(R.id.textView_ItemName);
            inventoryItemDescription = view.findViewById(R.id.textView_itemDescription);
            inventoryItemImage = view.findViewById(R.id.inventoryItemImageView);
        }
    }
}
