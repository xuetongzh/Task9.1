package com.google.lostfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.lostfoundapp.adapter.DemoListAdapter;
import com.google.lostfoundapp.bean.DemoInfo;

/**
 * Search demo entry
 */
public class PoiSearchDemoList extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menulist);
        // Get a reference to the ListView from the layout
        ListView demoList = (ListView) findViewById(R.id.mapList);
        // Set the adapter for the ListView using a custom DemoListAdapter
        demoList.setAdapter(new DemoListAdapter(PoiSearchDemoList.this, DEMOS));
        // Set an item click listener for the ListView
        demoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View v, int index, long arg3) {
                onListItemClick(index);  // Call onListItemClick method when an item is clicked
            }
        });
    }

    // Handle the item click event
    void onListItemClick(int index) {
        Intent intent;
        // Create an Intent to launch the selected demo activity based on the index
        intent = new Intent(PoiSearchDemoList.this, DEMOS[index].demoClass);
        // Start the activity
        this.startActivity(intent);
    }

    // Array of DemoInfo objects, represents different demos
    private static final DemoInfo[] DEMOS = {
            new DemoInfo(R.string.demo_name_suggestion_search, R.string.demo_desc_suggestion_search,
                    PickLocationActivity.class),
    };
}
