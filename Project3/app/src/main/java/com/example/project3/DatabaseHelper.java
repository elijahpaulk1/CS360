package com.example.project3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Database";
    private static final String TABLE_NAME = "InventoryTable";
    private static final String COL_ID = "ID";
    private static final String COL_ITEM_NAME = "ItemName";
    private static final String COL_ITEM_QUANTITY = "ItemQuantity";

    // User-related constants
    private static final String TABLE_USERS = "Users";
    private static final String COL_USER_ID = "UserID";
    private static final String COL_USERNAME = "Username";
    private static final String COL_PASSWORD = "Password";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME +
                " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_ITEM_NAME + " TEXT, " +
                COL_ITEM_QUANTITY + " INTEGER)";
        db.execSQL(createTableQuery);

        // Create users table
        String createUserTableQuery = "CREATE TABLE " + TABLE_USERS +
                " (" + COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT, " +
                COL_PASSWORD + " TEXT)";
        db.execSQL(createUserTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Method to add a new inventory item to the database
    public boolean addInventoryItem(String itemName, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ITEM_NAME, itemName);
        contentValues.put(COL_ITEM_QUANTITY, quantity);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    // Method to get all inventory items from the database
    public ArrayList<InventoryItem> getAllInventoryItems() {
        ArrayList<InventoryItem> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                try {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                    String itemName = cursor.getString(cursor.getColumnIndexOrThrow(COL_ITEM_NAME));
                    int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ITEM_QUANTITY));

                    itemList.add(new InventoryItem(id, itemName, quantity));
                } catch (IllegalArgumentException e) {
                    // Log or handle the exception
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return itemList;
    }

    // Method to update an inventory item in the database
    public boolean updateInventoryItem(int id, String itemName, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ITEM_NAME, itemName);
        contentValues.put(COL_ITEM_QUANTITY, quantity);

        int result = db.update(TABLE_NAME, contentValues, COL_ID + " = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    // Method to delete an inventory item from the database
    public boolean deleteInventoryItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COL_ID + " = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    // Method to register a new user
    public boolean registerUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if the username already exists
        if (isUsernameExists(username)) {
            return false; // Username already exists
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, contentValues);
        return result != -1;
    }

    // Method to check if the provided credentials are valid
    public boolean loginUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_USER_ID};
        String selection = COL_USERNAME + "=? and " + COL_PASSWORD + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    // Method to check if the provided username already exists
    private boolean isUsernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_USERS);

        String[] projection = {COL_USERNAME};
        String selection = COL_USERNAME + "=?";
        String[] selectionArgs = {username};

        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }
}

// InventoryItem class to represent an item in the inventory
class InventoryItem {
    private int id;
    private String itemName;
    private int quantity;

    public InventoryItem(int id, String itemName, int quantity) {
        this.id = id;
        this.itemName = itemName;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }
}
