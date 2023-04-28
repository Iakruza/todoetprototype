package com.example.todoetprototype.pet;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todoetprototype.inventory.UserModel;
import com.example.todoetprototype.store.StoreItem;

import java.io.Serializable;

public class TodopetViewModel extends ViewModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private final MutableLiveData<PetModel> petData = new MutableLiveData<>(PetModel.getInstance());

    private final long tickRate = 1000L;  // Milliseconds


    public void init() {
        updatePet();
    }

    public LiveData<PetModel> getPetData() {
        return petData;
    }

    public StoreItem useItem(String itemCategory) {
        for (StoreItem item: UserModel.getInstance().getInventory()) {
            if (item.getItemCategory().equals(itemCategory)) {
                UserModel.getInstance().removeItemFromInventory(item);
                return item;  // item found
            }
        }
        return null;  // no items of type category
    }

    public void clean() {
        StoreItem item = useItem("CLEANER");
        if (item != null) {
            PetModel pet = PetModel.getInstance();
            pet.setHygiene(item.getEffectMultiplier() * 5);
            petData.setValue(pet);
        }
    }

    public void feed() {
        StoreItem item = useItem("FOOD");
        if (item != null) {
            PetModel pet = PetModel.getInstance();
            pet.setHunger(item.getEffectMultiplier() * 5);
            //petData.getValue().setAffection(10);
            //this.petData.getValue().lastFedTimestamp = new Date().getTime();
            petData.setValue(pet);
        }
    }

    public void brush() {
        StoreItem item = useItem("BRUSH");
        if (item != null) {
            PetModel pet = PetModel.getInstance();
            pet.setAffection(10);
            //this.petData.getValue().lastPetTimestamp = new Date().getTime();
            petData.setValue(pet);
        }
    }

    // clock
    int lastHygieneTick = 0;
    int lastHungerTick = 0;
    int lastAffectionTick = 0;
    int lastPetLevelTick = 0;

    final int ticksPerHygieneUpdate = 20;
    final int ticksPerHungerUpdate = 10;
    final int ticksPerAffectionUpdate = 20;
    final int ticksPerPetLevelUpdate = 20;

    private void updatePet() {

        Runnable tickPetStats = () -> {
            System.out.println("Tick");
            tickPetStats();
            updatePet();
        };

        new Handler(Looper.getMainLooper()).postDelayed(tickPetStats, tickRate);
    }

    private void tickPetStats() {

        PetModel pet = getPetData().getValue();

        if (pet == null) {
            System.err.println("Pet is null");
            return;
        }

        if (pet.currentStage != pet.egg) {
            lastHygieneTick = (lastHygieneTick + 1) % ticksPerHygieneUpdate;
            lastHungerTick = (lastHungerTick + 1) % ticksPerHungerUpdate;
            lastAffectionTick = (lastAffectionTick + 1) % ticksPerAffectionUpdate;
        }
        lastPetLevelTick = (lastPetLevelTick + 1) % ticksPerPetLevelUpdate;

        if (pet.currentStage != pet.egg) {
            // If pet reaches tick threshold, reduce hygiene.
            if (lastHygieneTick % ticksPerHygieneUpdate == 0) {
                pet.setHygiene(-5);
            }
            // If pet reaches tick threshold, reduce hunger stat.
            if (lastHungerTick % ticksPerHungerUpdate == 0) {
                pet.setHunger(-5);
            }
            // If pet reaches tick threshold, reduce affection stat.
            if (lastAffectionTick % ticksPerAffectionUpdate == 0) {
                pet.setAffection(-5);
            }
        }

        if (pet.getAffection() <= 0 || pet.getHunger() <= 0 || pet.getHygiene() <= 0)
            pet.currentStage = pet.egg;

        if (lastPetLevelTick % ticksPerPetLevelUpdate == 0) {
            // Update pet appearance based on stage
            if (pet.currentStage == pet.egg && pet.affection >= 50) {
                pet.currentStage = pet.baby;
                pet.hunger = 20;
                pet.hygiene = 20;
                pet.affection = 20;
            } else if (pet.currentStage == pet.baby &&
                    pet.hunger >= 50 &&
                    pet.hygiene >= 50 &&
                    pet.affection >= 50) {
                pet.currentStage = pet.adolescent;
            } else if (pet.currentStage == pet.adolescent &&
                    pet.hunger >= 70 &&
                    pet.hygiene >= 70 &&
                    pet.affection >= 70) {
                pet.currentStage = pet.adult;
            } else if (pet.currentStage == pet.adult &&
                    pet.hunger >= 90 &&
                    pet.hygiene >= 90 &&
                    pet.affection >= 90) {
                pet.currentStage = pet.ancient;
            }
        }

        petData.setValue(pet);
    }
}