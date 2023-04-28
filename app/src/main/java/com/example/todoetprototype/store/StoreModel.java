package com.example.todoetprototype.store;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todoetprototype.R;

import java.util.ArrayList;
import java.util.List;

public class StoreModel extends AppCompatActivity {

    public static StoreModel instance = null;

    public static StoreModel getInstance() {
        if (instance == null)
            instance = new StoreModel();
        return instance;
    }

    private final List<StoreItem> catalog = new ArrayList<>();

    private StoreModel() {
        // Add dummy data
        catalog.add(new StoreItem(1, 5, "Soap", "Cleans pet to increase hygiene", "CLEANER", 1));
        //catalog.add(new StoreItem(2, 10, "Toothbrush", "A brush for cleaning teeth", "CLEANER", 2));
        catalog.add(new StoreItem(3, 4, "Todofood", "Delicious food for pets to increase hunger", "FOOD", 1));  // FOOD
        catalog.add(new StoreItem(4,5,"Brush","Brush pet to increase affection","BRUSH",1));
        // catalog.add(new StoreItem(4, 3, "Color Potion","Changes color of pet","POTION"));  // POTION
        // catalog.add(new StoreItem(5, 3, "Healing Salve","Cures sickness","MEDICINE"));  // MEDICINE
    }

    //Getters and Setters

    public List<StoreItem> getCatalog() {
        return catalog;
    }
}



