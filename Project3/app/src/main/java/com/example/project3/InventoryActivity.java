package com.example.project3;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class InventoryActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        dbHelper = new DatabaseHelper(this);

        // Adding an item to the inventory
        dbHelper.addInventoryItem("Item A", 10);

        // Updating an item in the inventory
        dbHelper.updateInventoryItem(1, "Item A", 20);

        // Deleting an item from the inventory
        dbHelper.deleteInventoryItem(1);

        // Displaying all items in a GridView
        ArrayList<InventoryItem> inventoryList = dbHelper.getAllInventoryItems();
        InventoryAdapter adapter = new InventoryAdapter(this, inventoryList);
        GridView gridView = findViewById(R.id.gridView); // Use R.id.gridView here
        gridView.setAdapter(adapter);

    }
}
