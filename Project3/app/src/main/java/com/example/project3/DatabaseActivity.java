package com.example.project3;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.telephony.SmsManager;

import java.util.ArrayList;

public class DatabaseActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ArrayAdapter<InventoryItem> adapter;
    private EditText editTextPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        dbHelper = new DatabaseHelper(this);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);

        GridView gridView = findViewById(R.id.gridView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dbHelper.getAllInventoryItems());
        gridView.setAdapter(adapter);

        // Set a click listener to handle item selection
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click (e.g., show details or allow editing)
                InventoryItem selectedItem = adapter.getItem(position);
                Toast.makeText(DatabaseActivity.this, "Selected item: " + selectedItem.getItemName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to send SMS notification
    public void sendSmsNotification(View view) {
        String phoneNumber = editTextPhoneNumber.getText().toString();
        String message = "Hello! This is your SMS notification.";

        if (!phoneNumber.isEmpty()) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);

            Toast.makeText(this, "SMS sent successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to handle the "Create" operation
    public void addItemToDatabase(View view) {
        // Implement logic to add a new item to the database
        dbHelper.addInventoryItem("New Item", 0);
        refreshGridView();
    }

    // Method to handle the "Delete" operation
    public void deleteItemFromDatabase(View view) {
        // Implement logic to delete an item from the database
        if (adapter.getCount() > 0) {
            dbHelper.deleteInventoryItem(adapter.getItem(0).getId());
            refreshGridView();
        } else {
            Toast.makeText(this, "No items to delete", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to handle the "Update" operation
    public void updateItemInDatabase(View view) {
        // Implement logic to update an item in the database
        if (adapter.getCount() > 0) {
            InventoryItem firstItem = adapter.getItem(0);
            dbHelper.updateInventoryItem(firstItem.getId(), "Updated Item", 1);
            refreshGridView();
        } else {
            Toast.makeText(this, "No items to update", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to refresh the grid view with the latest data from the database
    private void refreshGridView() {
        adapter.clear();
        adapter.addAll(dbHelper.getAllInventoryItems());
        adapter.notifyDataSetChanged();
    }
}
