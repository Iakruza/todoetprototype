package com.example.todoetprototype.store;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todoetprototype.inventory.UserModel;

import java.util.Objects;

public class StoreViewModel extends ViewModel {

    private final MutableLiveData<StoreModel> storeData = new MutableLiveData<>(StoreModel.getInstance());

    public StoreViewModel() {

        System.out.println("Store View Model Created");
    }

    public boolean buyItem(int position) {

        // Check if user model is initialized
        if (!UserModel.getInstance().getInitialized()) {
            return false;
        }

        // Check if user has enough coins
        final int userCoinTotal = UserModel.getInstance().getCoins();
        final int itemPrice = StoreModel.getInstance().getCatalog().get(position).getItemPrice();
        if (userCoinTotal < itemPrice) {
            return false;
        }

        // Remove item from store
        StoreItem itemToMove = getItemFromCatalog(position);
        // Add item to inventory
        UserModel.getInstance().addItemToInventory(itemToMove);
        // Set user coin balance
        UserModel.getInstance().setCoins(userCoinTotal - itemPrice);

        // Notify the observers
        storeData.setValue(StoreModel.getInstance());

        return true;
    }

    private StoreItem getItemFromCatalog(int position) {
        return Objects.requireNonNull(storeData.getValue()).getCatalog().get(position);
    }

    private StoreItem removeItemFromCatalog(int position) {
        return Objects.requireNonNull(storeData.getValue()).getCatalog().remove(position);
    }

    public MutableLiveData<StoreModel> getStoreData() {
        return storeData;
    }
}
