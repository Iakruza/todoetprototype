package com.example.todoetprototype.inventory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todoetprototype.store.StoreItem;
import com.example.todoetprototype.utils.DatabaseHandler;

public class UserViewModel extends ViewModel {

    private final MutableLiveData<UserModel> userData = new MutableLiveData<>(UserModel.getInstance());

    public UserViewModel() {

        System.out.println("User View Model Created");
    }


    public LiveData<UserModel> getUserData() {
        return userData;
    }

    public void updateUser(int coins) {
        UserModel updatedUserModel = userData.getValue();
        assert updatedUserModel != null;
        updatedUserModel.setCoins(coins);
        userData.setValue(updatedUserModel);
    }

    public void addItemToInventory(StoreItem newItem) {
        UserModel.getInstance().addItemToInventory(newItem);
//        db.insertInventoryItem(newItem);
        userData.setValue(UserModel.getInstance());
    }
    public void removeItemFromInventory(DatabaseHandler db, StoreItem itemToRemove) {
        //UserModel.getInstance().removeItemFromInventory(itemToRemove);
        //db.deleteInventoryItem(itemToRemove.getItemID());
        userData.setValue(UserModel.getInstance());
    }
}
