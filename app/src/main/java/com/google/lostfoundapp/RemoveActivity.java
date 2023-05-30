package com.google.lostfoundapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.lostfoundapp.bean.Item;

import org.litepal.LitePal;

public class RemoveActivity extends AppCompatActivity {
    private int itemId;  // Variable to store the ID of the item
    private Item item;  // Variable to store the item object
    private Button remove;  // Button to remove the item
    private TextView removeTv;  // TextView to display item details

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove);

        itemId = (int) getIntent().getExtras().get("itemId");  // Get the item ID from the intent extras
        item = LitePal.where("itemId = ?", String.valueOf(itemId)).find(Item.class).get(0);
        // Query the LitePal database for the item with the specified ID and retrieve it as an Item object

        removeTv = findViewById(R.id.removeTv);  // Initialize the removeTv TextView
        removeTv.setText(item.getType() + " " + item.getName() + " \n\n" + item.getDate() + " \n\nAt " + item.getLocation());
        // Set the text of removeTv to display the type, name, date, and location of the item

        remove = findViewById(R.id.remove);  // Initialize the remove Button
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.delete() > 0) {
                    // Delete the item from the LitePal database.
                    Toast.makeText(RemoveActivity.this, "Success", Toast.LENGTH_SHORT).show();  // Display a short toast message indicating that the removal was successful
                    finish();
                }
            }
        });
    }
}