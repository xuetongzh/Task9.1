package com.google.lostfoundapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.lostfoundapp.adapter.ItemAdapter;
import com.google.lostfoundapp.bean.Item;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class AllActivity extends AppCompatActivity {
    private RecyclerView allRec;
    private ItemAdapter itemAdapter;
    private List<Item> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);  // Set the activity layout file to activity_all.xml
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();  // Get data and update the page when the activity resumes
    }

    private void getData() {
        items = LitePal.findAll(Item.class);  // Retrieve all item data from the database and store it in the items list
        allRec = findViewById(R.id.allRec);  // Find the RecyclerView control in the layout file by using its Id
        allRec.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));  // Set the RecyclerView's layout manager to a vertical linear layout
        itemAdapter = new ItemAdapter(AllActivity.this, items);  // Create an ItemAdapter object and pass the item list to it
        allRec.setAdapter(itemAdapter);  // Set the ItemAdapter as the adapter for the RecyclerView to display the item list
    }
}