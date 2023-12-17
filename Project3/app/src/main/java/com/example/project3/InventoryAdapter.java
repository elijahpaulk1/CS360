package com.example.project3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class InventoryAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<InventoryItem> itemList;

    public InventoryAdapter(Context context, ArrayList<InventoryItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item_layout, parent, false);
        }

        TextView itemNameTextView = convertView.findViewById(R.id.itemNameTextView);
        TextView quantityTextView = convertView.findViewById(R.id.quantityTextView);

        InventoryItem currentItem = itemList.get(position);

        itemNameTextView.setText(currentItem.getItemName());
        quantityTextView.setText(String.valueOf(currentItem.getQuantity()));

        return convertView;
    }
}
